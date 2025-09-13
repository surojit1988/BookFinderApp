package `in`.surojit.bookfinderapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: BookEntity)

    @Delete
    suspend fun delete(book: BookEntity)

    @Query("SELECT * FROM favorite_books WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): BookEntity?

    @Query("SELECT * FROM favorite_books ORDER BY title")
    fun getAllFlow(): Flow<List<BookEntity>>
}