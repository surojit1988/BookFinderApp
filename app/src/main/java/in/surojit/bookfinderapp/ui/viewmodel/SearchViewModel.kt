package `in`.surojit.bookfinderapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.surojit.bookfinderapp.domain.model.Book
import `in`.surojit.bookfinderapp.domain.usecase.BookUseCases
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val items: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val isEndReached: Boolean = false,
    val error: String? = null,
    val query: String = ""
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: BookUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    private var currentPage = 1

    fun search(query: String, refresh: Boolean = false) {
        if (query.isBlank()) return

        viewModelScope.launch {
            if (refresh) {
                currentPage = 1
                _uiState.value = SearchUiState(query = query, isLoading = true)
            } else {
                _uiState.update { it.copy(isLoading = true, query = query) }
            }

            try {
                val result = useCases.searchBooks(query, currentPage)
                _uiState.update {
                    it.copy(
                        items = if (refresh) result else it.items + result,
                        isLoading = false,
                        isEndReached = result.isEmpty()
                    )
                }
                if (result.isNotEmpty()) currentPage++
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun toggleFavorite(book: Book) {
        // TODO hook up with SaveFavorite / RemoveFavorite use cases
    }
}