package hwr.oop.examples.doppelkopf_2026.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DecksWithOrWithoutNineTest {
	
	private val deckWithNine = Deck.createRandom(
		includeNine = true,
	)
	
	private val deckWithoutNine = Deck.createRandom(
		includeNine = false
	)
	
	@Test
	fun `with Nine, contains 48 cards`() {
		// when
		val cards = deckWithNine.cards()
		// then
		assertThat(cards).hasSize(48)
	}
	
	@Test
	fun `with Nine, each card twice`() {
		// when
		val cards = deckWithNine.cards()
		val distinct = cards.distinct()
		// then
		assertThat(distinct).hasSize(24).allMatch { card -> cards.count { it == card } == 2 }
	}
	
	@Test
	fun `with Nine, contains 8 nines`() {
		// when
		val cards = deckWithNine.cards()
		val nines = cards.filter { it.rank() == Rank.NINE }
		// then
		assertThat(nines).hasSize(8)
	}
	
	@Test
	fun `without Nine, contains 40 cards`() {
		// when
		val cards = deckWithoutNine.cards()
		// then
		assertThat(cards).hasSize(40)
	}
	
	@Test
	fun `without Nine, each card twice`() {
		// when
		val cards = deckWithoutNine.cards()
		val distinct = cards.distinct()
		// then
		assertThat(distinct).hasSize(20).allMatch { card -> cards.count { it == card } == 2 }
	}
	
	@Test
	fun `without Nine, contains no nines`() {
		// when
		val cards = deckWithoutNine.cards()
		val nines = cards.filter { it.rank() == Rank.NINE }
		// then
		assertThat(nines).isEmpty()
	}
}