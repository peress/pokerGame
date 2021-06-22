package com.appian.poker.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.appian.poker.Card;
import com.appian.poker.Deck;
import com.appian.poker.PokerController;
import com.appian.utils.Constants;

/**
 * 
 * @author rui.peres
 *
 */
//@SpringBootTest
@WebMvcTest({ PokerController.class, Deck.class })
public class PokerTests {

	@Autowired
	private Deck pokerGame;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void init() {
		pokerGame.init();
	}

	/**
	 * Test if two cards were swapped inside the array.
	 */
	@Test
	public void testSwappedCards() {
		Card d = pokerGame.getDeck()[1];

		assertEquals(pokerGame.getDeck()[1], pokerGame.getDeck()[1]);

		pokerGame.swapCards(1);

		assertNotEquals(d, pokerGame.getDeck()[1]);
	}

	/**
	 * Test constructor created Constants.TOTAL_AMOUNT_CARDS ammount of cards.
	 */
	@Test
	public void testConstructorAmountCards() {
		assertEquals(Constants.TOTAL_AMOUNT_CARDS, pokerGame.getDeck().length);
	}

	/**
	 * Test constructor has no 2 equal cards.
	 */
	@Test
	public void testConstructorNoDuplicateCards() {

		Card firstCard = pokerGame.getDeck()[0];

		for (int i = 1; i < Constants.TOTAL_AMOUNT_CARDS; i++) {
			assertNotEquals(firstCard, pokerGame.getDeck()[i]);
		}
	}

	/**
	 * Calls dealOneCard service one more time than cards available and expects an
	 * http nok status.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testExcessiveRequestsToDealOneCard() throws Exception {

		for (int i = 0; i < Constants.TOTAL_AMOUNT_CARDS; i++) {

			mockMvc.perform(MockMvcRequestBuilders.put("/shuffle")).andExpect(MockMvcResultMatchers.status().isOk())
					.andReturn();

			mockMvc.perform(MockMvcRequestBuilders.get("/dealOneCard")).andExpect(MockMvcResultMatchers.status().isOk())
					.andReturn();
		}

		assertEquals(Constants.TOTAL_AMOUNT_CARDS,
				Arrays.stream(pokerGame.getDeck()).filter((Card c) -> c.isDelivered()).count());

		mockMvc.perform(MockMvcRequestBuilders.put("/shuffle")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		mockMvc.perform(MockMvcRequestBuilders.get("/dealOneCard"))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

	}
}
