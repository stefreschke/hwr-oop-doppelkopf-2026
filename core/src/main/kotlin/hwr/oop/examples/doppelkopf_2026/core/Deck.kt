package hwr.oop.examples.doppelkopf_2026.core

data class Deck(
	private val cards: List<Card>,
) {
	fun cards(): List<Card> = cards
	
	fun toMutableDeck(): MutableDeck = MutableDeck(cards.toMutableList())
	
	companion object {
		fun createRandom(includeNine: Boolean): Deck = Deck(
			(1..2).flatMap {
				Suit.entries.flatMap { suit ->
					Rank.entries.filter { includeNine || it != Rank.NINE }.map { rank -> Card(suit, rank) }
				}
			}.shuffled()
		)
	}
}

