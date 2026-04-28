package hwr.oop.examples.doppelkopf_2026.core

data class MutableDeck(
	private val cards: MutableList<Card>,
) {
	fun draw(count: Int): List<Card> {
		val drawn = cards.take(count)
		(1..count).forEach { _ -> cards.removeFirst() }
		return drawn
	}
}