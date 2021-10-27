package com.example.cryptocurrencytask.data.remote

import com.example.cryptocurrencytask.data.remote.dto.CoinsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinCapApi {

    @GET("v2/assets")
    suspend fun getCoins(
        @Query("limit") limit:Int = 20,
        @Query("offset") offset:Int = 0,
    ): CoinsDto
}