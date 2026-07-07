package hwr.oop.examples.template.service

import hwr.oop.examples.doppelkopf_2026.adapters.`in`.LoadGameByIdQuery
import hwr.oop.examples.doppelkopf_2026.adapters.`in`.NewGameUseCase
import hwr.oop.examples.doppelkopf_2026.adapters.`in`.PlayCardUseCase
import hwr.oop.examples.doppelkopf_2026.ports.out.GameRepository
import hwr.oop.examples.doppelkopf_2026.ports.out.LoadGameByIdPort
import hwr.oop.examples.doppelkopf_2026.ports.out.SaveGamePort
import hwr.oop.examples.template.FileSystemPersistence
import hwr.oop.examples.template.FileSystemPersistenceConfiguration
import hwr.oop.examples.template.SqlPersistence
import hwr.oop.examples.template.config.ConfigLoader
import hwr.oop.examples.template.config.PersistenceType
import okio.Path.Companion.toPath
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("!test")
class Config {
	
	private val appConfig = ConfigLoader.load()
	private val gamePersistence: GameRepository by lazy {
		when (appConfig.persistence) {
			PersistenceType.SQL -> SqlPersistence(
				appConfig.sql.jdbcUrl,
				appConfig.sql.username,
				appConfig.sql.password,
			)
			
			PersistenceType.FILE_SYSTEM -> FileSystemPersistence(
				configuration = FileSystemPersistenceConfiguration(
					directory = appConfig.fileSystem.directory.toPath()
				)
			)
		}
	}
	
	@Bean
	fun persistence(): GameRepository = gamePersistence
	
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