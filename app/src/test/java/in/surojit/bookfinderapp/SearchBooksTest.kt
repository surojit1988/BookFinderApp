package `in`.surojit.bookfinderapp


import `in`.surojit.bookfinderapp.domain.model.Book
import `in`.surojit.bookfinderapp.domain.repository.BookRepository
import `in`.surojit.bookfinderapp.domain.usecase.SearchBooks
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class SearchBooksTest {

    private lateinit var repository: BookRepository
    private lateinit var searchBooks: SearchBooks

    @Before
    fun setup() {
        repository = mock()
        searchBooks = SearchBooks(repository)
    }

    @Test
    fun `invoke returns list of books from repository`() = runTest {
        val query = "Kotlin"
        val page = 1

        val mockBooks = listOf(
            Book("1", "Kotlin in Action", "Dmitry Jemerov", null),
            Book("2", "Kotlin Programming", "Venkat Subramaniam", null)
        )

        whenever(repository.search(query, page)).thenReturn(mockBooks)

        val result = searchBooks(query, page)

        assertEquals(2, result.size)
        assertEquals("Kotlin in Action", result[0].title)
        assertEquals("Kotlin Programming", result[1].title)
    }
}