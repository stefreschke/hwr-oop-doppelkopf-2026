package hwr.oop.examples.doppelkopf_2026.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ShortHandleForCardsTest {
	
	private val sut = CardFromStringConverter
	
	@ParameterizedTest
	@CsvSource(
		// normal cards
		"AS, SPADES, ACE",
		"as, SPADES, ACE",
		"KH, HEARTS, KING",
		"kh, HEARTS, KING",
		"JD, DIAMONDS, JACK",
		"jd, DIAMONDS, JACK",
		"QC, CLUBS, QUEEN",
		"qc, CLUBS, QUEEN",
		// special cards
		"FUCHS, DIAMONDS, ACE",
		"fUcHs, DIAMONDS, ACE",
		"DULLE, HEARTS, TEN",
		"DuLlE, HEARTS, TEN",
	)
	fun `string correctly parsed to card`(inputString: String, suit: Suit, rank: Rank) {
		// when
		val card = with(sut) {
			inputString.asCard()
		}
		// then
		assertThat(card.suit()).isEqualTo(suit)
		assertThat(card.rank()).isEqualTo(rank)
	}
}