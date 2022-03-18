package com.salaheddin.fallingwords.models

import com.google.gson.annotations.SerializedName

data class Word(
    @SerializedName("text_eng")
    val eng: String,
    @SerializedName("text_spa")
    val spa: String
)