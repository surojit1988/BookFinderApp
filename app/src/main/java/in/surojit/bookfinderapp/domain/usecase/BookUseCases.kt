package `in`.surojit.bookfinderapp.domain.usecase

import `in`.surojit.bookfinderapp.domain.model.Book
import `in`.surojit.bookfinderapp.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow


class SearchBooks(private val repo: BookRepository) {
    suspend operator fun invoke(query: String, page: Int) = repo.search(query, page)
}

class GetWorkDetails(private val repo: BookRepository) {
    suspend operator fun invoke(workId: String) = repo.getWorkDetails(workId)
}

class SaveFavorite(private val repo: BookRepository) {
    suspend operator fun invoke(book: Book) = repo.saveFavorite(book)
}

class RemoveFavorite(private val repo: BookRepository) {
    suspend operator fun invoke(book: Book) = repo.removeFavorite(book)
}

class IsFavorite(private val repo: BookRepository) {
    suspend operator fun invoke(id: String) = repo.isFavorite(id)
}

class FavoritesFlow(private val repo: BookRepository) {
    operator fun invoke(): Flow<List<Book>> = repo.favoritesFlow()
}

data class BookUseCases(
    val searchBooks: SearchBooks,
    val getWorkDetails: GetWorkDetails,
    val saveFavorite: SaveFavorite,
    val removeFavorite: RemoveFavorite,
    val isFavorite: IsFavorite,
    val favoritesFlow: FavoritesFlow
)