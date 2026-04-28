package hwr.oop.examples.doppelkopf_2026.core

data class Hand(
	private val player: PlayerId,
	private val cards: List<Card>,
) {
	fun player(): PlayerId = player
	fun cards(): List<Card> = cards
}
