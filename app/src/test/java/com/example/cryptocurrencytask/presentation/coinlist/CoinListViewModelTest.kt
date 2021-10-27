package com.example.cryptocurrencytask.presentation.coinlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.cryptocurrencytask.common.Constants
import com.example.cryptocurrencytask.data.repository.FakeCoinRepository
import com.example.cryptocurrencytask.domain.model.Coin
import com.example.cryptocurrencytask.domain.usecase.CoinUseCases
import com.example.cryptocurrencytask.domain.usecase.filtercoinsbyname.FilterCoinsByNameUseCase
import com.example.cryptocurrencytask.domain.usecase.getcoins.GetCoinsUseCase
import com.example.cryptocurrencytask.presentation.TestCoroutineRule
import com.example.cryptocurrencytask.presentation.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CoinListViewModelTest{

    private lateinit var viewModel:CoinListViewModel
    @Mock
    private lateinit var fakeCoinRepository: FakeCoinRepository
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun `filter coins by empty query, returns all coins`()= testCoroutineRule.runBlockingTest {
        val list = listOf<Coin>(
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
        doReturn(list)
            .`when`(fakeCoinRepository)
            .getCoins()
        viewModel = CoinListViewModel(CoinUseCases(
            getCoinsUseCase = GetCoinsUseCase(fakeCoinRepository),
            filterCoinsByNameUseCase = FilterCoinsByNameUseCase()
        ))

        viewModel.getFilteredCoinsByName("")
        val value = viewModel.state.getOrAwaitValueTest()
        assertThat(value.coins).hasSize(list.size)
    }

    @Test
    fun `filter coins by blank query, returns all coins`()= testCoroutineRule.runBlockingTest {
        val list = listOf<Coin>(
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
        doReturn(list)
            .`when`(fakeCoinRepository)
            .getCoins()
        viewModel = CoinListViewModel(CoinUseCases(
            getCoinsUseCase = GetCoinsUseCase(fakeCoinRepository),
            filterCoinsByNameUseCase = FilterCoinsByNameUseCase()
        ))

        viewModel.getFilteredCoinsByName("   ")
        val value = viewModel.state.getOrAwaitValueTest()
        assertThat(value.coins).hasSize(list.size)
    }

    @Test
    fun `filter coins by matching query, returns coins that start with query`()= testCoroutineRule.runBlockingTest {
        stubRepositoryForReturningListOfCoin()
        viewModel = CoinListViewModel(CoinUseCases(
            getCoinsUseCase = GetCoinsUseCase(fakeCoinRepository),
            filterCoinsByNameUseCase = FilterCoinsByNameUseCase()
        ))

        viewModel.getFilteredCoinsByName("bi")
        val value = viewModel.state.getOrAwaitValueTest()
        assertThat(value.coins).hasSize(2)
    }

    @Test
    fun `filter coins by mismatching query, returns error message`()= testCoroutineRule.runBlockingTest {
        stubRepositoryForReturningListOfCoin()
        viewModel = CoinListViewModel(CoinUseCases(
            getCoinsUseCase = GetCoinsUseCase(fakeCoinRepository),
            filterCoinsByNameUseCase = FilterCoinsByNameUseCase()
        ))

        viewModel.getFilteredCoinsByName("xixi")
        val value = viewModel.state.getOrAwaitValueTest()
        assertThat(value.error).isEqualTo(Constants.ERROR_NO_COIN_SEARCH)
    }

    private suspend fun stubRepositoryForReturningListOfCoin() {
        val list = listOf<Coin>(
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
        doReturn(list)
            .`when`(fakeCoinRepository)
            .getCoins()
    }
}