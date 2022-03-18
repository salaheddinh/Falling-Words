package com.salaheddin.fallingwords.di

import com.salaheddin.fallingwords.ui.gameScreen.GameViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { GameViewModel(get()) }
}