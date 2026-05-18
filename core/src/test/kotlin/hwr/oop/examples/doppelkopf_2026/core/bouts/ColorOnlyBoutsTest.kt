package hwr.oop.examples.doppelkopf_2026.core.bouts

import hwr.oop.examples.doppelkopf_2026.core.Bout
import hwr.oop.examples.doppelkopf_2026.core.CardFromStringConverter
import hwr.oop.examples.doppelkopf_2026.core.GameType
import hwr.oop.examples.doppelkopf_2026.core.PlayerId
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class ColorOnlyBoutsTest {
	
	// players
	private val alpha = PlayerId("alpha")
	private val beta = PlayerId("beta")
	private val gamma = PlayerId("gamma")
	private val delta = PlayerId("delta")
	
	// required cards
	private val ace = CardFromStringConverter.convert("AS").first()
	private val ten = CardFromStringConverter.convert("TS").first()
	private val king = CardFromStringConverter.convert("KS").first()
	private val nine = CardFromStringConverter.convert("NS").first()
	
	@Test
	fun `after three cards, is not finished`() {
		// given
		val bout = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		// when
		val finishedBout = bout.put(ace).put(king).put(ten)
		// then
		val isFinished = finishedBout.isFinished()
		assertThat(isFinished).isFalse
	}
	
	@Test
	fun `after four cards, is finished`() {
		// given
		val bout = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		// when
		val finishedBout = bout.put(ace).put(king).put(king).put(ten)
		// then
		val isFinished = finishedBout.isFinished()
		assertThat(isFinished).isTrue
	}
	
	@Test
	fun `put in fifth card, exception`() {
		// given
		val bout = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		// when / then
		val afterFourCardsPut = bout.put(ace).put(king).put(king).put(ten)
		assertThatThrownBy { afterFourCardsPut.put(/* fifth card = */ ten) }.isInstanceOf(IllegalStateException::class.java)
			.hasMessageContaining("Bout is already finished")
	}
	
	@Test
	fun `after all cards played, intermediate states know next and last players`() {
		// given
		val bout = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		// when
		val afterFirstCard = bout.put(ace)
		val afterSecondCard = afterFirstCard.put(king)
		val afterThirdCard = afterSecondCard.put(king)
		val afterFourthCard = afterThirdCard.put(ten)
		// then
		assertThat(bout.lastPlayer()).isNull()
		assertThat(afterFirstCard.lastPlayer()).isEqualTo(alpha).isEqualTo(bout.nextPlayer())
		assertThat(afterSecondCard.lastPlayer()).isEqualTo(beta).isEqualTo(afterFirstCard.nextPlayer())
		assertThat(afterThirdCard.lastPlayer()).isEqualTo(gamma).isEqualTo(afterSecondCard.nextPlayer())
		assertThat(afterFourthCard.lastPlayer()).isEqualTo(delta).isEqualTo(afterThirdCard.nextPlayer())
		assertThat(afterFourthCard.nextPlayer()).isNull()
	}
	
	@Test
	fun `incomplete bout, no winner`() {
		// given
		val beforeFirst = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		// when
		val afterFirst = beforeFirst.put(ace)
		val afterSecond = afterFirst.put(king)
		// then
		assertThat(beforeFirst.winner()).isEqualTo(afterFirst.winner()).isEqualTo(afterSecond.winner()).isNull()
	}
	
	@Test
	fun `first ace by alpha, other cards irrelevant, alpha is winner`() {
		// given
		val bout = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		// when
		val afterWinningCardPlayed = bout.put(ace)
		val finishedBout = afterWinningCardPlayed.put(king).put(king).put(ten)
		// then
		val winner = finishedBout.winner()
		assertThat(winner).isNotNull.isEqualTo(alpha).isEqualTo(afterWinningCardPlayed.lastPlayer())
	}
	
	@Test
	fun `first ace by beta, other cards irrelevant, beta is winner`() {
		// given
		val startingBout = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		// when
		val afterWinningCardPlayed = startingBout.put(ten).put(ace)
		val finishedBout = afterWinningCardPlayed.put(ten).put(king)
		// then
		val winner = finishedBout.winner()
		assertThat(winner).isNotNull.isEqualTo(beta).isEqualTo(afterWinningCardPlayed.lastPlayer())
	}
	
	@Test
	fun `first ten by alpha, no card higher than ten, alpha is winner`() {
		// given
		val bout = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		// when
		val afterWinningCardPlayed = bout.put(ten)
		val finishedBout = afterWinningCardPlayed.put(king).put(king).put(ten)
		// then
		val winner = finishedBout.winner()
		assertThat(winner).isNotNull.isEqualTo(alpha).isEqualTo(afterWinningCardPlayed.lastPlayer())
	}
	
	@Test
	fun `first ten by gamma, no card higher than ten, gamma is winner`() {
		// given
		val bout = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		// when
		val afterWinningCardPlayed = bout.put(king).put(king).put(ten)
		val finishedBout = afterWinningCardPlayed.put(ten)
		// then
		val winner = finishedBout.winner()
		assertThat(winner).isNotNull.isEqualTo(gamma).isEqualTo(afterWinningCardPlayed.lastPlayer())
	}
	
	@Test
	fun `nine leads, king and ten strike, delta wins with ace`() {
		// given
		val bout = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
		// when
		val finishedBout = bout.put(nine).put(king).put(ten).put(ace)
		// then
		val winner = finishedBout.winner()
		assertThat(winner).isNotNull.isEqualTo(delta)
	}
	
	@Test
	fun `beta and delta strike, intermediate states know about leading players`() {
		// given
		val beforeFirstCard = Bout(gameType = GameType.NORMAL, playerOrder = listOf(alpha, beta, gamma, delta))
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