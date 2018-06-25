package com.carlosughini.appmarvel.models.entity

data class Response(
        val code: Int,
        val etag: String,
        val data: Data
)