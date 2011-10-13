package org.neo4j.app.trivialt.service;

import java.util.Collection;

import org.neo4j.app.trivialt.model.Answer;
import org.neo4j.app.trivialt.model.Category;
import org.neo4j.app.trivialt.model.FreeformTrivia;
import org.neo4j.app.trivialt.model.Player;
import org.neo4j.app.trivialt.model.Question;
import org.neo4j.app.trivialt.model.Team;
import org.neo4j.app.trivialt.repository.Answers;
import org.neo4j.app.trivialt.repository.Categories;
import org.neo4j.app.trivialt.repository.PlayerRepository;
import org.neo4j.app.trivialt.repository.Questions;
import org.neo4j.app.trivialt.repository.Teams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TrivialtWorld {


    @Autowired private Categories categories;
    @Autowired private Questions questions;
    @Autowired private Answers answers;
    @Autowired private PlayerRepository players;
    @Autowired private Teams teams;

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
		// TODO Auto-generated method stub
		return null;
	}

	public synchronized Player register(String playerHandle, String playerName) {
		Player unique = players.findByPropertyValue("handle", playerHandle);
		if (unique == null) {
			return players.save(new Player(playerHandle, playerName));
		} else {
			return null;
		}
	}

	public Player findPlayer(String handle) {
		return (Player) players.findByPropertyValue("handle", handle);
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

	public void draft(Player player, Team onTeam) {
		// TODO Auto-generated method stub
		
	}

	public void makeFriends(Player playerA, Player playerB) {
		playerA.addFriend(playerB);
	}

	public Team findTeam(String teamName) {
		return teams.findByPropertyValue("name", teamName);
	}

}
