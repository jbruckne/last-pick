package joebruckner.lastpick.source.history

import joebruckner.lastpick.source.DatabaseHelper

object HistoryEntry {
    val COLUMN_MOVIE_ID = "MovieId"
    val COLUMN_TIMESTAMP = "Timestamp"
    val TABLE_HISTORY = "History"
    val CREATE_TABLE_HISTORY = "CREATE TABLE IF NOT EXISTS $TABLE_HISTORY (" +
            "${DatabaseHelper.COLUMN_ID} INTEGER PRIMARY KEY, " +
            "$COLUMN_MOVIE_ID INTEGER, " +
            "$COLUMN_TIMESTAMP DATETIME CURRENT_TIMESTAMP" +
            ");"
    val DELETE_TABLE_HISTORY = "DROP TABLE IF EXISTS $TABLE_HISTORY"
}