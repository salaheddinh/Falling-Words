package com.salaheddin.fallingwords.repositories

import com.google.gson.reflect.TypeToken
import com.salaheddin.fallingwords.models.Word
import com.salaheddin.fallingwords.utils.JsonFileUtils

class WordsRepositoryImpl: WordsRepository {

    override suspend fun getWords(): List<Word> {
        val type = object : TypeToken<List<Word>>() {}.type
        return JsonFileUtils.jsonToWord<List<Word>>(JsonFileUtils.loadJSONFromAsset(),type)
    }
}