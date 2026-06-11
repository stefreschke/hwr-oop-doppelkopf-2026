package hwr.oop.examples.doppelkopf_2026.ports.out

import hwr.oop.examples.doppelkopf_2026.core.Game

interface SaveGamePort {
	
	fun save(game: Game): Unit
	
}

