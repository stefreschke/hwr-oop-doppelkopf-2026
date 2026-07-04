package hwr.oop.examples.doppelkopf_2026.core

import kotlinx.serialization.Serializable

@Serializable
data class Game(
	private val gameId: GameId = GameId.random(),
	private val handsOfPlayers: List<Hand>,
	private val bouts: List<Bout>,
	private val players: List<PlayerId> = handsOfPlayers.map { it.player() },
) {
	// private val bouts = createNextBoutIfNecessary(bouts)
	private val activeBout = this.bouts.last()
	
	private fun createNextBoutIfNecessary(bouts: List<Bout>): List<Bout> {
		return if (bouts.isEmpty()) {
			listOf(
				Bout(gameType = GameType.NORMAL, playerOrder = players)
			)
		} else {
			val last = bouts.last()
			if (last.isFinished()) {
				bouts + Bout(
					gameType = GameType.NORMAL,
					playerOrder = last.nextPlayerOrder(),
				)
			} else {
				bouts
			}
		}
	}
	
	companion object {
		fun createRandomGame(
			players: List<PlayerId>,
			withNine: Boolean,
			gameId: GameId = GameId.random(),
		): Game {
			requireValidNumberOfPlayers(players)
			val deck = Deck.createRandom(withNine).toMutableDeck()
			val hands = buildHandsBasedOn(players, deck, withNine)
			val bouts = listOf(
				Bout(
					gameType = GameType.NORMAL,
					playerOrder = players,
				)
			)
			return Game(
				gameId = gameId,
				handsOfPlayers = hands,
				bouts = bouts
			)
		}
		
		private fun requireValidNumberOfPlayers(players: List<PlayerId>) {
			if (players.size != 4) {
				throw InvalidNumberOfPlayersException(players)
			}
		}
		
		private fun buildHandsBasedOn(
			players: List<PlayerId>,
			deck: MutableDeck,
			withNine: Boolean,
		): List<Hand> {
			val steps = dealingStepsBasedOn(withNine)
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
		
		private fun dealingStepsBasedOn(withNine: Boolean): List<Int> = if (withNine) {
			listOf(4, 4, 4)
		} else {
			listOf(3, 4, 3)
		}
	}
	
	fun handOf(player: PlayerId): Hand {
		require(player in players) { "Player $player is not in players" }
		return handsOfPlayers.find { it.player() == player }!!
	}
	
	fun activeBout() = activeBout
	
	fun id(): GameId = gameId
	
}
