package hwr.oop.examples.doppelkopf_2026.core

data class Bout(
	val cards: List<Card> = emptyList(),
	val gameType: GameType,
	val playerOrder: List<PlayerId>,
) {
	init {
		require(playerOrder.size == 4) { "Player order must have 4 players" }
		require(playerOrder.toSet().size == 4) { "Player order must have 4 different players" }
	}
	
	// command
	fun put(card: Card): Bout {
		check(cards.size < 4) { "Bout is already finished, cannot put more cards" }
		return copy(
			cards = cards + card,
		)
	}
	
	// query
	fun isFinished(): Boolean = cards.size == 4
	
	fun nextPlayer(): PlayerId? = if (cards.size >= 4) null else playerOrder[cards.size]
	
	fun lastPlayer(): PlayerId? = if (cards.isEmpty()) null else playerOrder[cards.size - 1]
	
	fun winner(): PlayerId? = if (cards.size == 4) playerOrder.firstOrNull() else null
}
