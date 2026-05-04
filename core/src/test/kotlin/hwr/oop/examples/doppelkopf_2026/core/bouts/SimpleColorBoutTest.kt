package hwr.oop.examples.doppelkopf_2026.core.bouts

import hwr.oop.examples.doppelkopf_2026.core.Bout
import hwr.oop.examples.doppelkopf_2026.core.CardFromStringConverter
import hwr.oop.examples.doppelkopf_2026.core.GameType
import hwr.oop.examples.doppelkopf_2026.core.PlayerId
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SimpleColorBoutTest {
	
	private val alpha = PlayerId("alpha")
	private val beta = PlayerId("beta")
	private val gamma = PlayerId("gamma")
	private val delta = PlayerId("delta")
	
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
	
	@Test
	fun `bout, only color, all spades, after three cards, is not finished`() {
		// given
		val bout = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		val (ace, king, ten) = CardFromStringConverter.convert("AS", "KS", "TS")
		// when
		val finishedBout = bout.put(ace).put(king).put(ten)
		// then
		val isFinished = finishedBout.isFinished()
		assertThat(isFinished).isFalse
	}
	
	@Test
	fun `bout, only color, all spades, after four cards, is finished`() {
		// given
		val bout = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		val (ace, king, ten) = CardFromStringConverter.convert("AS", "KS", "TS")
		// when
		val finishedBout = bout.put(ace).put(king).put(king).put(ten)
		// then
		val isFinished = finishedBout.isFinished()
		assertThat(isFinished).isTrue
	}
	
	@Test
	fun `bout, only color, all spades, put in fifth card, exception`() {
		// given
		val bout = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		val (ace, king, ten) = CardFromStringConverter.convert("AS", "KS", "TS")
		// when / then
		val afterFourCardsPut = bout.put(ace).put(king).put(king).put(ten)
		assertThatThrownBy { afterFourCardsPut.put(/* fifth card = */ ten) }
			.isInstanceOf(IllegalStateException::class.java)
			.hasMessageContaining("Bout is already finished")
	}
	
	@Test
	fun `bout, only color, knows which player put what`() {
		// given
		val bout = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		val (ace, king, ten) = CardFromStringConverter.convert("AS", "KS", "TS")
		
		// when
		val afterFirstCard = bout.put(ace)
		val afterSecondCard = afterFirstCard.put(king)
		val afterThirdCard = afterSecondCard.put(king)
		val afterFourthCard = afterThirdCard.put(ten)
		
		// then
		assertThat(bout.lastPlayer()).isNull()
		
		assertThat(afterFirstCard.lastPlayer())
			.isEqualTo(alpha)
			.isEqualTo(bout.nextPlayer())
		
		assertThat(afterSecondCard.lastPlayer())
			.isEqualTo(beta)
			.isEqualTo(afterFirstCard.nextPlayer())
		
		assertThat(afterThirdCard.lastPlayer())
			.isEqualTo(gamma)
			.isEqualTo(afterSecondCard.nextPlayer())
		
		assertThat(afterFourthCard.lastPlayer())
			.isEqualTo(delta)
			.isEqualTo(afterThirdCard.nextPlayer())
		
		assertThat(afterFourthCard.nextPlayer()).isNull()
	}
	
	@Test
	fun `bout, only color, all spades, incomplete bout, no winner`() {
		// given
		val beforeFirst = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		val (ace, king, ten) = CardFromStringConverter.convert("AS", "KS", "TS")
		// when
		val afterFirst = beforeFirst.put(ace)
		val afterSecond = afterFirst.put(king)
		// then
		assertThat(beforeFirst.winner())
			.isEqualTo(afterFirst.winner())
			.isEqualTo(afterSecond.winner())
			.isNull()
	}
	
	@Test
	fun `bout, only color, all spades, first ace by alpha, alpha is winner`() {
		// given
		val bout = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		val (ace, king, ten) = CardFromStringConverter.convert("AS", "KS", "TS")
		// when
		val afterWinningCardPlayed = bout.put(ace)
		val finishedBout = afterWinningCardPlayed.put(king).put(king).put(ten)
		// then
		val winner = finishedBout.winner()
		assertThat(winner)
			.isNotNull
			.isEqualTo(alpha)
			.isEqualTo(afterWinningCardPlayed.lastPlayer())
	}
	
	@Test
	fun `bout, only color, all spades, first ace by beta, beta is winner`() {
		// given
		val startingBout = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		val (ace, king, ten) = CardFromStringConverter.convert("AS", "KS", "TS")
		// when
		val afterWinningCardPlayed = startingBout.put(ten).put(ace)
		val finishedBout = afterWinningCardPlayed.put(ten).put(king)
		// then
		val winner = finishedBout.winner()
		assertThat(winner)
			.isNotNull
			.isEqualTo(beta)
			.isEqualTo(afterWinningCardPlayed.lastPlayer())
	}
	
	@Test
	fun `bout, only color, all spades, first ten by alpha, no card higher than ten, alpha is winner`() {
		// given
		val bout = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		val (king, ten) = CardFromStringConverter.convert("KS", "TS")
		// when
		val afterWinningCardPlayed = bout.put(ten)
		val finishedBout = afterWinningCardPlayed.put(king).put(king).put(ten)
		// then
		val winner = finishedBout.winner()
		assertThat(winner)
			.isNotNull
			.isEqualTo(alpha)
			.isEqualTo(afterWinningCardPlayed.lastPlayer())
	}
	
	@Test
	fun `bout, only color, all spades, first ten by gamma, no card higher than ten, gamma is winner`() {
		// given
		val bout = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		val (king, ten) = CardFromStringConverter.convert("KS", "TS")
		// when
		val afterWinningCardPlayed = bout.put(king).put(king).put(ten)
		val finishedBout = afterWinningCardPlayed.put(ten)
		// then
		val winner = finishedBout.winner()
		assertThat(winner)
			.isNotNull
			.isEqualTo(gamma)
			.isEqualTo(afterWinningCardPlayed.lastPlayer())
	}
	
	@Test
	fun `bout, only color, all spades, beta and delta strike, bout knows about leading player`() {
		// given
		val beforeFirstCard = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		val (ace, king, ten) = CardFromStringConverter.convert("AS", "KS", "TS")
		// when
		val afterFirstCard = beforeFirstCard.put(king)
		val afterSecondCard = afterFirstCard.put(ten)
		val afterThirdCard = afterSecondCard.put(ten)
		val finishedBout = afterThirdCard.put(ace)
		// then
		assertThat(beforeFirstCard.leader()).isNull()
		assertThat(afterFirstCard.leader()).isEqualTo(alpha)  // initial play
		assertThat(afterSecondCard.leader()).isEqualTo(beta)  // strike
		assertThat(afterThirdCard.leader()).isEqualTo(beta)
		assertThat(finishedBout.leader()).isEqualTo(delta).isEqualTo(finishedBout.winner())  // final strike
	}
}