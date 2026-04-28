package hwr.oop.examples.doppelkopf_2026.core

class Game(
	private val handsOfPlayers: List<Hand>,
	private val players: List<PlayerId> = handsOfPlayers.map { it.player() },
) {
	companion object {
		fun create(players: List<PlayerId>, withNeun: Boolean): Game {
			require(players.size == 4) { "Doppelkopf is always played with exactly 4 players" }
			val deck = Deck.createRandom(withNeun).toMutableDeck()
			val hands = buildHandsBasedOn(players, deck, withNeun)
			return Game(handsOfPlayers = hands)
		}
		
		private fun buildHandsBasedOn(
			players: List<PlayerId>,
			deck: MutableDeck,
			withNeun: Boolean,
		): List<Hand> {
			val steps = dealingStepsBasedOn(withNeun)
			val playerHandCards = players.associateWith { _ -> mutableListOf<Card>() }
			steps.forEach { step ->
				players.forEach { player ->
					val handCards = playerHandCards.getValue(player)
					val cardsDrawn = deck.draw(step)
					handCards.addAll(cardsDrawn)
				}
			}
			val hands = players.map { player ->
				val handCards = playerHandCards.getValue(player)
				Hand(player, handCards)
			}
			return hands
		}
		
		private fun dealingStepsBasedOn(withNeun: Boolean): List<Int> = if (withNeun) {
			listOf(4, 4, 4)
		} else {
			listOf(3, 4, 3)
		}
	}
	
	fun handOf(player: PlayerId): Hand {
		require(player in players) { "Player $player is not in players" }
		return handsOfPlayers.find { it.player() == player }!!
	}
	
}
