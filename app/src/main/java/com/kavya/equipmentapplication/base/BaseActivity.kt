package com.kavya.equipmentapplication.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.kavya.equipmentapplication.di.ViewModelFactory
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector, LifecycleObserver {

    // This object is needed to enable fragment to know about the activity dependencies
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        //This provides every object annotated with @Inject
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }
}