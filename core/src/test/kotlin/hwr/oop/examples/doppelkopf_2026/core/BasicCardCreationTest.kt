package hwr.oop.examples.doppelkopf_2026.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class BasicCardCreationTest {
	
	@Test
	fun `all ranks exist`() {
		// given
		val ranks = Rank.entries
		// when
		// then
		assertThat(ranks).containsExactlyInAnyOrder(
			Rank.NINE,
			Rank.TEN,
			Rank.JACK,
			Rank.QUEEN,
			Rank.KING,
			Rank.ACE,
		)
	}
	
	@ParameterizedTest
	@EnumSource(Suit::class)
	fun `all ranks, each suit can exist`(suit: Suit) {
		// given
		val allRanks = Rank.entries
		
		// when
		val cards = allRanks.map { Card(suit, it) }
		
		// then
		assertThat(cards)
			.hasSize(allRanks.size)
			.allMatch { it.suit() == suit }
		
		val ranks = cards.map { it.rank() }
		assertThat(ranks).containsExactlyInAnyOrderElementsOf(allRanks)
	}
	
	@Test
	fun `all suits exist`() {
		// given
		val suits = Suit.entries
		// when
		// then
		assertThat(suits).containsExactlyInAnyOrder(
			Suit.SPADES,
			Suit.HEARTS,
			Suit.DIAMONDS,
			Suit.CLUBS
		)
	}
	
	@ParameterizedTest
	@EnumSource(Rank::class)
	fun `all suits, each rank can exist`(rank: Rank) {
		// given
		val allSuits = Suit.entries
		
		// when
		val cards = allSuits.map { Card(it, rank) }
		
		// then
		assertThat(cards)
			.hasSize(4)
			.allMatch { it.rank() == rank }
		
		val suits = cards.map { it.suit() }
		assertThat(suits).containsExactlyInAnyOrderElementsOf(allSuits)
	}
	
}