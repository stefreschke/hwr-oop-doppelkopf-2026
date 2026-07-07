package hwr.oop.examples.template.service

import hwr.oop.examples.doppelkopf_2026.adapters.`in`.LoadGameByIdQuery
import hwr.oop.examples.doppelkopf_2026.adapters.`in`.NewGameUseCase
import hwr.oop.examples.doppelkopf_2026.adapters.`in`.PlayCardUseCase
import hwr.oop.examples.template.service.api.GameReadApi
import hwr.oop.examples.template.service.api.GameWriteApi
import hwr.oop.examples.template.service.model.*
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(
	private val newGameUseCase: NewGameUseCase,
	private val playCardUseCase: PlayCardUseCase,
	private val loadGameByIdQuery: LoadGameByIdQuery,
) : GameReadApi, GameWriteApi {
	
	override fun getGame(gameId: String?): ResponseEntity<GameResponse> {
		require(gameId != null) { "Game ID is null" }
		val loadedGame = loadGameByIdQuery.loadGameById(gameId)
		val response = with(ResponseMapper) {
			loadedGame.asGameResponse()
		}
		return ResponseEntity.ok(response)
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
		require(gameId != null) { "Game ID is null" }
		require(playCardRequest != null) { "required request body (PlayCardRequest) was null" }
		val command: PlayCardUseCase.Command = with(RequestMapper) {
			playCardRequest.asCommand(gameId)
		}
		playCardUseCase.playAction(command)
		return getGame(gameId)
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

