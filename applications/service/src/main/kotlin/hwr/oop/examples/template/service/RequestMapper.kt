package hwr.oop.examples.template.service

import hwr.oop.examples.doppelkopf_2026.adapters.`in`.NewGameUseCase
import hwr.oop.examples.doppelkopf_2026.adapters.`in`.PlayCardUseCase
import hwr.oop.examples.template.service.model.CreateGameRequest
import hwr.oop.examples.template.service.model.PlayCardRequest
import java.util.*

object RequestMapper {
	fun CreateGameRequest.asCommand() = NewGameUseCase.Command(
		gameId = UUID.randomUUID().toString(),
		playersIds = this.playerIds,
		withNine = this.withNines ?: false,
	)
	
	fun PlayCardRequest.asCommand(gameId: String) = PlayCardUseCase.Command(
		gameId = gameId,
		player = this.playerId,
		suit = this.card.suit,
		rank = this.card.rank,
	)
	
}