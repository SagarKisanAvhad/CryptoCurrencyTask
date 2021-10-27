package com.example.cryptocurrencytask.data.remote.dto

data class CoinsDto(
    val data: List<CoinDto>,
    val timestamp: Long
)