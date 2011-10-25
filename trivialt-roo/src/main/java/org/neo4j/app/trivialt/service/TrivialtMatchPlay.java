package org.neo4j.app.trivialt.service;

import static org.neo4j.graphdb.Direction.INCOMING;
import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.app.trivialt.model.Answer;
import org.neo4j.app.trivialt.model.Card;
import org.neo4j.app.trivialt.model.Category;
import org.neo4j.app.trivialt.model.Deck;
import org.neo4j.app.trivialt.model.FramedQuestion;
import org.neo4j.app.trivialt.model.Match;
import org.neo4j.app.trivialt.model.Proposal;
import org.neo4j.app.trivialt.model.Question;
import org.neo4j.app.trivialt.model.Round;
import org.neo4j.app.trivialt.model.Team;
import org.neo4j.app.trivialt.repository.AnswerRepository;
import org.neo4j.app.trivialt.repository.CategoryRepository;
import org.neo4j.app.trivialt.repository.MatchRepository;
import org.neo4j.app.trivialt.repository.PlayerRepository;
import org.neo4j.app.trivialt.repository.QuestionRepository;
import org.neo4j.app.trivialt.repository.TeamRepository;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.helpers.collection.ClosableIterable;
import org.neo4j.kernel.Traversal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.EntityPath;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TrivialtMatchPlay {

	public static final String EMPTY_MATCH_TITLE = "Nothing To See Here";
	public static final String EMPTY_ROUND_TITLE = "Any Answer Will Do";

	@Autowired
	private CategoryRepository categories;
	@Autowired
	private QuestionRepository questions;
	@Autowired
	private AnswerRepository answers;
	@Autowired
	private PlayerRepository players;
	@Autowired
	private TeamRepository teams;
	@Autowired
	private MatchRepository matches;

	public boolean teamNameExists(String name) {
		ClosableIterable<Team> findAllByPropertyValue = teams
				.findAllByPropertyValue("name", name);
		return (findAllByPropertyValue.iterator().hasNext());
	}

	public void initiate(Team newTeam) {
		newTeam = newTeam.save();
		Match featuredMatch = getFeaturedMatch();
		Deck deckForMatch = new Deck();
		deckForMatch.setMatch(featuredMatch);
		for (Round r : featuredMatch.getRounds()) {
			Card c = new Card();
			c.setRound(r);
			c.setDeck(deckForMatch);
			c.save();
		}
		deckForMatch.save();
		newTeam.add(deckForMatch);
		newTeam.setCurrentDeck(deckForMatch);
		newTeam.save();
	}

	public Match getFeaturedMatch() {
		Match featuredMatch = null;
		// ABK: matches.findAllByProperty("featured", true) never works. why?
		for (Match possibleMatch : matches.findAll()) {
			if (possibleMatch.getFeatured()) {
				featuredMatch = possibleMatch;
				break;
			}
		}
		if (featuredMatch != null) {
			return featuredMatch;
		} else {
			return getApologeticEmptyMatch();
		}
	}

	public void setFeaturedMatch(Match featuredMatch) {
		for (Match m : matches.findAll()) {
			m.setFeatured(false);
			m.save();
		}
		featuredMatch.setFeatured(true);
		featuredMatch.save();
	}

	private Match getApologeticEmptyMatch() {
		Match apologetic = matches.findByPropertyValue("title",
				EMPTY_MATCH_TITLE);
		if (apologetic == null) {
			apologetic = new Match(EMPTY_MATCH_TITLE);
			apologetic.setFeatured(true);
			apologetic.save();

			// Sad round 1

			Round oneSadRound = new Round("Round 1: Nothing to see here")
					.save();

			Question apology = uniqueQuestion("Sorry, were you hoping to play Trivialt?");
			Answer yes = uniqueAnswer("yes").save();
			Answer no = uniqueAnswer("no").save();
			Category general = uniqueCategory("General").save();
			apology.setAnswer(yes);
			apology.setCategory(general);
			apology.save();
			yes.save();
			general.save();

			FramedQuestion frame = new FramedQuestion().save();
			frame.setOriginalQuestion(apology);
			frame.setPhrase("Sorry, were you hoping to play Trivialt?");
			frame.add(yes);
			frame.add(no);
			frame.save();

			oneSadRound.add(frame);
			oneSadRound.save();

			// Sad round 2

			Round anotherSadRound = new Round("Round 2: Please go home").save();

			Question stillHere = uniqueQuestion("Sorry, are you still here?");
			stillHere.setAnswer(yes);
			stillHere.setCategory(general);
			stillHere.save();

			frame = new FramedQuestion().save();
			frame.setOriginalQuestion(stillHere);
			frame.setPhrase("Sorry, are you still here?");
			frame.add(yes);
			frame.add(no);
			frame.save();

			anotherSadRound.add(frame);

			Question openEnded = uniqueQuestion("What is the answer to this open ended question?");
			openEnded.setAnswer(yes);
			openEnded.setCategory(general);
			openEnded.save();

			frame = new FramedQuestion().save();
			frame.setOriginalQuestion(openEnded);
			frame.setPhrase("What is the answer to this open ended question?");
			frame.save();

			anotherSadRound.add(frame);
			anotherSadRound.save();

			apologetic.add(oneSadRound);
			apologetic.add(anotherSadRound);
			apologetic.setCurrentRound(oneSadRound);

		}
		return apologetic;
	}

	public Category uniqueCategory(String category) {
		Category unique = categories.findByPropertyValue("name", category);
		if (unique == null) {
			unique = new Category(category);
			unique.save();
		}
		return unique;
	}

	public Answer uniqueAnswer(String answer) {
		Answer unique = answers.findByPropertyValue("text", answer);
		if (unique == null) {
			unique = new Answer(answer);
			unique.save();
		}
		return unique;
	}

	public Question uniqueQuestion(String question) {
		Question unique = questions.findByPropertyValue("text", question);
		if (unique == null) {
			unique = new Question(question);
			unique.save();
		}
		return unique;
	}

	public Collection<Score> getScores(Match match) {
		Map<Deck, Score> deckScoreMap = new HashMap<Deck, Score>();

		for (Deck deck : match.getDecks()) {
			Score newScore = new Score();
			newScore.setId(deck.getTeam().getId());
			String teamName = deck.getTeam().getName();
			if (teamName==null) teamName="anonymous";
			newScore.setName(teamName);
			newScore.setScore(0);
			deckScoreMap.put(deck, newScore);

			TraversalDescription traverseToAllMatchProposals = Traversal
					.description().depthFirst()
					.evaluator(Evaluators.excludeStartPosition())
					.relationships(Deck.DECK_TO_CARDS_REL, OUTGOING)
					.relationships(Card.CARD_TO_PROPOSAL_REL, OUTGOING).evaluator(Evaluators.atDepth(2));

			Iterable result = deck
					.findAllPathsByTraversal(traverseToAllMatchProposals);

			Iterable<EntityPath<Deck, Proposal>> proposalPaths = (Iterable<EntityPath<Deck, Proposal>>) result;

			for (EntityPath<Deck, Proposal> proposalPath : proposalPaths) {
				Proposal proposal = proposalPath.endEntity(Proposal.class);
				Score deckScore = deckScoreMap.get(deck);
				deckScore.accumulate(proposal.getScore());
			}

		}
		return deckScoreMap.values();
	}

	private void dumpNode(Node start) {
		System.out.println(start);
		for (String k : start.getPropertyKeys()) {
			System.out.println("\t" + k + " : " + start.getProperty(k));
		}
	}

	public void updateRoundAvailability(Round round, boolean isAvailable) {
		// TODO Auto-generated method stub

	}

	public void resetMatch(Match match) {
		match.setAvailable(false);
		match.setCurrentRound(null);
		for (Round r : match.getRounds()) {
			r.setAvailable(false);
			r.save();
		}
		match.save();
	}

	public void startMatch(Match match) {
		match.setAvailable(true);
		Round currentRound = null;
		for (Round r : match.getRounds()) {
			if (currentRound == null) {
				currentRound = r;
				currentRound.setAvailable(true);
				match.setCurrentRound(currentRound);
			} else {
				r.setAvailable(false);
			}
			r.save();
		}
		match.save();
	}

	public boolean stepMatch(Match match) {
		if (match.getAvailable()) {
			Round currentRound = match.getCurrentRound();
			if (currentRound != null) {
				close(currentRound);
				Round nextRound = findNext(match, currentRound);
				if (nextRound != null) {
					match.setCurrentRound(nextRound);
					startRound(nextRound);
					return true;
				}
				return false;
			}
		}
		return false;
	}

	private void startRound(Round round) {
		round.setAvailable(true);
		FramedQuestion currentQ = null;
		for (FramedQuestion q : round.getFramedQuestions()) {
			if (currentQ == null) {
				currentQ = q;
				currentQ.setAvailable(true);
				round.setCurrentQuestion(currentQ);
			} else {
				q.setAvailable(false);
			}
			q.save();
		}

	}

	private Round findNext(Match match, Round currentRound) {
		Round nextRound = null;
		boolean foundCurrent = false;
		for (Round r : match.getRounds()) {
			if (r.equals(currentRound)) {
				foundCurrent = true;
				continue;
			} else if (foundCurrent) {
				nextRound = r;
				break;
			}
		}
		return nextRound;
	}

	public boolean stepRound(Round round) {
		if (round.getAvailable()) {
			FramedQuestion currentQ = round.getCurrentQuestion();
			if (currentQ != null) {
				FramedQuestion nextQ = findNext(round, currentQ);
				if (nextQ != null) {
					nextQ.setAvailable(true);
					nextQ.save();
					round.setCurrentQuestion(nextQ);
					round.save();
					return true;
				}
				return false;
			}
		}
		return false;
	}

	private FramedQuestion findNext(Round round, FramedQuestion currentQuestion) {
		FramedQuestion nextQ = null;
		boolean foundCurrent = false;
		for (FramedQuestion fq : round.getFramedQuestions()) {
			if (fq.equals(currentQuestion)) {
				foundCurrent = true;
				continue;
			} else if (foundCurrent) {
				nextQ = fq;
				break;
			}
		}
		return nextQ;
	}

	public void close(Round round) {
		round.setAvailable(false);
		round.save();
		for (FramedQuestion fq : round.getFramedQuestions()) {
			fq.setAvailable(false);
			fq.save();
		}

	}

	public void grade(Proposal p) {
		Answer a = p.getFramedQuestion().getOriginalQuestion().getAnswer();
		String proposedAnswer = p.getProposedAnswer();
		if (proposedAnswer == null) proposedAnswer = "";
		if (proposedAnswer.equals(a.getText())) {
			p.setScore(p.getCard().getRound().getPointsPerQuestion());
		}
		 else {
			 p.setScore(0);
		 }
		p.save();
	}
}
