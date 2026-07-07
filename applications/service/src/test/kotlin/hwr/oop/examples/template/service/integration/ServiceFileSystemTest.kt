package hwr.oop.examples.template.service.integration

import hwr.oop.examples.doppelkopf_2026.adapters.`in`.LoadGameByIdQuery
import hwr.oop.examples.doppelkopf_2026.adapters.`in`.NewGameUseCase
import hwr.oop.examples.doppelkopf_2026.adapters.`in`.PlayCardUseCase
import hwr.oop.examples.doppelkopf_2026.ports.out.GameRepository
import hwr.oop.examples.doppelkopf_2026.ports.out.LoadGameByIdPort
import hwr.oop.examples.doppelkopf_2026.ports.out.SaveGamePort
import hwr.oop.examples.template.FileSystemPersistence
import hwr.oop.examples.template.FileSystemPersistenceConfiguration
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = MOCK)
@ActiveProfiles("test")
class ServiceFileSystemTest : AbstractIntegrationTest() {
	
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
		fun newGameUseCase(saveGameUseCase: SaveGamePort) = NewGameUseCase(
			saveGamePort = saveGameUseCase
		)
		
		@Bean
		fun playCardUseCase(saveGameUseCase: SaveGamePort, loadGameByIdPort: LoadGameByIdPort) = PlayCardUseCase(
			loadGameByIdPort = loadGameByIdPort,
			saveGamePort = saveGameUseCase,
		)
		
		@Bean
		fun loadGameByIdQuery(loadGameByIdPort: LoadGameByIdPort) = LoadGameByIdQuery(
			loadGameByIdPort = loadGameByIdPort,
		)
	}
	
}
