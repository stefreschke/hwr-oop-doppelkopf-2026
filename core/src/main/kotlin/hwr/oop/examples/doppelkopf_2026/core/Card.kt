package hwr.oop.examples.doppelkopf_2026.core

class Card(
	private val suit: Suit,
	private val rank: Rank
) {
	fun suit(): Suit {
		return suit
	}
	
	fun rank(): Rank {
		return rank
	}
}
