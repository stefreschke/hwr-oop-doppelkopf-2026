package hwr.oop.examples.template.service

import hwr.oop.examples.doppelkopf_2026.adapters.`in`.LoadGameByIdQuery
import hwr.oop.examples.doppelkopf_2026.adapters.`in`.NewGameUseCase
import hwr.oop.examples.doppelkopf_2026.adapters.`in`.PlayCardUseCase
import hwr.oop.examples.doppelkopf_2026.core.*
import hwr.oop.examples.doppelkopf_2026.ports.out.LoadGameByIdPort
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class GetGameEndpointTest {
	
	private val newGameUseCase: NewGameUseCase = mockk()
	private val playCardUseCase: PlayCardUseCase = mockk()
	private val loadGameByIdQuery: LoadGameByIdQuery = mockk()
	private val mockMvc: MockMvc = MockMvcBuilders
		.standaloneSetup(Controller(newGameUseCase, playCardUseCase, loadGameByIdQuery))
		.setControllerAdvice(ControllerAdvice())
		.build()
	
	private val gameId = GameId.random()
	private val p1 = PlayerId("p1")
	private val p2 = PlayerId("p2")
	private val p3 = PlayerId("p3")
	private val p4 = PlayerId("p4")
	private val game = Game(
		gameId = gameId,
		handsOfPlayers = listOf(
			Hand(p1, CardFromStringConverter.convert("AS", "DULLE")),
			Hand(p2, CardFromStringConverter.convert("KS", "DULLE")),
			Hand(p3, CardFromStringConverter.convert("TS", "FUCHS")),
			Hand(p4, CardFromStringConverter.convert("KS", "FUCHS")),
		),
		bouts = listOf(Bout(gameType = GameType.NORMAL, playerOrder = listOf(p1, p2, p3, p4))),
	)
	
	@Test
	fun `GET game returns 200 with game response`() {
		// given
		every { loadGameByIdQuery.loadGameById(gameId.value) } returns game
		
		// when/then
		mockMvc.perform(get("/games/${gameId.value}"))
			.andExpect(status().isOk)
			.andExpect(jsonPath("$.id", `is`(gameId.value)))
			.andExpect(jsonPath("$.bout.cards").isEmpty)
	}
	
	@Test
	fun `GET game when game not found returns 500`() {
		// given
		every { loadGameByIdQuery.loadGameById(any()) } throws LoadGameByIdPort.CouldNotLoadException(gameId)
		
		// when/then
		mockMvc.perform(get("/games/${gameId.value}"))
			.andExpect(status().isInternalServerError)
			.andExpect(jsonPath("$.status", `is`(500000)))
			.andExpect(jsonPath("$.error", `is`("An unexpected error occurred")))
	}
	
	@Test
	fun `GET game when query throws IllegalStateException returns 500`() {
		// given
		every { loadGameByIdQuery.loadGameById(any()) } throws IllegalStateException("invalid state")
		
		// when/then
		mockMvc.perform(get("/games/${gameId.value}"))
			.andExpect(status().isInternalServerError)
			.andExpect(jsonPath("$.status", `is`(500001)))
			.andExpect(jsonPath("$.error", `is`("Reached invalid state")))
			.andExpect(jsonPath("$.message", `is`("invalid state")))
	}
	
	@Test
	fun `GET game when query throws unexpected exception returns 500`() {
		// given
		every { loadGameByIdQuery.loadGameById(any()) } throws RuntimeException("unexpected error")
		
		// when/then
		mockMvc.perform(get("/games/${gameId.value}"))
			.andExpect(status().isInternalServerError)
			.andExpect(jsonPath("$.status", `is`(500000)))
			.andExpect(jsonPath("$.error", `is`("An unexpected error occurred")))
			.andExpect(jsonPath("$.message", `is`("unexpected error")))
	}
}
