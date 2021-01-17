package com.kavya.equipmentapplication.equipment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kavya.equipmentapplication.data.EquipmentRemoteDataSource
import com.kavya.equipmentapplication.data.State
import com.kavya.equipmentapplication.model.EquipmentResponseItem
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class EquipmentViewModel @Inject constructor(val repository: EquipmentRemoteDataSource) : ViewModel() {

    private val _equipmentData = MutableLiveData<State<List<EquipmentResponseItem>>>()
    val equipmentData: LiveData<State<List<EquipmentResponseItem>>>
        get() = _equipmentData
    @InternalCoroutinesApi
    fun getEquipmentList(){
        viewModelScope.launch {
            repository.getAllEquipments().collect {
                _equipmentData.value = it
            }
        }
    }
}
