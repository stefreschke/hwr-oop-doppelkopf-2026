package hwr.oop.examples.template.service

import hwr.oop.examples.doppelkopf_2026.adapters.`in`.NewGameUseCase
import hwr.oop.examples.template.service.api.GameReadApi
import hwr.oop.examples.template.service.api.GameWriteApi
import hwr.oop.examples.template.service.model.*
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(
	private val newGameUseCase: NewGameUseCase,
) : GameReadApi, GameWriteApi {
	
	override fun getGame(gameId: String?): ResponseEntity<GameResponse> {
		TODO("Not yet implemented")
	}
	
	override fun createGame(createGameRequest: @Valid CreateGameRequest?): ResponseEntity<GameCreatedResponse> {
		require(createGameRequest != null) { "required request body (CreateGameRequest) was null" }
		val command = with(RequestMapper) {
			createGameRequest.asCommand()
		}
		newGameUseCase.startGame(command)
		val response = GameCreatedResponse(command.gameId)
		return ResponseEntity.status(201).body(response)
	}
	
	override fun playCard(
		gameId: String?,
		playCardRequest: @Valid PlayCardRequest?,
	): ResponseEntity<GameResponse> {
		TODO("Not yet implemented")
	}
	
	override fun announce(
		gameId: String?,
		announcementRequest: @Valid AnnouncementRequest?,
	): ResponseEntity<GameResponse> {
		TODO("Not yet implemented")
	}
	
	override fun proposeContract(
		gameId: String?,
		proposeContractRequest: @Valid ProposeContractRequest?,
	): ResponseEntity<GameResponse> {
		TODO("Not yet implemented")
	}
	
	override fun passOnContractPropose(
		gameId: String?,
		passOnContractsRequest: @Valid PassOnContractsRequest?,
	): ResponseEntity<GameResponse> {
		TODO("Not yet implemented")
	}
	
	override fun acceptArmut(
		gameId: String?,
		acceptArmutRequest: @Valid AcceptArmutRequest?,
	): ResponseEntity<GameResponse> {
		TODO("Not yet implemented")
	}
	
}
