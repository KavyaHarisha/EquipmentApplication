package com.kavya.equipmentapplication.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.kavya.equipmentapplication.data.EquipmentDataDao
import com.kavya.equipmentapplication.data.EquipmentDatabase
import com.kavya.equipmentapplication.data.EquipmentRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideEquipmentRemoteDataSource(context: Context,dao: EquipmentDataDao)
            = EquipmentRemoteDataSource(context,dao)

    @Singleton
    @Provides
    fun provideDb(app: Application) = EquipmentDatabase.getInstance(app)

    @Singleton
    @Provides
    fun provideEquipmentDao(db: EquipmentDatabase) = db.equipmentDataDao()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
}
