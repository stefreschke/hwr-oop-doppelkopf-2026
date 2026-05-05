package hwr.oop.examples.doppelkopf_2026.core

enum class GameType {
	NORMAL {
		private val trumpComparator = Rank.TrumpComparator
		private val rankComparator = Rank.ColorComparator
		private val suitComparator = Suit.NaiveComparator
		
		override fun comparator(): Comparator<Card> {
			return { a, b ->
				if (a.isTrump() && b.isTrump()) {
					val trumpComparatorValue = trumpComparator.compare(a.rank(), b.rank())
					if (trumpComparatorValue != 0) trumpComparatorValue else suitComparator.compare(a.suit(), b.suit())
				} else if (!a.isTrump() && b.isTrump()) {
					-1
				} else if (a.isTrump() && !b.isTrump()) {
					1
				} else {
					rankComparator.compare(a.rank(), b.rank())
				}
			}
		}
		
		private fun Card.isTrump(): Boolean = this.rank() in listOf(Rank.JACK, Rank.QUEEN) || this.suit() == Suit.DIAMONDS
	};
	
	abstract fun comparator(): Comparator<Card>
}
