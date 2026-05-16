package hwr.oop.examples.doppelkopf_2026.core.bouts

import hwr.oop.examples.doppelkopf_2026.core.Bout
import hwr.oop.examples.doppelkopf_2026.core.CardFromStringConverter
import hwr.oop.examples.doppelkopf_2026.core.GameType
import hwr.oop.examples.doppelkopf_2026.core.PlayerId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class TrumpOnlyBoutsTest {
	
	// players
	private val alpha = PlayerId("alpha")
	private val beta = PlayerId("beta")
	private val gamma = PlayerId("gamma")
	private val delta = PlayerId("delta")
	
	@ParameterizedTest
	@CsvSource(
		// kings
		"KD, TD, FUCHS, JD",
		// jacks
		"JD, JH, JS, JC",
		"JH, JS, JC, QD",
		// queens
		"QD, QH, QS, QC",
		"QH, QS, QC, DULLE"
	)
	fun `ascending trumps`(firstString: String, secondString: String, thirdString: String, fourthString: String) {
		// given
		val (first, second, third, fourth) = CardFromStringConverter.convert(
			firstString,
			secondString,
			thirdString,
			fourthString
		)
		val bout = Bout(
			gameType = GameType.NORMAL,
			playerOrder = listOf(alpha, beta, gamma, delta),
		)
		// when
		val afterFirst = bout.put(first)
		val afterSecond = afterFirst.put(second)
		val afterThird = afterSecond.put(third)
		val afterFourth = afterThird.put(fourth)
		// then
		assertThat(afterFirst.leader()).isEqualTo(alpha)
		assertThat(afterSecond.leader()).isEqualTo(beta)
		assertThat(afterThird.leader()).isEqualTo(gamma)
		assertThat(afterFourth.leader()).isEqualTo(delta)
	}
}