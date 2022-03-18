package com.salaheddin.fallingwords

import android.app.Application
import com.salaheddin.fallingwords.di.AppModule
import com.salaheddin.fallingwords.di.RepositoryModule
import com.salaheddin.fallingwords.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class FallingWordsApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FallingWordsApplication)

            printLogger(level = Level.DEBUG)
            modules(
                listOf(
                    AppModule,
                    ViewModelModule,
                    RepositoryModule
                )
            )
        }
    }
}