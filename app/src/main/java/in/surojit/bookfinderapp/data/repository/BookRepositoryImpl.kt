package `in`.surojit.bookfinderapp.data.repository

import `in`.surojit.bookfinderapp.data.local.BookDao
import `in`.surojit.bookfinderapp.data.local.BookEntity
import `in`.surojit.bookfinderapp.data.model.BookDoc
import `in`.surojit.bookfinderapp.data.remote.OpenLibraryApi
import `in`.surojit.bookfinderapp.domain.model.Book
import `in`.surojit.bookfinderapp.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepositoryImpl @Inject constructor(
    private val api: OpenLibraryApi,
    private val dao: BookDao
) : BookRepository {

    private fun docToBook(d: BookDoc): Book {
        // key like "/works/OL45883W" -> id "OL45883W"
        val id = d.key?.removePrefix("/works/") ?: UUID.randomUUID().toString()
        val authors = d.author_name?.joinToString(", ") ?: "Unknown"
        val cover = d.cover_i?.let { "https://covers.openlibrary.org/b/id/${it}-M.jpg" }
        return Book(id = id, title = d.title ?: "-", authors = authors, coverUrl = cover)
    }

    override suspend fun search(query: String, page: Int): List<Book> {
        if (query.isBlank()) return emptyList()
        val res = api.searchBooks(title = query, page = page)
        return res.docs.map { docToBook(it) }
    }

    override suspend fun getWorkDetails(workId: String) =
        api.getWorkDetails(workId)

    override suspend fun saveFavorite(book: Book) {
        dao.insert(BookEntity(book.id, book.title, book.authors, book.coverUrl))
    }

    override suspend fun removeFavorite(book: Book) {
        dao.delete(BookEntity(book.id, book.title, book.authors, book.coverUrl))
    }

    override suspend fun isFavorite(id: String): Boolean = dao.getById(id) != null

    override fun favoritesFlow(): Flow<List<Book>> = dao.getAllFlow().map { list -> list.map { Book(it.id, it.title, it.authors, it.coverUrl) } }

}