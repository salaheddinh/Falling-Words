package com.salaheddin.fallingwords.repositories

import com.salaheddin.fallingwords.models.Word

class WordsRepositorySameMock : WordsRepository {
    override suspend fun getWords(): List<Word> {
        return arrayListOf(
            Word("testEng", "testSpa"),
            Word("testEng", "testSpa"),
            Word("testEng", "testSpa"),
            Word("testEng", "testSpa"),
            Word("testEng", "testSpa"),
            Word("testEng", "testSpa"),
        )
    }
}