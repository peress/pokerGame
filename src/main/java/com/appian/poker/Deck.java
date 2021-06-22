package com.appian.poker;

import java.util.Random;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.appian.interfaces.CardSuites;
import com.appian.interfaces.CardValues;
import com.appian.utils.Constants;

@Component
public class Deck implements InitializingBean {

	private Card[] deck;

	public Card[] getDeck() {
		return deck;
	}

	public void setDeck(Card[] deck) {
		this.deck = deck;
	}
	
	/** 
	 * Generates a random number within range.
	 * @param lowestNr lower limit
	 * @return random int number
	 */
	private int generateRandomNr(int lowestNr) {
		return new Random().nextInt(Constants.TOTAL_AMOUNT_CARDS - lowestNr) + lowestNr;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.init();
	}

	/**
	 * Initiates deck with all cards.
	 */
	public void init() {
		deck = new Card[Constants.TOTAL_AMOUNT_CARDS];

		int i = 0;
		for (CardSuites suit : CardSuites.values()) {
			for (CardValues values : CardValues.values()) {
				deck[i] = new Card(suit, values, Boolean.FALSE);
				i++;
			}
		}
	}
	
	/**
	 * Swaps a card in the deck.
	 * @param idToBeSwapped id position to be randomly swapped.
	 */
	public void swapCards(int idToBeSwapped) {
		int indexNr = generateRandomNr(idToBeSwapped);
		Card c;
		
		c = deck[idToBeSwapped];
		deck[idToBeSwapped] = deck[indexNr];
		deck[indexNr] = c;
	}
}
