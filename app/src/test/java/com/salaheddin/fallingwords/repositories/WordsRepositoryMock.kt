package com.salaheddin.fallingwords.repositories

import com.google.gson.reflect.TypeToken
import com.salaheddin.fallingwords.models.Word
import com.salaheddin.fallingwords.utils.JsonFileUtils
import java.io.File

class WordsRepositoryMock : WordsRepository {
    private val successFilePath = "../app/src/test/assets/words.json"

    override suspend fun getWords(): List<Word> {
        val s = getJson(successFilePath)
        val type = object : TypeToken<List<Word>>() {}.type
        return JsonFileUtils.jsonToWord<List<Word>>(s, type)
    }

    private fun getJson(path: String): String {
        val file = File(path)
        return String(file.readBytes())
    }
}