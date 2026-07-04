package hwr.oop.examples.template.service

import hwr.oop.examples.doppelkopf_2026.adapters.`in`.NewGameUseCase
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class NewGameEndpointTest {
	
	private val newGameUseCase: NewGameUseCase = mockk()
	private val mockMvc: MockMvc = MockMvcBuilders
		.standaloneSetup(Controller(newGameUseCase))
		.build()
	
	@Test
	fun `POST games delegates to use case and returns 201 with game id`() {
		justRun { newGameUseCase.startGame(any()) }
		
		mockMvc.perform(
			post("/games")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""{"playerIds": ["p1", "p2", "p3", "p4"]}""")
		)
			.andExpect(status().isCreated)
			.andExpect(jsonPath("$.id").isNotEmpty)
		
		verify(exactly = 1) { newGameUseCase.startGame(any()) }
	}
	
}