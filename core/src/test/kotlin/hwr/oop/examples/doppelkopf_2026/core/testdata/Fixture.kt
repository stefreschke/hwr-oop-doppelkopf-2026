package hwr.oop.examples.doppelkopf_2026.core.testdata

import hwr.oop.examples.doppelkopf_2026.core.Bout
import hwr.oop.examples.doppelkopf_2026.core.Card
import hwr.oop.examples.doppelkopf_2026.core.CardFromStringConverter
import hwr.oop.examples.doppelkopf_2026.core.Game
import hwr.oop.examples.doppelkopf_2026.core.GameType
import hwr.oop.examples.doppelkopf_2026.core.Hand
import hwr.oop.examples.doppelkopf_2026.core.PlayerId

object Fixture {
	
	val alpha = PlayerId("alpha")
	val beta = PlayerId("beta")
	val gamma = PlayerId("gamma")
	val delta = PlayerId("delta")
	
	val hands = listOf(
		alpha withHand CardFromStringConverter.convert("AS", "DULLE"),
		beta withHand CardFromStringConverter.convert("KS", "DULLE"),
		gamma withHand CardFromStringConverter.convert("TS", "FUCHS"),
		delta withHand CardFromStringConverter.convert("KS", "FUCHS")
	)
	
	val pastBoutsFinished = listOf(
		Bout(
			gameType = GameType.NORMAL,
			playerOrder = listOf(alpha, beta, gamma, delta),
			cards = CardFromStringConverter.convert("AC", "QC", "AC", "KC"),
			cardToBeat = CardFromStringConverter.convertSingle("QC"),
			leadingPlayer = beta,
		)
	)
	
	fun game(): Game = Game(
		handsOfPlayers = hands,
		bouts = pastBoutsFinished,
	)
	
	private infix fun PlayerId.withHand(cards: List<Card>): Hand = Hand(this, cards)
}