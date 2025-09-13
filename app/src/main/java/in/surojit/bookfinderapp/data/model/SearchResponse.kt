package `in`.surojit.bookfinderapp.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    val docs: List<BookDoc> = emptyList(),
    val numFound: Int = 0
)

@JsonClass(generateAdapter = true)
data class BookDoc(
    val key: String? = null, // e.g. "/works/OL45883W"
    val title: String? = null,
    val author_name: List<String>? = null,
    val cover_i: Int? = null,
    val edition_key: List<String>? = null
)
