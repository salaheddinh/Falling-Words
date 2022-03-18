package com.salaheddin.fallingwords.di

import com.salaheddin.fallingwords.repositories.WordsRepository
import com.salaheddin.fallingwords.repositories.WordsRepositoryImpl
import org.koin.dsl.module

val RepositoryModule = module {
    single<WordsRepository> { WordsRepositoryImpl() }
}