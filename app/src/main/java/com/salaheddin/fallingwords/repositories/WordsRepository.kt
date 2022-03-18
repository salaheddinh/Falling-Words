package com.salaheddin.fallingwords.repositories

import com.salaheddin.fallingwords.models.Word

interface WordsRepository {
    suspend fun getWords(): List<Word>
}