package org.neo4j.app.trivialt.model;

public class FreeformTrivia {
    private String question;
    private String answer;
    private String category;


    public FreeformTrivia( String category, String question, String answer )
    {
        this.category = category;
        this.question = question;
        this.answer = answer;
    }

    public String getAnswer()
    {
        return answer;
    }

    public String getCategory()
    {
        return category;
    }

    public String getQuestion()
    {
        return question;
    }

}
