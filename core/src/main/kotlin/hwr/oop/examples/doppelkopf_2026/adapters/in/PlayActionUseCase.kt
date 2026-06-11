package hwr.oop.examples.doppelkopf_2026.adapters.`in`

import hwr.oop.examples.doppelkopf_2026.core.GameId
import hwr.oop.examples.doppelkopf_2026.ports.out.LoadGameByIdPort
import hwr.oop.examples.doppelkopf_2026.ports.out.SaveGamePort

class PlayActionUseCase internal constructor(
	private val loadGamePort: LoadGameByIdPort,
	private val saveGamePort: SaveGamePort,
) {
	fun playAction(playActionCommand: PlayActionCommand) {
		val gameId = GameId(playActionCommand.gameId)
		val loadedGame = loadGamePort.loadByid(gameId)
		val updatedGame = TODO("domain logic on game")
		saveGamePort.save(updatedGame)
	}
	
}
