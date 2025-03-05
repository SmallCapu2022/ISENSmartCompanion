package fr.isen.capucine.isensmartcompanion.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryItem(historyItem: HistoryItem)

    // Utilisation de Flow pour observer les changements en temps réel
    @Query("SELECT * FROM history ORDER BY date DESC")
    fun getAllHistory(): Flow<List<HistoryItem>> // ⚡ Changement ici

    @Delete
    suspend fun deleteHistoryItem(historyItem: HistoryItem)

    @Query("DELETE FROM history")
    suspend fun deleteAllHistory()
}
