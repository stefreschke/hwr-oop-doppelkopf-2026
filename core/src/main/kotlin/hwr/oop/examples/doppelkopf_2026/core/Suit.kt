package hwr.oop.examples.doppelkopf_2026.core

enum class Suit(private val order: Int) {
	SPADES(2),
	CLUBS(3),
	HEARTS(1),
	DIAMONDS(0);
	
	object NaiveComparator : Comparator<Suit> {
		override fun compare(
			first: Suit,
			second: Suit,
		): Int = first.order.compareTo(second.order)
	}
	
}
