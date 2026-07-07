package hwr.oop.examples.doppelkopf_2026.adapters.`in`

import hwr.oop.examples.doppelkopf_2026.adapters.out.InMemoryPersistence
import hwr.oop.examples.doppelkopf_2026.core.Rank
import hwr.oop.examples.doppelkopf_2026.core.Suit
import hwr.oop.examples.doppelkopf_2026.core.testdata.Fixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class PlayCardUseCaseTest {
	
	@Test
	@Disabled
	fun `calling the usecase is the same as calling the message on the object, but with persistence`() {
		// given
		val persistence = InMemoryPersistence()
		val game = Fixture.game()
		val gameId = game.id()
		persistence.save(game)
		val useCase = PlayCardUseCase(persistence, persistence)
		
		// when
		useCase.playAction(
			PlayCardUseCase.Command(
				gameId.value,
				player = "alpha",
				suit = Suit.SPADES.name,
				rank = Rank.ACE.name,
			)
		)
		
		// then
		val loadedGame = persistence.loadByid(gameId)
		assertThat(loadedGame).isNotEqualTo(game)
	}
}