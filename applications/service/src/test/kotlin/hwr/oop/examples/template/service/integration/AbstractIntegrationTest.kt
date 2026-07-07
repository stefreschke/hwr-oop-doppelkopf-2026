package hwr.oop.examples.template.service.integration

import com.jayway.jsonpath.JsonPath
import hwr.oop.examples.doppelkopf_2026.core.*
import hwr.oop.examples.doppelkopf_2026.ports.out.GameRepository
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
abstract class AbstractIntegrationTest {
	
	@Autowired
	protected lateinit var webApplicationContext: WebApplicationContext
	
	@Autowired
	protected lateinit var gameRepository: GameRepository
	
	protected lateinit var mockMvc: MockMvc
	
	@BeforeEach
	fun setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
	}
	
	@Test
	fun `create game returns 201 with game id, game is loadable from repository`() {
		// when
		val result = mockMvc.perform(
			post("/games")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					"""
					{
					  "playerIds": ["p1", "p2", "p3", "p4"]
					}
					""".trimIndent()
				)
		)
			.andExpect(status().isCreated)
			.andExpect(jsonPath("$.id").isNotEmpty)
			.andReturn()
		
		// then
		val id = JsonPath.read<String>(result.response.contentAsString, "$.id")
		val game = gameRepository.loadByid(GameId(id))
		assertThat(game).isNotNull()
	}
	
	@Test
	fun `get game returns 200 with game, game is loadable from repository`() {
		// given
		val gameId = GameId.random()
		val p1 = PlayerId("p1")
		val p2 = PlayerId("p2")
		val p3 = PlayerId("p3")
		val p4 = PlayerId("p4")
		val game = Game(
			gameId = gameId,
			handsOfPlayers = listOf(
				Hand(p1, CardFromStringConverter.convert("AS", "DULLE")),
				Hand(p2, CardFromStringConverter.convert("KS", "DULLE")),
				Hand(p3, CardFromStringConverter.convert("TS", "FUCHS")),
				Hand(p4, CardFromStringConverter.convert("KS", "FUCHS")),
			),
			bouts = listOf(Bout(gameType = GameType.NORMAL, playerOrder = listOf(p1, p2, p3, p4))),
		)
		gameRepository.save(game)
		
		// when/then
		mockMvc.perform(get("/games/${gameId.value}"))
			.andExpect(status().isOk)
			.andExpect(jsonPath("$.id", `is`(gameId.value)))
			.andExpect(jsonPath("$.bout.cards").isEmpty)
	}
	
	@Test
	@Disabled
	fun `play card returns 200 with updated game, game is updated in repository`() {
		// given
		val gameId = GameId.random()
		val p1 = PlayerId("p1")
		val p2 = PlayerId("p2")
		val p3 = PlayerId("p3")
		val p4 = PlayerId("p4")
		val game = Game(
			gameId = gameId,
			handsOfPlayers = listOf(
				Hand(p1, CardFromStringConverter.convert("AS", "DULLE")),
				Hand(p2, CardFromStringConverter.convert("KS", "DULLE")),
				Hand(p3, CardFromStringConverter.convert("TS", "FUCHS")),
				Hand(p4, CardFromStringConverter.convert("KS", "FUCHS")),
			),
			bouts = listOf(Bout(gameType = GameType.NORMAL, playerOrder = listOf(p1, p2, p3, p4))),
		)
		gameRepository.save(game)
		
		// when/then
		mockMvc.perform(
			post("/games/${gameId.value}/play")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					"""
					{
					  "playerId": "p1",
					  "card": {
					    "suit": "SPADES",
					    "rank": "ACE"
					  }
					}
					""".trimIndent()
				)
		)
			.andExpect(status().isOk)
			.andExpect(jsonPath("$.bout.cards[0].suit", `is`("SPADES")))
			.andExpect(jsonPath("$.bout.cards[0].rank", `is`("ACE")))
		
		val updatedGame = gameRepository.loadByid(gameId)
		assertThat(updatedGame).isNotEqualTo(game)
	}
}