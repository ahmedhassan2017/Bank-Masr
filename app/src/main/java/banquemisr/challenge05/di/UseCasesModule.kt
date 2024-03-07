package banquemisr.challenge05.di

import banquemisr.challenge05.domain.repositories.MoviesRepo
import banquemisr.challenge05.domain.usecases.getMovieDetails.GetMovieDetailsUseCase
import banquemisr.challenge05.domain.usecases.getMovieDetails.GetMovieDetailsUseCaseImp
import banquemisr.challenge05.domain.usecases.getMovies.GetMoviesUseCase
import banquemisr.challenge05.domain.usecases.getMovies.GetMoviesUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule
{

    @Provides
    @Singleton
    fun provideMoviesUseCase(repo:MoviesRepo): GetMoviesUseCase
    {
        return GetMoviesUseCaseImp(repo)
    }

    @Provides
    @Singleton
    fun provideMovieDetailsUseCase(repo:MoviesRepo): GetMovieDetailsUseCase
    {
        return GetMovieDetailsUseCaseImp(repo)
    }

}