package com.example.cryptocurrencytask.domain.usecase.getcoins

import com.example.cryptocurrencytask.common.Constants.ERROR_NO_COIN
import com.example.cryptocurrencytask.common.Resource
import com.example.cryptocurrencytask.data.repository.FakeCoinRepository
import com.example.cryptocurrencytask.domain.model.Coin
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.IOException

class GetCoinsUseCaseTest{

    private lateinit var getCoinsUseCase: GetCoinsUseCase
    private lateinit var fakeCoinRepository: FakeCoinRepository

    @Before
    fun setUp(){
        fakeCoinRepository = FakeCoinRepository()
        getCoinsUseCase = GetCoinsUseCase(fakeCoinRepository)
    }

    @Test
    fun `given coin list, then emits first item of type Loading`() = runBlocking {
        //arrange
        stubRepositoryForReturningListOfCoin()
        //act
        val firstItem = getCoinsUseCase().first()
        //assert
        assertThat(firstItem).isInstanceOf(Resource.Loading<List<Coin>>()::class.java)
    }

    @Test
    fun `given exception, then emits first item of type Loading`() = runBlocking {
        //arrange
        stubRepositoryForReturningListOfCoin()
        //act
        val firstItem = getCoinsUseCase().first()
        //assert
        assertThat(firstItem).isInstanceOf(Resource.Loading<List<Coin>>()::class.java)
    }

    @Test
    fun `given coin list, then emits second item having same data`() = runBlocking {
        //arrange
        stubRepositoryForReturningListOfCoin()
        //act
        val secondItem = getCoinsUseCase().drop(1).first()
        //assert
        assertThat(secondItem.data).hasSize(fakeCoinRepository.coins.size)
        assertThat(secondItem.data).isEqualTo(fakeCoinRepository.coins)
    }

    @Test
    fun `given coin list, then emits second item of type Success`() = runBlocking {
        //arrange
        stubRepositoryForReturningListOfCoin()
        //act
        val secondItem = getCoinsUseCase().drop(1).first()
        //assert
        assertThat(secondItem).isInstanceOf(Resource.Success<List<Coin>>(fakeCoinRepository.coins)::class.java)
    }

    @Test
    fun `given empty coin list, then emits second item of type Error`() = runBlocking {
        //arrange
        //act
        val secondItem = getCoinsUseCase().drop(1).first()
        //assert
        assertThat(secondItem).isInstanceOf(Resource.Error<List<Coin>>(ERROR_NO_COIN)::class.java)
    }

    @Test
    fun `given empty coin list, then emits second item having message no any coin found`() = runBlocking {
        //arrange
        //act
        val secondItem = getCoinsUseCase().drop(1).first()
        //assert
        assertThat(secondItem.message).isEqualTo(ERROR_NO_COIN)
    }

    private fun stubRepositoryForReturningListOfCoin() {
        fakeCoinRepository.coins.addAll(
            listOf(
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
        )
    }

}