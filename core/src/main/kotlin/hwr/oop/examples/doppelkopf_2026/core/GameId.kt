package hwr.oop.examples.doppelkopf_2026.core

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@JvmInline
value class GameId(val value: String) {
	companion object {
		fun random(): GameId = GameId(UUID.randomUUID().toString())
	}
}
