package com.kavya.equipmentapplication.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.kavya.equipmentapplication.EquipmentApplication

object AppInjector {

    fun init(application: EquipmentApplication) {
        DaggerAppComponent.builder().application(application)
            .build().inject(application)
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(p0: Activity) {
            }

            override fun onActivityStarted(p0: Activity) {
            }

            override fun onActivityDestroyed(p0: Activity) {
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityStopped(p0: Activity) {
            }

            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
            }

            override fun onActivityResumed(p0: Activity) {
            }

        })
    }
}
