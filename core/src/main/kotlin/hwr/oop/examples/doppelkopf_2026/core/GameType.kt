package hwr.oop.examples.doppelkopf_2026.core

enum class GameType {
	NORMAL {
		override fun comparator(card: Card?): Comparator<Card>? {
			return if (card == null) null
			else GameOrderingCardComparator(trumpOrderingsNormalGame, card)
		}
	};
	
	abstract fun comparator(card: Card?): Comparator<Card>?
}

private class GameOrderingCardComparator(
	private val predicates: List<CardOrderingPredicate>,
	private val startingCard: Card,
) : Comparator<Card> {
	
	private val baseColor: Suit? =
		if (predicates.none { it.appliesTo(startingCard) }) startingCard.suit() else null
	
	override fun compare(
		first: Card,
		second: Card,
	): Int {
		val aMatch = findFirstMatchingPredicateWithIndex(first)
		val bMatch = findFirstMatchingPredicateWithIndex(second)
		return when {
			aMatch != null && bMatch != null ->
				if (aMatch.index == bMatch.index) aMatch.value.compare(first, second)
				else bMatch.index.compareTo(aMatch.index)
			
			aMatch != null && bMatch == null -> 1
			aMatch == null && bMatch != null -> -1
			else -> compareFehl(first, second)
		}
	}
	
	private fun compareFehl(a: Card, b: Card): Int = when (baseColor) {
		a.suit() if b.suit() == baseColor -> Rank.ColorComparator.compare(a.rank(), b.rank())
		a.suit() -> 1
		else -> -1
	}
	
	private fun findFirstMatchingPredicateWithIndex(element: Card): IndexedValue<CardOrderingPredicate>? =
		predicates.withIndex().firstOrNull { (_, pred) -> pred.appliesTo(element) }
}

private class CardOrderingPredicate(
	private val comparator: Comparator<Card> = CardIdentityComparator,
	private val suits: Set<Suit> = Suit.entries.toSet(),
	private val ranks: Set<Rank> = Rank.entries.toSet(),
) : Comparator<Card> by comparator {
	fun appliesTo(card: Card): Boolean = suits.any { card.suit() == it } && ranks.any { card.rank() == it }
}

private object CardIdentityComparator : Comparator<Card> {
	override fun compare(card1: Card, card2: Card): Int {
		return if (card1 == card2) 0 else -1
	}
}

private object CardSuitComparator : Comparator<Card> {
	override fun compare(a: Card, b: Card): Int = Suit.NaiveComparator.compare(a.suit(), b.suit())
}

private object CardRankComparator : Comparator<Card> {
	override fun compare(a: Card, b: Card): Int = Rank.TrumpComparator.compare(a.rank(), b.rank())
}

private val trumpOrderingsNormalGame = listOf(
	CardOrderingPredicate(suits = setOf(Suit.HEARTS), ranks = setOf(Rank.TEN)),
	CardOrderingPredicate(comparator = CardSuitComparator, ranks = setOf(Rank.QUEEN)),
	CardOrderingPredicate(comparator = CardSuitComparator, ranks = setOf(Rank.JACK)),
	CardOrderingPredicate(
		comparator = CardRankComparator,
		suits = setOf(Suit.DIAMONDS),
		ranks = setOf(Rank.NINE, Rank.KING, Rank.TEN, Rank.ACE)
	)
)
