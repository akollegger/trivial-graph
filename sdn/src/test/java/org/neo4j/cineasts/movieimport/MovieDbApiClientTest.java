package org.neo4j.cineasts.movieimport;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author mh
 * @since 13.03.11
 */
public class MovieDbApiClientTest {
    private static final String API_KEY = "926d2a79e82920b62f03b1cb57e532e6";

    @Test
    public void testGetMovie() throws Exception {
        Map movie = new MovieDbApiClient(API_KEY).getMovie("2");
        assertEquals(2,movie.get("id"));
    }

    @Test
    public void testGetPerson() throws Exception {
        Map person = new MovieDbApiClient(API_KEY).getPerson("30112");
        assertEquals(30112,person.get("id"));

    }
}
