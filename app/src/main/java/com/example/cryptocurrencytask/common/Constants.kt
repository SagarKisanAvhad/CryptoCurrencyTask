package com.example.cryptocurrencytask.common

object Constants {
    const val BASE_URL = "https://api.coincap.io/"
    const val REFRESH_INTERVAL:Long = 10_000

    const val ERROR_UNEXPECTED = "An unexpected error occurred"
    const val ERROR_NO_INTERNET = "Couldn't reach server. Check your internet connection"
    const val ERROR_NO_COIN_SEARCH = "Coin not found. Search by different name"
    const val ERROR_NO_COIN = "No any Coin found."
}