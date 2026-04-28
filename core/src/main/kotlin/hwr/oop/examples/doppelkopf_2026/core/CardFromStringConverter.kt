package hwr.oop.examples.doppelkopf_2026.core

object CardFromStringConverter {
	fun String.asCard(): Card {
		require(isNotEmpty()) { "Card string must not be empty" }
		require(isNotBlank()) { "Card string must not be blank" }
		val uppercase = this.uppercase()
		return when (uppercase) {
			"FUCHS" -> Card(Suit.SCHELLEN, Rank.DAUSS)
			"DULLE" -> Card(Suit.HERZ, Rank.ZEHN)
			else -> {
				require(this.length == 2) { "Card string must be exactly 2 characters long" }
				val suitChar = uppercase[0]
				val rankChar = uppercase[1]
				Card(suits(suitChar), ranks(rankChar))
			}
		}
	}
	
	private fun suits(char: Char): Suit = when (char) {
		'S' -> Suit.SCHELLEN
		'H' -> Suit.HERZ
		'E' -> Suit.EICHEL
		'G' -> Suit.GRUEN
		else -> throw IllegalArgumentException("Unknown suit: $char")
	}
	
	private fun ranks(char: Char): Rank = when (char) {
		'N' -> Rank.NEUN
		'Z' -> Rank.ZEHN
		'U' -> Rank.UNTER
		'O' -> Rank.OBER
		'K' -> Rank.KOENIG
		'D' -> Rank.DAUSS
		else -> throw IllegalArgumentException("Unknown rank: $char")
	}
	
}
