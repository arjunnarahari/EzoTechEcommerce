package app.ezotech.ecommerce.di

import android.content.Context
import androidx.room.Room
import app.ezotech.ecommerce.data.repository.datasource.local.ProductDao
import app.ezotech.ecommerce.data.repository.datasource.local.database.AppRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RoomDbModule {

    @Provides
    fun provideAppDao(@ApplicationContext context: Context): ProductDao {
        return Room.databaseBuilder(context, AppRoomDatabase::class.java, "ezotech_ecomm")
            .fallbackToDestructiveMigration()
            .build()
            .getAppDao()
    }
}