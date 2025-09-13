package `in`.surojit.bookfinderapp.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WorkDetails(
    val description: Any? = null, // can be String or {value: String}
    val title: String? = null,
    val covers: List<Int>? = null
)
