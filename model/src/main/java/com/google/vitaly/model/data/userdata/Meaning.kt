package com.google.vitaly.model.data.userdata

data class Meaning(
    val translatedMeaning: TranslatedMeaning = TranslatedMeaning(),
    val imageUrl: String = "",
    val previewUrl: String = ""
)
