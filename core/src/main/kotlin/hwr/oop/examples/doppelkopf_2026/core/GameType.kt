package hwr.oop.examples.doppelkopf_2026.core

enum class GameType {
	NORMAL {
		private val trumpComparator = Rank.TrumpComparator
		private val rankComparator = Rank.ColorComparator
		private val suitComparator = Suit.NaiveComparator
		
		override fun comparator(card: Card?): Comparator<Card>? {
			return if (card == null) null
			else Comparator { a, b ->
				if (a.isTrump() && b.isTrump()) {
					if (a.isDulle() && b.isDulle()) {
						0
					} else if (a.isDulle() && !b.isDulle()) {
						1
					} else if (!a.isDulle() && b.isDulle()) {
						-1
					} else {
						val trumpRankComparator = trumpComparator.compare(a.rank(), b.rank())
						if (trumpRankComparator != 0) trumpRankComparator else suitComparator.compare(a.suit(), b.suit())
					}
				} else if (!a.isTrump() && b.isTrump()) {
					-1
				} else if (a.isTrump() && !b.isTrump()) {
					1
				} else {
					if (a.suit() == b.suit()) {
						rankComparator.compare(a.rank(), b.rank())
					} else if (a.suit() == card.suit()) {
						1
					} else {
						-1
					}
				}
			}
		}
		
		private fun Card.isDulle(): Boolean = this.suit() == Suit.HEARTS && this.rank() == Rank.TEN
		
		private fun Card.isTrump(): Boolean =
			this.rank() in listOf(Rank.JACK, Rank.QUEEN) || this.suit() == Suit.DIAMONDS || this.isDulle()
	};
	
	abstract fun comparator(card: Card?): Comparator<Card>?
}
