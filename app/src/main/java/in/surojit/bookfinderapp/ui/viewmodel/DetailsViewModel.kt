package `in`.surojit.bookfinderapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.surojit.bookfinderapp.data.model.WorkDetails
import `in`.surojit.bookfinderapp.domain.model.Book
import `in`.surojit.bookfinderapp.domain.usecase.BookUseCases
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: BookUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    fun loadDetails(bookId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val details = useCases.getWorkDetails(bookId)
                val fav = useCases.isFavorite(bookId)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        workDetails = details,
                        isFavorite = fav
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.localizedMessage ?: "Error loading details"
                    )
                }
            }
        }
    }

    fun toggleFavorite(bookId: String) {
        viewModelScope.launch {
            val fav = useCases.isFavorite(bookId)
            if (fav) {
                // remove
                useCases.removeFavorite(
                    Book(bookId, "", "", null) // minimal info for deletion
                )
            } else {
                // save minimal info, you can enrich with WorkDetails if needed
                val details = _uiState.value.workDetails
                useCases.saveFavorite(
                    Book(
                        id = bookId,
                        title = details?.title ?: "-",
                        authors = "Unknown",
                        coverUrl = details?.covers?.firstOrNull()
                            ?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" }
                    )
                )
            }
            _uiState.update { it.copy(isFavorite = !fav) }
        }
    }
}

data class DetailsUiState(
    val workDetails: WorkDetails? = null,
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val error: String? = null
)