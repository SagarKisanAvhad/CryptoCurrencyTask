package com.example.cryptocurrencytask.data.repository

import com.example.cryptocurrencytask.common.Resource
import com.example.cryptocurrencytask.domain.model.Coin
import com.example.cryptocurrencytask.domain.repository.CoinRepository

class FakeCoinRepository : CoinRepository {

    val coins = mutableListOf<Coin>()

    override suspend fun getCoins(): List<Coin> {
        return coins
    }


}