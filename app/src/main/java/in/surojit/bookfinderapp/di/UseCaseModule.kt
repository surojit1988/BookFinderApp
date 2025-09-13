package `in`.surojit.bookfinderapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.surojit.bookfinderapp.data.local.BookDao
import `in`.surojit.bookfinderapp.data.remote.OpenLibraryApi
import `in`.surojit.bookfinderapp.data.repository.BookRepositoryImpl
import `in`.surojit.bookfinderapp.domain.repository.BookRepository
import `in`.surojit.bookfinderapp.domain.usecase.BookUseCases
import `in`.surojit.bookfinderapp.domain.usecase.FavoritesFlow
import `in`.surojit.bookfinderapp.domain.usecase.GetWorkDetails
import `in`.surojit.bookfinderapp.domain.usecase.IsFavorite
import `in`.surojit.bookfinderapp.domain.usecase.RemoveFavorite
import `in`.surojit.bookfinderapp.domain.usecase.SaveFavorite
import `in`.surojit.bookfinderapp.domain.usecase.SearchBooks
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideBookUseCases(repo: BookRepository): BookUseCases {
        return BookUseCases(
            searchBooks = SearchBooks(repo),
            getWorkDetails = GetWorkDetails(repo),
            saveFavorite = SaveFavorite(repo),
            removeFavorite = RemoveFavorite(repo),
            isFavorite = IsFavorite(repo),
            favoritesFlow = FavoritesFlow(repo)
        )
    }

    @Provides
    @Singleton
    fun provideBookRepository(api: OpenLibraryApi, dao: BookDao): BookRepository {
        return BookRepositoryImpl(api, dao)
    }
}