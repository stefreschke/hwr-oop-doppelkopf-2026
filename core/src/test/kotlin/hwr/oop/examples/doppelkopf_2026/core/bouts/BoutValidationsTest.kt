package hwr.oop.examples.doppelkopf_2026.core.bouts

import hwr.oop.examples.doppelkopf_2026.core.Bout
import hwr.oop.examples.doppelkopf_2026.core.GameType
import hwr.oop.examples.doppelkopf_2026.core.PlayerId
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.intArrayOf

class BoutValidationsTest {
	
	@ParameterizedTest
	@ValueSource(ints = [0, 3, 5])
	fun `bout, invalid number of players in order, exception`(invalidNumberOfPlayers: Int) {
		// given
		val players = (1..invalidNumberOfPlayers).map { PlayerId("player$it") }
		// when / then
		assertThatThrownBy { Bout(gameType = GameType.NORMAL, playerOrder = players) }
			.isInstanceOf(IllegalArgumentException::class.java)
			.hasMessageContaining("Player order must have 4 players")
	}
	
	@Test
	fun `bout, duplicate players, exception`() {
		// given
		val players = listOf(PlayerId("alpha"), PlayerId("beta"), PlayerId("beta"), PlayerId("delta"))
		// when / then
		assertThatThrownBy { Bout(gameType = GameType.NORMAL, playerOrder = players) }
			.isInstanceOf(IllegalArgumentException::class.java)
			.hasMessageContaining("Player order must have 4 different players")
	}
}