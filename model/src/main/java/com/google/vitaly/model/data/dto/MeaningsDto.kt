package com.google.vitaly.model.data.dto

import com.google.gson.annotations.SerializedName
import geekbrains.ru.model.data.dto.TranslationDto

class MeaningsDto(
    @field:SerializedName("translation") val translation: TranslationDto?,
    @field:SerializedName("imageUrl") val imageUrl: String?,
    @field:SerializedName("previewUrl") val previewUrl: String?
)
