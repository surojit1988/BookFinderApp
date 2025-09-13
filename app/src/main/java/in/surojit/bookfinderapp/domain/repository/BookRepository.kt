package `in`.surojit.bookfinderapp.domain.repository

import `in`.surojit.bookfinderapp.data.model.WorkDetails
import `in`.surojit.bookfinderapp.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun search(query: String, page: Int): List<Book>
    suspend fun getWorkDetails(workId: String): WorkDetails
    suspend fun saveFavorite(book: Book)
    suspend fun removeFavorite(book: Book)
    suspend fun isFavorite(id: String): Boolean
    fun favoritesFlow(): Flow<List<Book>>
}