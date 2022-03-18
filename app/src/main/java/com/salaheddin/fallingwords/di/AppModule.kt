package com.salaheddin.fallingwords.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val AppModule = module {
    factory { androidContext().assets }
}