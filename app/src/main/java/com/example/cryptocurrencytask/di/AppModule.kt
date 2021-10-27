package com.example.cryptocurrencytask.di

import com.example.cryptocurrencytask.common.Constants.BASE_URL
import com.example.cryptocurrencytask.data.remote.CoinCapApi
import com.example.cryptocurrencytask.data.repository.CoinRepositoryImpl
import com.example.cryptocurrencytask.domain.repository.CoinRepository
import com.example.cryptocurrencytask.domain.usecase.CoinUseCases
import com.example.cryptocurrencytask.domain.usecase.filtercoinsbyname.FilterCoinsByNameUseCase
import com.example.cryptocurrencytask.domain.usecase.getcoins.GetCoinsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCoinCapApi(): CoinCapApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinCapApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinCapApi):CoinRepository{
        return CoinRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideCoinUseCases(repository: CoinRepository): CoinUseCases{
        return CoinUseCases(
            getCoinsUseCase = GetCoinsUseCase(repository),
            filterCoinsByNameUseCase = FilterCoinsByNameUseCase()
        )
    }
}