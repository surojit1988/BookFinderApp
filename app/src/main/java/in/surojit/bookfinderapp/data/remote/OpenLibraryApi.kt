package `in`.surojit.bookfinderapp.data.remote

import `in`.surojit.bookfinderapp.data.model.SearchResponse
import `in`.surojit.bookfinderapp.data.model.WorkDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenLibraryApi {

    // Search by title: https://openlibrary.org/search.json?title={query}&limit=20&page={page}
    @GET("search.json")
    suspend fun searchBooks(
        @Query("title") title: String,
        @Query("limit") limit: Int = 20,
        @Query("page") page: Int = 1
    ): SearchResponse

    // Work detail: https://openlibrary.org/works/{work_id}.json
    @GET("works/{work_id}.json")
    suspend fun getWorkDetails(@Path("work_id") workId: String): WorkDetails
}