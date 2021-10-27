package com.example.cryptocurrencytask.data.repository

import com.example.cryptocurrencytask.common.Constants.ERROR_NO_INTERNET
import com.example.cryptocurrencytask.common.Constants.ERROR_UNEXPECTED
import com.example.cryptocurrencytask.data.remote.CoinCapApi
import com.example.cryptocurrencytask.data.remote.dto.mapToCoin
import com.example.cryptocurrencytask.domain.model.Coin
import com.example.cryptocurrencytask.domain.repository.CoinRepository
import java.io.IOException
import javax.inject.Inject
import kotlin.jvm.Throws

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinCapApi
) : CoinRepository {
    override suspend fun getCoins(): List<Coin> {
        return try {
            api.getCoins().data.mapNotNull { coinDto -> coinDto.mapToCoin() }
        } catch (e: IOException) {
            throw IOException(ERROR_NO_INTERNET)
        } catch (e: Exception) {
            throw Exception(e.localizedMessage ?: ERROR_UNEXPECTED)
        }
    }
}