package com.appian.poker;

import com.appian.interfaces.CardSuites;
import com.appian.interfaces.CardValues;

public class Card {

	private CardSuites suit;
	private CardValues rank;
	private boolean delivered;

	public Card(CardSuites suit, CardValues rank, boolean delivered) {
		super();
		this.suit = suit;
		this.rank = rank;
		this.delivered = delivered;
	}
	

	public Card() {
		super();
	}

	public CardSuites getSuit() {
		return suit;
	}

	public void setSuit(CardSuites suit) {
		this.suit = suit;
	}

	public CardValues getRank() {
		return rank;
	}

	public void setRank(CardValues rank) {
		this.rank = rank;
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

	@Override
	public String toString() {
		return "Card [suit=" + suit + ", rank=" + rank + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		final Card otherCard = (Card) obj;
		if ((this.suit == null) ? (otherCard.suit != null) : !this.suit.equals(otherCard.suit)) {
			return false;
		}

		if (this.rank != otherCard.rank) {
			return false;
		}

		return true;
	}

}
