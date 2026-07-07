package hwr.oop.examples.template.service


object ResponseMapper {
	// objects from core
	private typealias ApiGame = hwr.oop.examples.template.service.model.GameResponse
	private typealias ApiCard = hwr.oop.examples.template.service.model.Card
	private typealias CoreBout = hwr.oop.examples.doppelkopf_2026.core.Bout
	
	// api objects (names similar to domain objects, thus using a type alias
	private typealias CoreGame = hwr.oop.examples.doppelkopf_2026.core.Game
	private typealias CoreCard = hwr.oop.examples.doppelkopf_2026.core.Card
	private typealias ApiBout = hwr.oop.examples.template.service.model.Bout
	
	// best have a look at: https://kotlinlang.org/docs/scope-functions.html#function-selection
	fun CoreGame.asGameResponse(): ApiGame = ApiGame().also {
		it.id = this.id().value
		it.bout = this.activeBout().asApiBout()
	}
	
	private fun CoreBout.asApiBout(): ApiBout = ApiBout().also {
		it.cards = this.playedCardsInOrder().asApiCards()
	}
	
	private fun List<CoreCard>.asApiCards(): List<ApiCard> = this.map {
		it.asApiCard()
	}
	
	private fun CoreCard.asApiCard(): ApiCard = ApiCard().also {
		it.suit = this.suit().name
		it.rank = this.rank().name
	}
	
}