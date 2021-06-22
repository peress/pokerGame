package com.appian.poker;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.appian.utils.Constants;

/**
 * Class exposing poker rest api.
 * 
 * @author rui.peres
 *
 */
@RestController
public class PokerController {

	/** Holds deck data structure.*/ 
	@Autowired
	private Deck pokerGame;

	private final static Logger LOGGER = Logger.getLogger(PokerController.class.getName());

	/**
	 * Returns a card available on deck.
	 * 
	 * @return HTTP 200 - if a card is successfully delivered HTTP 404 - if no more
	 *         cards available to be delivered
	 * 
	 */
	@GetMapping(value = "/dealOneCard")
	@ResponseBody
	public ResponseEntity<Card> dealOneCard() {
		Optional<Card> dealtCard;

		try {

			dealtCard = Arrays.stream(this.pokerGame.getDeck()).filter((Card card) -> !card.isDelivered()).findAny();

			dealtCard.get().setDelivered(Boolean.TRUE);

			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.log(Level.INFO, dealtCard.get().toString());
			}

		} catch (NullPointerException | NoSuchElementException e) {
			return new ResponseEntity<Card>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Card>(dealtCard.get(), HttpStatus.OK);
	}

	/**
	 * Shuffles all the cards on the deck.
	 * @return HTTP 200 - if deck was successfully shuffled
	 */
	@PutMapping(value = "/shuffle")
	public ResponseEntity<Object> shuffle() {

		for (int swapId = 0; swapId < Constants.TOTAL_AMOUNT_CARDS; swapId++) {
			if (!this.pokerGame.getDeck()[swapId].isDelivered()) {
				this.pokerGame.swapCards(swapId);
			}
		}
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.log(Level.INFO, "Deck shuffled");
		}

		if (LOGGER.isLoggable(Level.FINEST)) {
			for (Card card : this.pokerGame.getDeck()) {
				LOGGER.log(Level.FINEST, card.toString());
			}
		}

		return ResponseEntity.ok().build();
	}

}
