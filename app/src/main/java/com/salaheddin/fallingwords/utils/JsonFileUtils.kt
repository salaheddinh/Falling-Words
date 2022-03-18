package com.salaheddin.fallingwords.utils

import android.content.res.AssetManager
import com.google.gson.Gson
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type

object JsonFileUtils: KoinComponent {
    fun loadJSONFromAsset(): String {
        val assetManager: AssetManager by inject()
        var json = ""
        json = try {
            val input: InputStream = assetManager.open("words.json")
            val size: Int = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            buffer.toString(Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }

    fun <T> jsonToWord(jsonString: String, typeToken: Type): T {
        val gson = Gson()
        return gson.fromJson(jsonString, typeToken)
    }
}