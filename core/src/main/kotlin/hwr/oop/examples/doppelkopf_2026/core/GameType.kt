package hwr.oop.examples.doppelkopf_2026.core

enum class GameType {
	NORMAL {
		private val rankComparator = Rank.ColorComparator
		
		override fun comparator(): Comparator<Card> {
			return { a, b -> rankComparator.compare(a.rank(), b.rank()) }
		}
	};
	
	abstract fun comparator(): Comparator<Card>
}
