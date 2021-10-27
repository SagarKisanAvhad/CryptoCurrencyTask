package com.example.cryptocurrencytask.data.remote.dto

import com.example.cryptocurrencytask.domain.model.Coin

data class CoinDto(
    val changePercent24Hr: String,
    val explorer: String,
    val id: String,
    val marketCapUsd: String,
    val maxSupply: String,
    val name: String,
    val priceUsd: String,
    val rank: String,
    val supply: String,
    val symbol: String,
    val volumeUsd24Hr: String,
    val vwap24Hr: String
)

fun CoinDto.mapToCoin(): Coin?{
    return try {
        Coin(
            name = name,
            priceUsd = "%.2f".format(priceUsd.toDouble()).prefixDollar(),
            changePercent24Hr = changePercent24Hr.suffixPercent(),
            isValueInGreen = "%.3f".format(changePercent24Hr.toFloat()).toFloat() > 0.000
        )
    }catch (_:Exception){
        null
    }
}

private fun String.prefixDollar() = "\$$this"
private fun String.suffixPercent() = if ("%.3f".format(this.toFloat()).toFloat() > 0.000) "+$this%" else "-$this%"