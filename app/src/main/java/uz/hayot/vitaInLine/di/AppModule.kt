package uz.hayot.vitaInLine.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.geo.go.database.room.database.AppDatabase
import uz.hayot.vitaInLine.data.Repository
import uz.hayot.vitaInLine.data.local.room.dao.VitaDao
import uz.hayot.vitaInLine.data.local.sharedPref.SharedPrefRepository
import uz.hayot.vitaInLine.data.remote.ApiInterface
import uz.hayot.vitaInLine.data.remote.RetrofitBuilder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideRetrofitGetServer(): ApiInterface {
        return RetrofitBuilder.apiInterfaceBuilder()
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(
        apiInterface: ApiInterface, sharedPrefRepository: SharedPrefRepository, vitaDao: VitaDao
    ): Repository {
        return Repository(
            apiInterface = apiInterface,
            sharedInterface = sharedPrefRepository,
            vitaDao = vitaDao
        )
    }

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPrefRepository {
        return SharedPrefRepository(context)
    }

    @Singleton
    @Provides
    fun getAppDB(context: Application): AppDatabase {
        return AppDatabase.getDataDB(context)
    }

    @Singleton
    @Provides
    fun getDao(appDB: AppDatabase): VitaDao {
        return appDB.getVitaDAO()
    }
}