package hwr.oop.examples.template.service

import com.fasterxml.jackson.databind.ObjectMapper
import hwr.oop.examples.doppelkopf_2026.adapters.`in`.NewGameUseCase
import hwr.oop.examples.doppelkopf_2026.core.GameId
import hwr.oop.examples.doppelkopf_2026.ports.out.GameRepository
import hwr.oop.examples.template.FileSystemPersistence
import hwr.oop.examples.template.FileSystemPersistenceConfiguration
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = MOCK)
@ActiveProfiles("test")
class ServiceFileSystemTest {
	
	@TestConfiguration
	class Config {
		private val fakeFileSystem = FakeFileSystem()
		private val tempDir = "/tmp/service-fs-test".toPath()
		private val gameRepository: FileSystemPersistence = FileSystemPersistence(
			FileSystemPersistenceConfiguration(tempDir),
			fakeFileSystem.also { it.createDirectories(tempDir) }
		)
		
		@Bean
		@Primary
		fun persistence(): GameRepository = gameRepository
		
		@Bean
		fun newGameUseCase(gameRepository: GameRepository) = NewGameUseCase(
			saveGamePort = gameRepository
		)
	}
	
	@Autowired
	private lateinit var webApplicationContext: WebApplicationContext

	@Autowired
	private lateinit var gameRepository: GameRepository

	private lateinit var mockMvc: MockMvc
	
	@BeforeEach
	fun setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
	}
	
	@Test
	fun `create game returns 201 with game id, game is loadable from repository`() {
		val result = mockMvc.perform(
			post("/games")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""{"playerIds": ["p1", "p2", "p3", "p4"]}""")
		)
			.andExpect(status().isCreated)
			.andExpect(jsonPath("$.id").isNotEmpty)
			.andReturn()

		val id = ObjectMapper().readTree(result.response.contentAsString)["id"].asText()
		val game = gameRepository.loadByid(GameId(id))
		assertThat(game).isNotNull()
	}
	
}
