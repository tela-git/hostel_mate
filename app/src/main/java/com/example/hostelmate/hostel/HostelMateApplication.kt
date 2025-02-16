package com.example.hostelmate.hostel

import android.app.Application
import com.example.hostelmate.hostel.data.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HostelMateApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@HostelMateApplication)
            modules(
                appModule
            )
        }
    }
}