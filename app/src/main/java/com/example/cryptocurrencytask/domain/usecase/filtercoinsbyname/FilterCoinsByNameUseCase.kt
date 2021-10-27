package com.example.cryptocurrencytask.domain.usecase.filtercoinsbyname

import com.example.cryptocurrencytask.common.Constants.ERROR_NO_COIN_SEARCH
import com.example.cryptocurrencytask.common.Resource
import com.example.cryptocurrencytask.domain.model.Coin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FilterCoinsByNameUseCase{
    operator fun invoke(coins:List<Coin>, query: String): Flow<Resource<List<Coin>>> = flow {
        emit(Resource.Loading<List<Coin>>())
        val filteredCoinsByName = coins.filter { coin -> coin.name.startsWith(prefix = query.trim(),ignoreCase = true) }
        if (filteredCoinsByName.isEmpty())
        emit(Resource.Error<List<Coin>>(ERROR_NO_COIN_SEARCH))
        else
        emit(Resource.Success<List<Coin>>(filteredCoinsByName))
    }
}