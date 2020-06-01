package com.google.vitaly.model.data.userdata

import com.google.vitaly.model.data.userdata.Meaning

data class Result(
    val text: String = "",
    val meanings: List<Meaning> = listOf()
)
