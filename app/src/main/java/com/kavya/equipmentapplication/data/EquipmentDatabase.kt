package com.kavya.equipmentapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kavya.equipmentapplication.model.EquipmentResponseItem

@Database(entities = [EquipmentResponseItem::class],
        version = 1, exportSchema = false)

abstract class EquipmentDatabase : RoomDatabase() {

    abstract fun equipmentDataDao(): EquipmentDataDao


    companion object {

        @Volatile
        private var instance: EquipmentDatabase? = null

        fun getInstance(context: Context): EquipmentDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): EquipmentDatabase {
            return Room.databaseBuilder(context, EquipmentDatabase::class.java, "equipment-db")
                    .addCallback(object : RoomDatabase.Callback() {
                    })
                    .build()
        }
    }
}
