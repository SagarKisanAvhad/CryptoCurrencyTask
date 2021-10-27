package com.example.cryptocurrencytask.presentation.coinlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencytask.common.Constants.ERROR_NO_COIN
import com.example.cryptocurrencytask.common.Constants.ERROR_NO_COIN_SEARCH
import com.example.cryptocurrencytask.common.Constants.ERROR_UNEXPECTED
import com.example.cryptocurrencytask.common.Resource
import com.example.cryptocurrencytask.domain.model.Coin
import com.example.cryptocurrencytask.domain.usecase.CoinUseCases
import com.example.cryptocurrencytask.domain.usecase.filtercoinsbyname.FilterCoinsByNameUseCase
import com.example.cryptocurrencytask.domain.usecase.getcoins.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val coinUseCases: CoinUseCases,
): ViewModel(){
    private val _state = MutableLiveData<CoinListState>(CoinListState())
    val state: LiveData<CoinListState> = _state

    private var coinList:List<Coin> = emptyList()

    init {
        getCoins()
    }

    private fun getCoins(){
        coinUseCases.getCoinsUseCase().onEach { resource ->
            when(resource){
                is Resource.Loading ->{
                    _state.value = CoinListState(isLoading = true)
                }
                is Resource.Success ->{
                    coinList = resource.data?: emptyList()
                    _state.value = CoinListState(coins = resource.data?: emptyList())
                }
                is Resource.Error ->{
                    _state.value = CoinListState(error = resource.message?:ERROR_UNEXPECTED)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getFilteredCoinsByName(query:String){
        coinUseCases.filterCoinsByNameUseCase(coinList,query).onEach {resource ->
            when(resource){
                is Resource.Loading ->{
                    _state.value = CoinListState(isLoading = true)
                }
                is Resource.Success ->{
                    _state.value = CoinListState(coins = resource.data?: emptyList())
                }
                is Resource.Error ->{
                    _state.value = CoinListState(error = resource.message?: ERROR_NO_COIN_SEARCH)
                }
            }
        }.launchIn(viewModelScope)
    }
}