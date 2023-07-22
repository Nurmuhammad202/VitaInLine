package uz.hayot.vitaInLine.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.hayot.vitaInLine.data.Repository
import uz.hayot.vitaInLine.data.local.SharedPrefRepository
import uz.hayot.vitaInLine.data.remote.ApiInterface
import uz.hayot.vitaInLine.data.remote.RetrofitBuilder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofitGetServer(): ApiInterface {
        return RetrofitBuilder.apiInterfaceBuilder()
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(
        apiInterface: ApiInterface, sharedPrefRepository: SharedPrefRepository
    ): Repository {
        return Repository(apiInterface = apiInterface, sharedInterface = sharedPrefRepository)
    }

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPrefRepository {
        return SharedPrefRepository(context)
    }

}