package hwr.oop.examples.doppelkopf_2026.adapters.`in`

import hwr.oop.examples.doppelkopf_2026.core.Rank
import hwr.oop.examples.doppelkopf_2026.core.Suit

data class PlayActionCommand(
	val gameId: String,
	val player: String,
	val suit: Suit,
	val rank: Rank,
)
