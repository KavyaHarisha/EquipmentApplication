package com.kavya.equipmentapplication.equipment

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.Toast
import androidx.lifecycle.observe
import com.kavya.equipmentapplication.R
import com.kavya.equipmentapplication.base.BaseFragment
import com.kavya.equipmentapplication.data.State
import com.kavya.equipmentapplication.databinding.FragmentEquipmentListViewBinding
import com.kavya.equipmentapplication.model.EquipmentResponseItem
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.InternalCoroutinesApi


class EquipmentListViewFragment : BaseFragment() {

    private lateinit var viewModel: EquipmentViewModel
    lateinit var binding: FragmentEquipmentListViewBinding

    lateinit var adapter: ExpandableListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        AndroidSupportInjection.inject(this)
         binding = FragmentEquipmentListViewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun initViewModels() {
        viewModel = getFragmentScopeViewModel()
    }

    @InternalCoroutinesApi
    override fun subscribeToViewModels() {
        viewModel.getEquipmentList()
        subscribeUi(binding)
    }

    private fun subscribeUi(binding: FragmentEquipmentListViewBinding) {
        viewModel.equipmentData.observe(viewLifecycleOwner){
                state ->
            when(state){
                is State.Loading -> {
                    binding.progressBar.visibility= View.VISIBLE
                }
                is State.Success ->{
                    if(state.data.isNotEmpty()) {
                        binding.progressBar.visibility= View.GONE
                        val listData = getEquipmentData(state.data)
                        adapter = CustomExpandableListAdapter(listData.keys.toMutableList(), listData)
                        binding.equipmentExpendableList.setAdapter(adapter)
                        setExpandableIndicatorBounds()
                    }
                }
                is State.Error -> {
                    binding.progressBar.visibility= View.GONE
                    Toast.makeText(activity,state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setExpandableIndicatorBounds(){
        val metrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val display = activity!!.display
            display?.getRealMetrics(metrics)
        } else {
            @Suppress("DEPRECATION")
            val display = activity!!.windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(metrics)
        }
        val width = metrics.widthPixels
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            binding.equipmentExpendableList.setIndicatorBounds(
                    width - getPixelFromDips(50f), width - getPixelFromDips(10f))
        } else {
            binding.equipmentExpendableList.setIndicatorBoundsRelative(
                    width - getPixelFromDips(50f), width - getPixelFromDips(10f))
        }

    }

    private fun getPixelFromDips(pixels: Float): Int {
        val scale = resources.displayMetrics.density
        return (pixels * scale + 0.5f).toInt()
    }

    private fun getEquipmentData(items:List<EquipmentResponseItem>): HashMap<String, List<String>>{
        val expandableListDetail = HashMap<String, List<String>>()
        for(i in items.indices){
            val expandList = arrayListOf<String>()
            expandList.add(resources.getString(R.string.item_vin,items[i].vin))
            expandList.add(resources.getString(R.string.item_year,items[i].year))
            expandList.add(resources.getString(R.string.item_make,items[i].make))
            expandList.add(resources.getString(R.string.item_value,items[i].value))
            expandList.add(resources.getString(R.string.item_length,items[i].length))
            expandableListDetail[items[i].make] = expandList
        }
        return expandableListDetail
    }
}