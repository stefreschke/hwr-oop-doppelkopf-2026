package hwr.oop.examples.doppelkopf_2026.core

enum class Rank(
	private val colorOrder: Int,
	private val trumpOrder: Int,
) : Comparable<Rank> {
	NINE(0, 0),
	JACK(1, 3),
	QUEEN(2, 4),
	KING(3, 1),
	TEN(4, 2),
	ACE(5, 3);
	
	object ColorComparator : Comparator<Rank> {
		override fun compare(
			first: Rank,
			second: Rank,
		) = first.colorOrder.compareTo(second.colorOrder)
	}
	
	object TrumpComparator : Comparator<Rank> {
		override fun compare(
			first: Rank,
			second: Rank,
		) = first.trumpOrder.compareTo(second.trumpOrder)
	}
}
