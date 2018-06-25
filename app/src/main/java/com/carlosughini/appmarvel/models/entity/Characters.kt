package com.carlosughini.appmarvel.models.entity

data class Characters(
        val id: Int,
        val name: String,
        val description: String,
        val thumbnail: Thumbnail
)