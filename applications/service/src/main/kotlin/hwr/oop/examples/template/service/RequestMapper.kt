package hwr.oop.examples.template.service

import hwr.oop.examples.doppelkopf_2026.adapters.`in`.NewGameUseCase
import hwr.oop.examples.template.service.model.CreateGameRequest
import java.util.UUID

object RequestMapper {
	fun CreateGameRequest.asCommand(): NewGameUseCase.Command {
		return NewGameUseCase.Command(
			gameId = UUID.randomUUID().toString(),
			playersIds = this.playerIds,
			withNine = this.withNines ?: false,
		)
	}

}