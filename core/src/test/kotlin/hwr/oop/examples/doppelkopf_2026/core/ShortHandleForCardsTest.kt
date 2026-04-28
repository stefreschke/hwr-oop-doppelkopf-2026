package hwr.oop.examples.doppelkopf_2026.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ShortHandleForCardsTest {
	
	val sut = CardFromStringConverter
	
	@ParameterizedTest
	@CsvSource(
		// normal cards
		"SD, SCHELLEN, DAUSS",
		"sd, SCHELLEN, DAUSS",
		"HK, HERZ, KOENIG",
		"hk, HERZ, KOENIG",
		"GU, GRUEN, UNTER",
		"gu, GRUEN, UNTER",
		"EO, EICHEL, OBER",
		"eo, EICHEL, OBER",
		// special cards
		"FUCHS, SCHELLEN, DAUSS",
		"fUcHs, SCHELLEN, DAUSS",
		"DULLE, HERZ, ZEHN",
		"DuLlE, HERZ, ZEHN",
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