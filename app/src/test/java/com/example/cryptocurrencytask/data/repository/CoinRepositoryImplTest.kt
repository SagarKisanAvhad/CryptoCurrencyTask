package com.example.cryptocurrencytask.data.repository

import com.example.cryptocurrencytask.common.Constants.ERROR_NO_INTERNET
import com.example.cryptocurrencytask.common.Constants.ERROR_UNEXPECTED
import com.example.cryptocurrencytask.data.remote.CoinCapApi
import com.example.cryptocurrencytask.data.remote.dto.CoinDto
import com.example.cryptocurrencytask.data.remote.dto.CoinsDto
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.MockitoKotlinException
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class CoinRepositoryImplTest{

    @Test
    fun `should get list of coin`() = runBlocking{
        val coinDto = CoinsDto(
            data = listOf(CoinDto(
                changePercent24Hr = "1.2",
                explorer = "",
                id = "1",
                marketCapUsd = "432212.32",
                maxSupply = "",
                name=" BBB",
                priceUsd = "4333.55",
                rank = "5",
                supply = "ss",
                symbol = "$#",
                volumeUsd24Hr = "5000000",
                vwap24Hr = "123"
            )),
            timestamp = 21232
        )

        val api = mock<CoinCapApi>(){
            onBlocking{getCoins()} doReturn coinDto
        }

        val repository = CoinRepositoryImpl(api)
        val list = repository.getCoins()
        assertThat(list).isNotEmpty()
    }

    @Test
    fun `should throw No Internet Exception`() {
        runBlocking {
            val api = mock<CoinCapApi>(){
                onBlocking{getCoins()} doThrow MockitoKotlinException(ERROR_NO_INTERNET,IOException())
            }
            val repository = CoinRepositoryImpl(api)
            try {
                repository.getCoins()
            }catch (e:Exception){
                assertThat(e.localizedMessage).isEqualTo(ERROR_NO_INTERNET)
            }
        }
    }

    @Test
    fun `should throw unexpected error`() {
        runBlocking {
            val api = mock<CoinCapApi>(){
                onBlocking{getCoins()} doThrow MockitoKotlinException(ERROR_UNEXPECTED,RuntimeException())
            }
            val repository = CoinRepositoryImpl(api)
            try {
                repository.getCoins()
            }catch (e:Exception){
                assertThat(e.localizedMessage).isEqualTo(ERROR_UNEXPECTED)
            }
        }
    }
}