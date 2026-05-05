package hwr.oop.examples.doppelkopf_2026.core.bouts

import hwr.oop.examples.doppelkopf_2026.core.Bout
import hwr.oop.examples.doppelkopf_2026.core.CardFromStringConverter
import hwr.oop.examples.doppelkopf_2026.core.CardFromStringConverter.convertSingle
import hwr.oop.examples.doppelkopf_2026.core.GameType
import hwr.oop.examples.doppelkopf_2026.core.PlayerId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class BasicBoutWithTrumpTest {
	// players
	private val alpha = PlayerId("alpha")
	private val beta = PlayerId("beta")
	private val gamma = PlayerId("gamma")
	private val delta = PlayerId("delta")
	
	// required cards
	private val ace = convertSingle("AC")
	private val ten = convertSingle("TC")
	private val king = convertSingle("KC")
	private val trumpKing = convertSingle("KD")
	private val trumpTen = convertSingle("TD")
	private val trumpAce = convertSingle("AD")
	private val trumpJack = convertSingle("JS")
	private val trumpQueen = convertSingle("QC")
	private val heartTen = convertSingle("TH")
	
	@Test
	fun `gamma strikes with trump, gamma wins`() {
		// given
		val bout = Bout(
			gameType = GameType.NORMAL,
			playerOrder = listOf(alpha, beta, gamma, delta),
		)
		// when
		val beforeTrump = bout.put(ace).put(ten)
		val afterTrump = beforeTrump.put(trumpKing)
		val finishedBout = afterTrump.put(ace)
		// then
		assertThat(beforeTrump.leader()).isEqualTo(alpha)
		assertThat(afterTrump.leader()).isEqualTo(gamma)
		assertThat(finishedBout.leader()).isEqualTo(gamma)
	}
	
	@Test
	fun `beta strikes with trump, delta strikes again, delta wins`() {
		// given
		val bout = Bout(
			gameType = GameType.NORMAL,
			playerOrder = listOf(alpha, beta, gamma, delta)
		)
		// when
		val beforeTrump = bout.put(ace)
		val afterTrump = beforeTrump.put(trumpKing)
		val beforeLastTrump = afterTrump.put(ace)
		val afterLastTrump = beforeLastTrump.put(trumpTen)
		// then
		assertThat(beforeTrump.leader()).isEqualTo(alpha)
		assertThat(afterTrump.leader()).isEqualTo(beta)
		assertThat(beforeLastTrump.leader()).isEqualTo(beta)
		assertThat(afterLastTrump.leader()).isEqualTo(delta).isEqualTo(afterLastTrump.winner())
	}
	
	@Test
	fun `beta strikes with trump, gamma and delta play weaker trump, beta wins`() {
		// given
		val bout = Bout(
			gameType = GameType.NORMAL,
			playerOrder = listOf(alpha, beta, gamma, delta)
		)
		// when
		val beforeTrump = bout.put(ace)
		val afterTrump = beforeTrump.put(trumpQueen)
		val afterSecondTrump = afterTrump.put(trumpJack)
		val finishedBout = afterSecondTrump.put(trumpKing)
		// then
		assertThat(beforeTrump.leader())
			.isEqualTo(alpha)
		assertThat(finishedBout.winner())
			.isEqualTo(beta)
			.isEqualTo(afterTrump.leader())
			.isEqualTo(afterSecondTrump.leader())
	}
	
	@ParameterizedTest
	@CsvSource(
		"AS, KD, TD, AD",
		// jacks
		"JD, JH, JS, JC",
		// queens
		"QD, QH, QS, QC"
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