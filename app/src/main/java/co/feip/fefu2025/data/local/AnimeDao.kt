package co.feip.fefu2025.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(anime: AnimeFavoriteEntity)

    @Query("DELETE FROM favorite_anime WHERE id = :animeId")
    suspend fun removeFavorite(animeId: Int)

    @Query("SELECT * FROM favorite_anime WHERE id = :animeId")
    fun getFavorite(animeId: Int): Flow<AnimeFavoriteEntity?>

    @Query("SELECT * FROM favorite_anime ORDER BY title")
    fun getAllFavorites(): Flow<List<AnimeFavoriteEntity>>

    @Query("SELECT * FROM favorite_anime WHERE id = :animeId")
    suspend fun getFavoriteById(animeId: Int): AnimeFavoriteEntity?
}