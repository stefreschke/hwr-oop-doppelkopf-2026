package hwr.oop.examples.doppelkopf_2026.core.bouts

import hwr.oop.examples.doppelkopf_2026.core.Bout
import hwr.oop.examples.doppelkopf_2026.core.CardFromStringConverter
import hwr.oop.examples.doppelkopf_2026.core.GameType
import hwr.oop.examples.doppelkopf_2026.core.PlayerId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DifferentColorDoesNotCountTest {
	
	// players
	private val alpha = PlayerId("alpha")
	private val beta = PlayerId("beta")
	private val gamma = PlayerId("gamma")
	private val delta = PlayerId("delta")
	
	// required cards
	private val ace = CardFromStringConverter.convert("AS").first()
	private val ten = CardFromStringConverter.convert("TS").first()
	private val king = CardFromStringConverter.convert("KS").first()
	private val otherAce = CardFromStringConverter.convert("AH").first()
	private val otherTen = CardFromStringConverter.convert("TC").first()
	private val yetAnotherAce = CardFromStringConverter.convert("AC").first()
	// fourth ace is trump
	
	@Test
	fun `ten leads, other-colored ace does not count, alpha wins`() {
		// given
		val bout = Bout(
			gameType = GameType.NORMAL,
			playerOrder = listOf(alpha, beta, gamma, delta)
		)
		// when
		val finishedBout = bout.put(ten).put(king).put(otherAce).put(ten)
		// then
		val winner = finishedBout.winner()
		assertThat(winner).isEqualTo(alpha)
	}
	
	@Test
	fun `two kings lead, other-colored ten and ace do not count, alpha wins`() {
		// given
		val bout = Bout(
			gameType = GameType.NORMAL,
			playerOrder = listOf(alpha, beta, gamma, delta)
		)
		// when
		val finishedBout = bout.put(king).put(king).put(otherTen).put(otherAce)
		// then
		val winner = finishedBout.winner()
		assertThat(winner).isEqualTo(alpha)
	}
	
	@Test
	fun `ten strikes king, other-colored ace does not count, delta wins with ace`() {
		// given
		val bout = Bout(
			gameType = GameType.NORMAL,
			playerOrder = listOf(alpha, beta, gamma, delta)
		)
		// when
		val finishedBout = bout.put(king).put(ten).put(otherAce).put(ace)
		// then
		val winner = finishedBout.winner()
		assertThat(winner).isEqualTo(delta)
	}
	
	@Test
	fun `ten leads, two other-colored aces do not count, alpha wins`() {
		// given
		val bout = Bout(
			gameType = GameType.NORMAL,
			playerOrder = listOf(alpha, beta, gamma, delta)
		)
		// when
		val finishedBout = bout.put(ten).put(otherAce).put(yetAnotherAce).put(king)
		// then
		val winner = finishedBout.winner()
		assertThat(winner).isEqualTo(alpha)
	}
	
	@Test
	fun `two kings lead, two other-colored tens do not count, alpha wins`() {
		// given
		val bout = Bout(
			gameType = GameType.NORMAL,
			playerOrder = listOf(alpha, beta, gamma, delta)
		)
		// when
		val finishedBout = bout.put(king).put(otherTen).put(otherTen).put(king)
		// then
		val winner = finishedBout.winner()
		assertThat(winner).isEqualTo(alpha)
	}
}