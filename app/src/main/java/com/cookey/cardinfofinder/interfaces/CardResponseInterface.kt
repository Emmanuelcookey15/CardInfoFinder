package com.cookey.cardinfofinder.interfaces

import com.cookey.cardinfofinder.model.CardDetails

interface CardResponseInterface {

    fun loadingFailed(msg: String)
    fun loadingSuccessful(msg: String)
    fun card (data : CardDetails?)

}
