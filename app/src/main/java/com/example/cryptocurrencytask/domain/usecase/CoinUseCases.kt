package com.example.cryptocurrencytask.domain.usecase

import com.example.cryptocurrencytask.domain.usecase.filtercoinsbyname.FilterCoinsByNameUseCase
import com.example.cryptocurrencytask.domain.usecase.getcoins.GetCoinsUseCase


data class CoinUseCases(
    val getCoinsUseCase: GetCoinsUseCase,
    val filterCoinsByNameUseCase: FilterCoinsByNameUseCase
)