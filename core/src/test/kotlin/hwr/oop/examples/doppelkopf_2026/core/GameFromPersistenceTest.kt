package hwr.oop.examples.doppelkopf_2026.core

import hwr.oop.examples.doppelkopf_2026.core.CardFromStringConverter.convert
import hwr.oop.examples.doppelkopf_2026.core.CardFromStringConverter.convertSingle
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GameFromPersistenceTest {
	
	@Test
	fun `game with specified hand cards, can be created from fixture`() {
		// when
		val sut = Fixture.game()
		// then
		val activeBout: Bout = sut.activeBout()
		assertThat(activeBout.nextPlayer()).isEqualTo(Fixture.beta)
		
		val alphaHand = sut.handOf(Fixture.alpha).cards()
		val betaHand = sut.handOf(Fixture.beta).cards()
		val gammaHand = sut.handOf(Fixture.gamma).cards()
		val deltaHand = sut.handOf(Fixture.delta).cards()
		
		assertThat(alphaHand).isEqualTo(convert("AS", "DULLE"))
		assertThat(betaHand).isEqualTo(convert("KS", "DULLE"))
		assertThat(gammaHand).isEqualTo(convert("TS", "FUCHS"))
		assertThat(deltaHand).isEqualTo(convert("KS", "FUCHS"))
		
	}
	
}

private object Fixture {
	
	val alpha = PlayerId("alpha")
	val beta = PlayerId("beta")
	val gamma = PlayerId("gamma")
	val delta = PlayerId("delta")
	
	val hands = listOf(
		alpha withHand convert("AS", "DULLE"),
		beta withHand convert("KS", "DULLE"),
		gamma withHand convert("TS", "FUCHS"),
		delta withHand convert("KS", "FUCHS")
	)
	
	val pastBoutsFinished = listOf(
		Bout(
			gameType = GameType.NORMAL,
			playerOrder = listOf(alpha, beta, gamma, delta),
			cards = convert("AC", "QC", "AC", "KC"),
			cardToBeat = convertSingle("QC"),
			leadingPlayer = beta,
		)
	)
	
	fun game(): Game = Game(
		handsOfPlayers = hands,
		bouts = pastBoutsFinished,
	)
	
	private infix fun PlayerId.withHand(cards: List<Card>): Hand = Hand(this, cards)
}