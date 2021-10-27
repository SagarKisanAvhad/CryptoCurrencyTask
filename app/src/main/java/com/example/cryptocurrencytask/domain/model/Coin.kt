package com.example.cryptocurrencytask.domain.model

data class Coin(
    val name: String,
    val priceUsd: String,
    val changePercent24Hr: String,
    val isValueInGreen: Boolean
)
