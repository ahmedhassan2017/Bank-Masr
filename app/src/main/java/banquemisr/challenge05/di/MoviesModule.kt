package banquemisr.challenge05.di

import banquemisr.challenge05.data.remote.ApiInterface
import banquemisr.challenge05.domain.repositories.MoviesRepo
import banquemisr.challenge05.data.repository.MoviesRepoImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesModule
{
    @Provides
    @Singleton
    fun provideRepo(apiInterface: ApiInterface):MoviesRepo{
        return MoviesRepoImp(apiInterface)
    }
}