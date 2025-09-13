package `in`.surojit.bookfinderapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import `in`.surojit.bookfinderapp.data.local.AppDatabase
import `in`.surojit.bookfinderapp.data.local.BookDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): AppDatabase {
        return Room.databaseBuilder(ctx, AppDatabase::class.java, "books_db").build()
    }

    @Provides
    fun provideBookDao(db: AppDatabase): BookDao = db.bookDao()
}