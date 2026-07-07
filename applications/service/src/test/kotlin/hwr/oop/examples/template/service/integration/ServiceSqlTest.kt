package hwr.oop.examples.template.service.integration

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import hwr.oop.examples.doppelkopf_2026.adapters.`in`.LoadGameByIdQuery
import hwr.oop.examples.doppelkopf_2026.adapters.`in`.NewGameUseCase
import hwr.oop.examples.doppelkopf_2026.adapters.`in`.PlayCardUseCase
import hwr.oop.examples.doppelkopf_2026.ports.out.GameRepository
import hwr.oop.examples.doppelkopf_2026.ports.out.LoadGameByIdPort
import hwr.oop.examples.doppelkopf_2026.ports.out.SaveGamePort
import hwr.oop.examples.template.SqlPersistence
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class ServiceSqlTest : AbstractIntegrationTest() {
	
	companion object {
		@Container
		@JvmStatic
		val postgres = PostgreSQLContainer("postgres:17-alpine")
	}
	
	@TestConfiguration
	class Config {
		private val gameRepository = SqlPersistence(
			HikariDataSource(HikariConfig().apply {
				jdbcUrl = postgres.jdbcUrl
				username = postgres.username
				password = postgres.password
			})
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

