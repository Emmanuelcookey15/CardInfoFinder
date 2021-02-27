package com.cookey.cardinfofinder.repository

import com.cookey.cardinfofinder.api.RetrofitClient
import com.cookey.cardinfofinder.interfaces.CardResponseInterface
import com.cookey.cardinfofinder.model.CardDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    fun getCardDetails(card_num: String, callback: CardResponseInterface) {

        RetrofitClient.getInstance()?.getApi()?.getCard(card_num)?.enqueue(object :
            Callback<CardDetails> {
            override fun onFailure(call: Call<CardDetails>, t: Throwable) {
                callback.loadingFailed(t.toString())
            }

            override fun onResponse(call: Call<CardDetails>, response: Response<CardDetails>) {

                if (response.isSuccessful && response.body() != null) {
                    when (response.code()) {
                        200 -> {
                            response.body()?.let {
                                callback.card(it)
                            }

                            callback.loadingSuccessful("Card is valid")
                        }

                        else -> {
                            callback.loadingFailed("This request is Invalid.. please try again")
                        }
                    }
                } else {
                    callback.loadingFailed("Invalid request..Check your card details and try again")
                }
            }
        })
    }

}