package hwr.oop.examples.doppelkopf_2026.core

class InvalidNumberOfPlayersException(players: List<PlayerId>) : CoreException(
	"Doppelkopf is always played with exactly 4 players, but got ${players.size} players ($players)."
)
