package com.kavya.equipmentapplication.data

import android.content.Context
import com.google.gson.Gson
import com.kavya.equipmentapplication.model.EquipmentResponse
import com.kavya.equipmentapplication.model.EquipmentResponseItem
import kotlinx.coroutines.flow.*
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class EquipmentRemoteDataSource @Inject constructor(val context: Context, val dataDao: EquipmentDataDao) {

    fun getAllEquipments(): Flow<State<List<EquipmentResponseItem>>> {
        return object : NetworkDataRepository<List<EquipmentResponseItem>>() {

            override suspend fun saveRemoteData(response: List<EquipmentResponseItem>) {
                dataDao.insertAll(response)
            }

            override fun fetchFromLocal(): Flow<List<EquipmentResponseItem>> = dataDao.getEquipmentData()

            override suspend fun fetchFromRemote(): EquipmentResponse? = parseGsonResponse(
                    getJsonFromAssets(context, "assignment.json"),
                    EquipmentResponse::class.java
            )
        }.asFlow()
    }

}


fun getJsonFromAssets(context: Context, fileName: String?): String? {
    val jsonString: String
    jsonString = try {
        val inputStream: InputStream? = fileName?.let { context.assets.open(it) }
        val size: Int = inputStream!!.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer)
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
    return jsonString
}

fun <T> parseGsonResponse(serverResponse: String?, target: Class<T>?): T? {
    var data: T? = null
    val gson = Gson()
    try {
        data = gson.fromJson(serverResponse, target)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return data
}



