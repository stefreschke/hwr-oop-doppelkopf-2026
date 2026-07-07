package hwr.oop.examples.template.service

import hwr.oop.examples.doppelkopf_2026.adapters.`in`.LoadGameByIdQuery
import hwr.oop.examples.doppelkopf_2026.adapters.`in`.NewGameUseCase
import hwr.oop.examples.doppelkopf_2026.adapters.`in`.PlayCardUseCase
import hwr.oop.examples.doppelkopf_2026.core.InvalidNumberOfPlayersException
import hwr.oop.examples.doppelkopf_2026.core.PlayerId
import io.mockk.*
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class NewGameEndpointTest {
	
	private val newGameUseCase: NewGameUseCase = mockk()
	private val playCardUseCase: PlayCardUseCase = mockk()
	private val loadGameByIdQuery: LoadGameByIdQuery = mockk()
	private val mockMvc: MockMvc = MockMvcBuilders
		.standaloneSetup(Controller(newGameUseCase, playCardUseCase, loadGameByIdQuery))
		.setControllerAdvice(ControllerAdvice())
		.build()
	
	@Test
	fun `POST games delegates to use case and returns 201 with game id`() {
		val commandSlot = slot<NewGameUseCase.Command>()
		justRun { newGameUseCase.startGame(capture(commandSlot)) }
		
		mockMvc.perform(
			post("/games")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""{"playerIds": ["p1", "p2", "p3", "p4"]}""")
		)
			.andExpect(status().isCreated)
			.andExpect(jsonPath("$.id").isNotEmpty)
		
		verify(exactly = 1) { newGameUseCase.startGame(any()) }
		assertThat(commandSlot.captured.playersIds).containsExactly("p1", "p2", "p3", "p4")
	}
	
	@Test
	fun `POST games with invalid number of players returns 400`() {
		// given
		every { newGameUseCase.startGame(any()) } throws InvalidNumberOfPlayersException(
			listOf(PlayerId("p1"), PlayerId("p2"), PlayerId("p3"))
		)
		
		// when/then
		mockMvc.perform(
			post("/games")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""{"playerIds": ["p1", "p2", "p3"]}""")
		)
			.andExpect(status().isBadRequest)
			.andExpect(jsonPath("$.status", `is`(400001)))
			.andExpect(jsonPath("$.error", `is`("Invalid number of players provied")))
	}
	
	@Test
	fun `POST games when use case throws IllegalArgumentException returns 400`() {
		// given
		every { newGameUseCase.startGame(any()) } throws IllegalArgumentException("invalid argument")
		
		// when/then
		mockMvc.perform(
			post("/games")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""{"playerIds": ["p1", "p2", "p3", "p4"]}""")
		)
			.andExpect(status().isBadRequest)
			.andExpect(jsonPath("$.status", `is`(400000)))
			.andExpect(jsonPath("$.error", `is`("Bad Request")))
			.andExpect(jsonPath("$.message", `is`("invalid argument")))
	}
	
	@Test
	fun `POST games when use case throws IllegalStateException returns 500`() {
		// given
		every { newGameUseCase.startGame(any()) } throws IllegalStateException("invalid state")
		
		// when/then
		mockMvc.perform(
			post("/games")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""{"playerIds": ["p1", "p2", "p3", "p4"]}""")
		)
			.andExpect(status().isInternalServerError)
			.andExpect(jsonPath("$.status", `is`(500001)))
			.andExpect(jsonPath("$.error", `is`("Reached invalid state")))
			.andExpect(jsonPath("$.message", `is`("invalid state")))
	}
	
	@Test
	fun `POST games when use case throws unexpected exception returns 500`() {
		// given
		every { newGameUseCase.startGame(any()) } throws RuntimeException("unexpected error")
		
		// when/then
		mockMvc.perform(
			post("/games")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""{"playerIds": ["p1", "p2", "p3", "p4"]}""")
		)
			.andExpect(status().isInternalServerError)
			.andExpect(jsonPath("$.status", `is`(500000)))
			.andExpect(jsonPath("$.error", `is`("An unexpected error occurred")))
			.andExpect(jsonPath("$.message", `is`("unexpected error")))
	}
	
}