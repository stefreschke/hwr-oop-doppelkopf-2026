package hwr.oop.examples.doppelkopf_2026.core

object CardFromStringConverter {
	
	fun convertSingle(cardString: String): Card = convert(cardString).first()
	
	fun convert(vararg strings: String) = convert(strings.toList())
	
	private fun convert(list: List<String>): List<Card> = list.map { it.asCard() }
	
	fun String.asCard(): Card {
		require(isNotEmpty()) { "Card string must not be empty" }
		require(isNotBlank()) { "Card string must not be blank" }
		val uppercase = this.uppercase()
		return when (uppercase) {
			"FUCHS" -> Card(Suit.DIAMONDS, Rank.ACE)
			"DULLE" -> Card(Suit.HEARTS, Rank.TEN)
			"TH" -> throw IllegalArgumentException("Use \"DULLE\" instead of \"TH\" for the Ten of Hearts")
			"AD" -> throw IllegalArgumentException("Use \"FUCHS\" instead of \"AD\" for the Ace of Diamonds")
			else -> {
				require(this.length == 2) { "Card string must be exactly 2 characters long" }
				val rankChar = uppercase[0]
				val suitChar = uppercase[1]
				Card(suits(suitChar), ranks(rankChar))
			}
		}
	}
	
	private fun suits(char: Char): Suit = when (char) {
		'D' -> Suit.DIAMONDS
		'H' -> Suit.HEARTS
		'C' -> Suit.CLUBS
		'S' -> Suit.SPADES
		else -> throw IllegalArgumentException("Unknown suit: $char")
	}
	
	private fun ranks(char: Char): Rank = when (char) {
		'N' -> Rank.NINE
		'T' -> Rank.TEN
		'J' -> Rank.JACK
		'Q' -> Rank.QUEEN
		'K' -> Rank.KING
		'A' -> Rank.ACE
		else -> throw IllegalArgumentException("Unknown rank: $char")
	}
	
}
