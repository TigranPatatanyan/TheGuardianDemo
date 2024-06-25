package com.tigran.theguardiandemo

import android.app.Application
import com.tigran.theguardiandemo.di.koinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TheGuardianDemoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Android context
            androidContext(this@TheGuardianDemoApp)
            // modules
            modules(koinModule)
        }
    }

}