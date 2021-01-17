package com.kavya.equipmentapplication

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.kavya.equipmentapplication.base.BaseActivity
import com.kavya.equipmentapplication.databinding.ActivityMainBinding
import com.kavya.equipmentapplication.equipment.EquipmentListViewFragment


class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(
            R.id.main_frame, EquipmentListViewFragment()).commit()
    }
}