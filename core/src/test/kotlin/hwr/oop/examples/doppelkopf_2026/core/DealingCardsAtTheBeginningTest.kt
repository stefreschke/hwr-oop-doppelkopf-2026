package hwr.oop.examples.doppelkopf_2026.core

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class DealingCardsAtTheBeginningTest {
	
	private val alpha = PlayerId("alpha")
	private val beta = PlayerId("beta")
	private val gamma = PlayerId("gamma")
	private val delta = PlayerId("delta")
	private val players = listOf(alpha, beta, gamma, delta)
	
	@ParameterizedTest
	@CsvSource(
		"true, 12",
		"false, 10",
	)
	fun `create new Game, each player has correct hand size`(withNine: Boolean, expectedNumberOfCards: Int) {
		// given
		val game = Game.create(
			players = players,
			withNine = withNine,
		)
		
		// when
		val hands = players.map { player ->
			game.handOf(player)
		}
		
		// then
		assertThat(hands).hasSize(4).allMatch { it.cards().size == expectedNumberOfCards }
	}
	
	@ParameterizedTest
	@ValueSource(booleans = [true, false])
	fun `create new Game, each card exists twice across all players`(withNine: Boolean) {
		// given
		val game = Game.create(
			players = players,
			withNine = withNine,
		)
		
		// when
		val hands = players.map { player ->
			game.handOf(player)
		}
		
		val allCards = hands.flatMap { it.cards() }
		val allCardsDistinct = allCards.distinct()
		
		// then
		assertThat(allCardsDistinct).allSatisfy { card ->
			val equalCards = allCards.filter { it == card }
			assertThat(equalCards).hasSize(2)
		}
	}
	
	@ParameterizedTest
	@ValueSource(ints = [0, 3, 5])
	fun `invalid number of players, exception`(invalidNumberOfPlayers: Int) {
		// when
		val players = (1..invalidNumberOfPlayers).map { PlayerId("player$it") }
		assertThatThrownBy {
			Game.create(
				players = players,
				withNine = true,
			)
		}.hasMessageContaining("exactly 4 players")
	}
	
}