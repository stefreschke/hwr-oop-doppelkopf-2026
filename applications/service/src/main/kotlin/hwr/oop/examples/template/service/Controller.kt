package hwr.oop.examples.template.service

import hwr.oop.examples.template.service.api.GameReadApi
import hwr.oop.examples.template.service.api.GameWriteApi
import hwr.oop.examples.template.service.model.CreateGameRequest
import hwr.oop.examples.template.service.model.GameCreatedResponse
import hwr.oop.examples.template.service.model.GameResponse
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
	
}
