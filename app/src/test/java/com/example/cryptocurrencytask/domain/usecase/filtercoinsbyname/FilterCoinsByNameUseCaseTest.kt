package com.example.cryptocurrencytask.domain.usecase.filtercoinsbyname

import com.example.cryptocurrencytask.common.Constants.ERROR_NO_COIN_SEARCH
import com.example.cryptocurrencytask.common.Resource
import com.example.cryptocurrencytask.data.repository.FakeCoinRepository
import com.example.cryptocurrencytask.domain.model.Coin
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FilterCoinsByNameUseCaseTest{
    private lateinit var filterCoinsByNameUseCase: FilterCoinsByNameUseCase
    private lateinit var fakeCoinRepository: FakeCoinRepository

    @Before
    fun setUp(){
        fakeCoinRepository = FakeCoinRepository()
        filterCoinsByNameUseCase = FilterCoinsByNameUseCase()
    }

    @Test
    fun `search query matches with one of coin name in list, then emit first item of loading type`() = runBlocking{
        val coins = listOf(
            Coin(
                name = "Bitcoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Litecoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Dogecoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Detacoin",
                changePercent24Hr = "-1.24%",
                priceUsd = "$40000",
                isValueInGreen = false
            ),
            Coin(
                name = "Binancecoin",
                changePercent24Hr = "-2.24%",
                priceUsd = "$40000",
                isValueInGreen = false
            )
        )
        val searchQuery = "Bi"
        val firstItem = filterCoinsByNameUseCase(coins,searchQuery).first()
        assertThat(firstItem).isInstanceOf(Resource.Loading<List<Coin>>()::class.java)
    }

    @Test
    fun `search query not matches with one of coin name in list, then emit first item of loading type`() = runBlocking{
        val coins = listOf(
            Coin(
                name = "Bitcoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Litecoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Dogecoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Detacoin",
                changePercent24Hr = "-1.24%",
                priceUsd = "$40000",
                isValueInGreen = false
            ),
            Coin(
                name = "Binancecoin",
                changePercent24Hr = "-2.24%",
                priceUsd = "$40000",
                isValueInGreen = false
            )
        )
        val searchQuery = "Xelecoin"
        val firstItem = filterCoinsByNameUseCase(coins,searchQuery).first()
        assertThat(firstItem).isInstanceOf(Resource.Loading<List<Coin>>()::class.java)
    }

    @Test
    fun `search query matches with one of coin name in list, then emit second item of success type`() = runBlocking{
        val coins = listOf(
            Coin(
                name = "Bitcoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Litecoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Dogecoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Detacoin",
                changePercent24Hr = "-1.24%",
                priceUsd = "$40000",
                isValueInGreen = false
            ),
            Coin(
                name = "Binancecoin",
                changePercent24Hr = "-2.24%",
                priceUsd = "$40000",
                isValueInGreen = false
            )
        )
        val searchQuery = "Bi"
        val secondItem = filterCoinsByNameUseCase(coins,searchQuery).drop(1).first()
        assertThat(secondItem).isInstanceOf(Resource.Success<List<Coin>>(listOf(Coin(
            name = "Bitcoin",
            changePercent24Hr = "1.24%",
            priceUsd = "$40000",
            isValueInGreen = true
        ),Coin(
            name = "Binancecoin",
            changePercent24Hr = "-2.24%",
            priceUsd = "$40000",
            isValueInGreen = false
        )))::class.java)
        assertThat(secondItem.data).hasSize(2)
    }

    @Test
    fun `search query not matches with one of coin name in list, then emit second item of error type`() = runBlocking{
        val coins = listOf(
            Coin(
                name = "Bitcoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Litecoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Dogecoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Detacoin",
                changePercent24Hr = "-1.24%",
                priceUsd = "$40000",
                isValueInGreen = false
            ),
            Coin(
                name = "Binancecoin",
                changePercent24Hr = "-2.24%",
                priceUsd = "$40000",
                isValueInGreen = false
            )
        )
        val searchQuery = "Xelecoin"
        val secondItem = filterCoinsByNameUseCase(coins,searchQuery).drop(1).first()
        assertThat(secondItem).isInstanceOf(Resource.Error<List<Coin>>(ERROR_NO_COIN_SEARCH)::class.java)
        assertThat(secondItem.message).isEqualTo(ERROR_NO_COIN_SEARCH)
    }

    @Test
    fun `search query matches with one of coin name in list but case mismatch, then emit second item of success type`() = runBlocking{
        val coins = listOf(
            Coin(
                name = "Bitcoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Litecoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Dogecoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Detacoin",
                changePercent24Hr = "-1.24%",
                priceUsd = "$40000",
                isValueInGreen = false
            ),
            Coin(
                name = "Binancecoin",
                changePercent24Hr = "-2.24%",
                priceUsd = "$40000",
                isValueInGreen = false
            )
        )
        val searchQuery = "bI"
        val secondItem = filterCoinsByNameUseCase(coins,searchQuery).drop(1).first()
        assertThat(secondItem).isInstanceOf(Resource.Success<List<Coin>>(listOf(Coin(
            name = "Bitcoin",
            changePercent24Hr = "1.24%",
            priceUsd = "$40000",
            isValueInGreen = true
        ),Coin(
            name = "Binancecoin",
            changePercent24Hr = "-2.24%",
            priceUsd = "$40000",
            isValueInGreen = false
        )))::class.java)
        assertThat(secondItem.data).hasSize(2)
        assertThat(secondItem.data?.size).isLessThan(coins.size)
    }

    @Test
    fun `search query is empty, then emit second item of success type`() = runBlocking{
        val coins = listOf(
            Coin(
                name = "Bitcoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Litecoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Dogecoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Detacoin",
                changePercent24Hr = "-1.24%",
                priceUsd = "$40000",
                isValueInGreen = false
            ),
            Coin(
                name = "Binancecoin",
                changePercent24Hr = "-2.24%",
                priceUsd = "$40000",
                isValueInGreen = false
            )
        )
        val searchQuery = ""
        val secondItem = filterCoinsByNameUseCase(coins, searchQuery).drop(1).first()
        assertThat(secondItem).isInstanceOf(Resource.Success<List<Coin>>(coins)::class.java)
        assertThat(secondItem.data).hasSize(coins.size)
    }

    @Test
    fun `search query is black, then emit second item of success type`() = runBlocking{
        val coins = listOf(
            Coin(
                name = "Bitcoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Litecoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Dogecoin",
                changePercent24Hr = "1.24%",
                priceUsd = "$40000",
                isValueInGreen = true
            ),
            Coin(
                name = "Detacoin",
                changePercent24Hr = "-1.24%",
                priceUsd = "$40000",
                isValueInGreen = false
            ),
            Coin(
                name = "Binancecoin",
                changePercent24Hr = "-2.24%",
                priceUsd = "$40000",
                isValueInGreen = false
            )
        )
        val searchQuery = "   "
        val secondItem = filterCoinsByNameUseCase(coins, searchQuery).drop(1).first()
        assertThat(secondItem).isInstanceOf(Resource.Success<List<Coin>>(coins)::class.java)
        assertThat(secondItem.data).hasSize(coins.size)
    }

    @Test
    fun `coin list empty, then emit second item of error type`() = runBlocking{
        val coins = emptyList<Coin>()
        val searchQuery = "Bi"
        val secondItem = filterCoinsByNameUseCase(coins, searchQuery).drop(1).first()
        assertThat(secondItem).isInstanceOf(Resource.Error<List<Coin>>(ERROR_NO_COIN_SEARCH)::class.java)
    }
}