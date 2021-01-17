package com.kavya.equipmentapplication.di


import com.kavya.equipmentapplication.equipment.EquipmentListViewFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeCompanyListViewFragment(): EquipmentListViewFragment
}
