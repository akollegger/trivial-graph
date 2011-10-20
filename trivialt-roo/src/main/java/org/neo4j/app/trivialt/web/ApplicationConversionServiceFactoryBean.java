package org.neo4j.app.trivialt.web;

import org.neo4j.app.trivialt.model.Answer;
import org.neo4j.app.trivialt.model.Card;
import org.neo4j.app.trivialt.model.Deck;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.RooConversionService;

/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
        registry.addConverter(new CardConverter());
        registry.addConverter(new DeckConverter());
		
	}

	static class CardConverter implements Converter<Card, String> {
		public String convert(Card card) {
			return new StringBuilder().append("card_").append(card.getId()).toString();
		}
	}

	static class DeckConverter implements Converter<Deck, String> {
		public String convert(Deck deck) {
			return new StringBuilder().append("deck_").append(deck.getId()).toString();
		}
	}
}
