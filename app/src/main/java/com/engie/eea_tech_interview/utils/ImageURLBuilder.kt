package com.engie.eea_tech_interview.utils

object ImageURLBuilder {
    private const val base_url = "https://image.tmdb.org/t/p/original"


    fun buildURL(imageId: String): String {
        return base_url + imageId
    }
}