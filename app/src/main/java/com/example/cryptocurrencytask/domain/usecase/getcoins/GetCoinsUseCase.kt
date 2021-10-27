package com.example.cryptocurrencytask.domain.usecase.getcoins

import com.example.cryptocurrencytask.common.Constants
import com.example.cryptocurrencytask.common.Constants.ERROR_UNEXPECTED
import com.example.cryptocurrencytask.common.Constants.REFRESH_INTERVAL
import com.example.cryptocurrencytask.common.Resource
import com.example.cryptocurrencytask.domain.model.Coin
import com.example.cryptocurrencytask.domain.repository.CoinRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {


    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
      //  while (true){
            emit(Resource.Loading<List<Coin>>())
            val coins = repository.getCoins()
            if (coins.isEmpty())
                emit(Resource.Error<List<Coin>>(Constants.ERROR_NO_COIN))
            else
                emit(Resource.Success<List<Coin>>(coins))
         //   delay(REFRESH_INTERVAL)
       // }

    }.catch { throwable: Throwable ->
        emit(Resource.Error<List<Coin>>(throwable.localizedMessage ?: ERROR_UNEXPECTED))
    }
}