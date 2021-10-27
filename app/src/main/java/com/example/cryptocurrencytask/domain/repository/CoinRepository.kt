package com.example.cryptocurrencytask.domain.repository

import com.example.cryptocurrencytask.common.Resource
import com.example.cryptocurrencytask.domain.model.Coin

interface CoinRepository {
    suspend fun getCoins(): List<Coin>
}