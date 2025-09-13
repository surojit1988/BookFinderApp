package `in`.surojit.bookfinderapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_books")
data class BookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val authors: String,
    val coverUrl: String?
)
