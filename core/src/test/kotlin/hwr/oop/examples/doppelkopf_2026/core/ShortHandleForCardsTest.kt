package hwr.oop.examples.doppelkopf_2026.core

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
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
	
	@Test
	fun `empty string, exception`() {
		// when / then
		assertThatThrownBy { with(sut) { "".asCard() } }
			.isInstanceOf(IllegalArgumentException::class.java)
			.hasMessageContaining("Card string must not be empty")
	}
	
	@Test
	fun `blank string, exception`() {
		// when / then
		assertThatThrownBy { with(sut) { "   ".asCard() } }
			.isInstanceOf(IllegalArgumentException::class.java)
			.hasMessageContaining("Card string must not be blank")
	}
	
	@Test
	fun `TH, hint to use DULLE instead`() {
		// when / then
		assertThatThrownBy { with(sut) { "TH".asCard() } }
			.isInstanceOf(IllegalArgumentException::class.java)
			.hasMessageContaining("DULLE")
	}
	
	@Test
	fun `AD, hint to use FUCHS instead`() {
		// when / then
		assertThatThrownBy { with(sut) { "AD".asCard() } }
			.isInstanceOf(IllegalArgumentException::class.java)
			.hasMessageContaining("FUCHS")
	}
	
	@Test
	fun `string not exactly two characters, exception`() {
		// when / then
		assertThatThrownBy { with(sut) { "ASS".asCard() } }
			.isInstanceOf(IllegalArgumentException::class.java)
			.hasMessageContaining("Card string must be exactly 2 characters long")
	}
	
	@Test
	fun `unknown suit character, exception`() {
		// when / then
		assertThatThrownBy { with(sut) { "AX".asCard() } }
			.isInstanceOf(IllegalArgumentException::class.java)
			.hasMessageContaining("Unknown suit")
	}
	
	@Test
	fun `unknown rank character, exception`() {
		// when / then
		assertThatThrownBy { with(sut) { "XS".asCard() } }
			.isInstanceOf(IllegalArgumentException::class.java)
			.hasMessageContaining("Unknown rank")
	}
}