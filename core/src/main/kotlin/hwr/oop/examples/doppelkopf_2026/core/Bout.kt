package hwr.oop.examples.doppelkopf_2026.core

data class Bout(
	private val gameType: GameType,
	private val playerOrder: List<PlayerId>,
	private val cards: List<Card> = emptyList(),
	private val cardToBeat: Card? = null,
	private val leadingPlayer: PlayerId? = null,
) {
	private val comparator: Comparator<Card> = gameType.comparator()
	
	init {
		require(playerOrder.size == 4) { "Player order must have 4 players" }
		require(playerOrder.toSet().size == 4) { "Player order must have 4 different players" }
	}
	
	// command
	fun put(card: Card): Bout {
		check(cards.size < 4) { "Bout is already finished, cannot put more cards" }
		val newCardToBeat = updatedCardToBeat(card)
		val newLeadingPlayer = updatedLeadingPlayer(newCardToBeat)
		return copy(
			cards = cards + card,
			cardToBeat = newCardToBeat,
			leadingPlayer = newLeadingPlayer,
		)
	}
	
	// query
	fun isFinished(): Boolean = cards.size == 4
	
	fun nextPlayer(): PlayerId? = if (cards.size >= 4) null else playerOrder[cards.size]
	
	fun lastPlayer(): PlayerId? = if (cards.isEmpty()) null else playerOrder[cards.size - 1]
	
	fun winner(): PlayerId? = if (cards.size == 4) leadingPlayer else null
	
	// methods
	private fun updatedCardToBeat(card: Card): Card =
		if (cardToBeat == null) card else pickStrongestCard(card, cardToBeat)
	
	private fun pickStrongestCard(
		card: Card,
		cardToBeat: Card,
	): Card = comparator.compare(card, cardToBeat).let { if (it > 0) card else cardToBeat }
	
	private fun updatedLeadingPlayer(newCardToBeat: Card): PlayerId? =
		if (newCardToBeat != cardToBeat) nextPlayer() else leadingPlayer
}
