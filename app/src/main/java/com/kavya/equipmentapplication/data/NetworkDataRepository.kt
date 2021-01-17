package com.kavya.equipmentapplication.data

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.kavya.equipmentapplication.model.EquipmentResponse
import com.kavya.equipmentapplication.model.EquipmentResponseItem
import kotlinx.coroutines.flow.*


abstract class  NetworkDataRepository<RESULT> {

    fun asFlow() = flow<State<RESULT>> {

        emit(State.loading())
        emit(State.success(fetchFromLocal().first()))
        val data = fetchFromRemote()
        if (data != null) {
            saveRemoteData(data as List<EquipmentResponseItem>)
        } else {
            emit(State.error("No equipment data available"))
        }

        emitAll(
            fetchFromLocal().map {
                State.success(it)
            }
        )
    }.catch { e ->
        emit(State.error("Network error! Can't get latest equipment."))
        e.printStackTrace()
    }


    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: List<EquipmentResponseItem>)


    @MainThread
    protected abstract fun fetchFromLocal(): Flow<RESULT>


    @MainThread
    protected abstract suspend fun fetchFromRemote(): EquipmentResponse?
}
