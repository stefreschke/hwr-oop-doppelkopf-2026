package hwr.oop.examples.template.service

import hwr.oop.examples.template.service.api.GameReadApi
import hwr.oop.examples.template.service.api.GameWriteApi
import hwr.oop.examples.template.service.model.*
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller : GameReadApi, GameWriteApi {
	
	override fun getGame(gameId: String?): ResponseEntity<GameResponse> {
		TODO("Not yet implemented")
	}
	
	override fun createGame(createGameRequest: @Valid CreateGameRequest?): ResponseEntity<GameCreatedResponse> {
		TODO("Not yet implemented")
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
