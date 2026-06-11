package hwr.oop.examples.template

import hwr.oop.examples.doppelkopf_2026.core.Game
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.json.jsonb

private val format = Json {
	prettyPrint = false
	isLenient = true
	ignoreUnknownKeys = true
}

object DoppelkopfGamesTable : UUIDTable("doppelkopf_games") {
	val game = jsonb<Game>("game", format)
}
