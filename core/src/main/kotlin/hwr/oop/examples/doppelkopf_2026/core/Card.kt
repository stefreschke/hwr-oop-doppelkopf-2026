package hwr.oop.examples.doppelkopf_2026.core

import kotlinx.serialization.Serializable

@Serializable
data class Card(
	private val suit: Suit,
	private val rank: Rank,
) {
	fun suit(): Suit = suit
	fun rank(): Rank = rank
}
