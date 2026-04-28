package hwr.oop.examples.doppelkopf_2026.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DoppelkopfTest {
	
	@Test
	fun `all suits, aces can exist`() {
		// given
		val allSuits = listOf(
			Suit.SPADES,
			Suit.HEARTS,
			Suit.DIAMONDS,
			Suit.CLUBS
		)
		// when
		val mutableListOfCards = mutableListOf<Card>()
		for (suit in allSuits) {
			val ace = Card(suit, Rank.ACE)
			mutableListOfCards.add(ace)
		}
		// then
		assertThat(mutableListOfCards)
			.hasSize(4)
			.allMatch { it.rank() == Rank.ACE }
		
		val suits = mutableListOfCards.map { it.suit() }
		assertThat(suits).containsExactlyInAnyOrderElementsOf(allSuits)
	}
	
	@Test
	fun `card Pik-Ass can exist`() {
		// given
		// when
		val card = Card(
			suit = Suit.SPADES,
			rank = Rank.ACE
		)
		// then
		val suit = card.suit()
		val rank = card.rank()
		assertThat(suit).isEqualTo(Suit.SPADES)
		assertThat(rank).isEqualTo(Rank.ACE)
	}
	
	@Test
	fun `card Kreuz-Ass can exist`() {
		// given
		// when
		val card = Card(
			suit = Suit.CLUBS,
			rank = Rank.ACE
		)
		// then
		val suit = card.suit()
		val rank = card.rank()
		assertThat(suit).isEqualTo(Suit.CLUBS)
		assertThat(rank).isEqualTo(Rank.ACE)
	}
	
	@Test
	fun `card Herz-Ass can exist`() {
		// given
		// when
		val card = Card(
			suit = Suit.HEARTS,
			rank = Rank.ACE
		)
		// then
		val suit = card.suit()
		val rank = card.rank()
		assertThat(suit).isEqualTo(Suit.HEARTS)
		assertThat(rank).isEqualTo(Rank.ACE)
	}
	
	@Test
	fun `card Karo-Ass can exist`() {
		// given
		// when
		val card = Card(
			suit = Suit.DIAMONDS,
			rank = Rank.ACE
		)
		// then
		val suit = card.suit()
		val rank = card.rank()
		assertThat(suit).isEqualTo(Suit.DIAMONDS)
		assertThat(rank).isEqualTo(Rank.ACE)
	}
}