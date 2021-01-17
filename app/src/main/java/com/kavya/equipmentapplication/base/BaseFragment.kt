package com.kavya.equipmentapplication.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.kavya.equipmentapplication.di.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment : Fragment(), LifecycleObserver {

    protected open fun setupListeners() {}

    protected open fun initViewModels() {}

    protected open fun subscribeToViewModels() {}

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var baseActivity: BaseActivity

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if (context is BaseActivity) {
            baseActivity = context
        } else {
            throw IllegalStateException("Every fragment has to be attached to BaseActivity")
        }
        lifecycle.addObserver(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListeners()
        initViewModels()
        subscribeToViewModels()
    }

    inline fun <reified T : ViewModel> getActivityScopeViewModel(): T =
            ViewModelProvider(baseActivity, viewModelFactory)[T::class.java]

    inline fun <reified T : ViewModel> getFragmentScopeViewModel(): T =
            ViewModelProvider(this, viewModelFactory)[T::class.java]

    inline fun <reified T : Any> LiveData<out T>.observe(crossinline observer: (T) -> Unit) {
        observe(viewLifecycleOwner, Observer<T> { observer(it) })
    }
}