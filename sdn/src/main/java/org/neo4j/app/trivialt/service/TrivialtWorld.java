package org.neo4j.app.trivialt.service;

import java.util.Collection;

import org.neo4j.app.trivialt.model.Answer;
import org.neo4j.app.trivialt.model.Category;
import org.neo4j.app.trivialt.model.FreeformTrivia;
import org.neo4j.app.trivialt.model.Match;
import org.neo4j.app.trivialt.model.Player;
import org.neo4j.app.trivialt.model.Question;
import org.neo4j.app.trivialt.model.Round;
import org.neo4j.app.trivialt.model.Team;
import org.neo4j.app.trivialt.repository.AnswerRepository;
import org.neo4j.app.trivialt.repository.CategoryRepository;
import org.neo4j.app.trivialt.repository.MatchRepository;
import org.neo4j.app.trivialt.repository.PlayerRepository;
import org.neo4j.app.trivialt.repository.QuestionRepository;
import org.neo4j.app.trivialt.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TrivialtWorld: a world of related trivia.
 * 
 * <p/>
 * Patterns:
 * <p/>
 * (category)-[:CATEGORIZES]->(questions)
 * <p/>
 * (question)-[:ANSWERED_BY]->(answer)
 * (question)-[:MISDIRECTED_BY]->(answer)*
 * <p/>
 * (player)-[:KNOWS]->(player)*
 * <p/>
 * (team)<-[:MEMBER]-(player)
 * (team)-[:ASSIGNED]->(cards)*
 * <p/>
 * (card)-[:CONTAINS]->(proposals)
 * (card)->[:SUBMITTED_TO]->(round)
 * <p/>
 * (round)-[:PRESENTS.order]->(frame)
 * <p/>
 * (frame)-[:PHRASES]->(question)
 * (frame)-[:OFFERS]->(answer)*
 * <p/>
 * (proposal)-[:IN_RESPONSE_TO]->(frame)
 * (proposal)-[:PROPOSES]->(answer)
 * <p/>
 * (match)-[:ROUND.order]->(round)
 * <p/>
 * (media)-[:RENDERS]->(frame)
 * (media-library)-[:STORES]->(media)
 *
 */
@Repository
@Transactional
public class TrivialtWorld {


    @Autowired private CategoryRepository categories;
    @Autowired private QuestionRepository questions;
    @Autowired private AnswerRepository answers;
    @Autowired private PlayerRepository players;
    @Autowired private TeamRepository teams;
    @Autowired private MatchRepository matches;

	public void learn(FreeformTrivia fq) {
		Answer a = createUniqueAnswer(fq.getAnswer());
		Question q = createUniqueQuestion(fq.getQuestion(), a);
		Category cat = createUniqueCategory(fq.getCategory());
		cat.include(q);
	}

	private synchronized Answer createUniqueAnswer(String answer) {
		Answer unique = answers.findByPropertyValue("text", answer);
		if (unique == null) {
			unique = answers.save(new Answer(answer));
		}
		return unique;
	}

	private synchronized Question createUniqueQuestion(String question, Answer a) {
		Question unique = questions.findByPropertyValue("text", question);
		if (unique == null) {
			unique = questions.save(new Question(question, a));
		}
		return unique;
	}

	private synchronized Category createUniqueCategory(String category) {
		Category unique = categories.findByPropertyValue("name", category);
		if (unique == null) {
			unique = categories.save(new Category(category));
		}
		return unique;
	}

	public Iterable<Category> getAllCategories() {
		return categories.findAll();
	}

	public Question findQuestion(String questionText) {
		return questions.findByPropertyValue("text", questionText);
	}


	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

	public Iterable<Question> getQuestionsInCategory(String category) {
		Iterable<Question> foundQuestions = null;
		Category foundCategory = categories.findByPropertyValue("name", category);
		if (foundCategory != null) {
			foundQuestions = foundCategory.getQuestion();
		}
		return foundQuestions;
	}

	public synchronized Player register(String playerHandle, String playerName) {
		Player unique = players.findByPropertyValue("handle", playerHandle);
		if (unique == null) {
			return players.save(new Player(playerHandle, playerName));
		} else {
			return null;
		}
	}

	public Player findPlayer(String byHandle) {
		return (Player) players.findByPropertyValue("handle", byHandle);
	}

	public synchronized Team establish(Player founder, String teamName, String secret) {
		Team unique = teams.findByPropertyValue("name", teamName);
		if (unique == null) {
			unique = teams.save(new Team(teamName, secret));
			unique.setFounder(founder);
		} else {
			return null;
		}
		return unique;
	}

	public boolean considerMembership(Player ofApplicant, Team forTeam, String usingSecret) {
		if (forTeam.confirmSecret(usingSecret))
		{
			draft(ofApplicant, forTeam);
			return true;
		}
		return false;
	}
	

	public void draft(Player player, Team onTeam) {
		onTeam.add(player);
	}

	public void makeFriends(Player playerA, Player playerB) {
		playerA.addFriend(playerB);
	}

	public Team findTeam(String byName) {
		return teams.findByPropertyValue("name", byName);
	}

	public synchronized Match createMatch(Player forPlayer, String withTitle) {
		Match unique = matches.findByPropertyValue("title", withTitle);
		if (unique == null) {
			unique = matches.save(new Match(forPlayer, withTitle));
			unique.addRound("Answer me this...");
		} else {
			return null;
		}
		return unique;
	}

	public Match findMatch(String byTitle) {
		return matches.findByPropertyValue("title", byTitle);
	}

	public void addRound(Player forPlayer, Match toMatch, String titled) {
		if(toMatch.getTriviaMaster().equals(forPlayer))
		{
			toMatch.addRound(titled);
		}
	}

}
