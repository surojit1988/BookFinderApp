package `in`.surojit.bookfinderapp.domain.model

data class Book(
    val id: String,
    val title: String,
    val authors: String,
    val coverUrl: String?
)
