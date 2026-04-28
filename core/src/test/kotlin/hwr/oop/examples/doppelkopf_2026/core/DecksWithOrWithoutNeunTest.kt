package hwr.oop.examples.doppelkopf_2026.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DecksWithOrWithoutNeunTest {
	
	private val deckWithNeun = Deck.createRandom(
		includeNeun = true,
	)
	
	private val deckWithoutNeun = Deck.createRandom(
		includeNeun = false
	)
	
	@Test
	fun `with Neun, contains 48 cards`() {
		// when
		val cards = deckWithNeun.cards()
		// then
		assertThat(cards).hasSize(48)
	}
	
	@Test
	fun `with Neun, each card twice`() {
		// when
		val cards = deckWithNeun.cards()
		val distinct = cards.distinct()
		// then
		assertThat(distinct).hasSize(24).allMatch { card -> cards.count { it == card } == 2 }
	}
	
	@Test
	fun `with Neun, contains 8 neuns`() {
		// when
		val cards = deckWithNeun.cards()
		val neuns = cards.filter { it.rank() == Rank.NEUN }
		// then
		assertThat(neuns).hasSize(8)
	}
	
	@Test
	fun `without Neun, contains 40 cards`() {
		// when
		val cards = deckWithoutNeun.cards()
		// then
		assertThat(cards).hasSize(40)
	}
	
	@Test
	fun `without Neun, each card twice`() {
		// when
		val cards = deckWithoutNeun.cards()
		val distinct = cards.distinct()
		// then
		assertThat(distinct).hasSize(20).allMatch { card -> cards.count { it == card } == 2 }
	}
	
	@Test
	fun `without Neun, contains no neuns`() {
		// when
		val cards = deckWithoutNeun.cards()
		val neuns = cards.filter { it.rank() == Rank.NEUN }
		// then
		assertThat(neuns).isEmpty()
	}
}