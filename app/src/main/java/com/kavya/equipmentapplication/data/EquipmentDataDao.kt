package com.kavya.equipmentapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kavya.equipmentapplication.model.EquipmentResponseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipmentDataDao {

    @Query("SELECT * FROM equipment")
    fun getEquipmentData(): Flow<List<EquipmentResponseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<EquipmentResponseItem>)
}
