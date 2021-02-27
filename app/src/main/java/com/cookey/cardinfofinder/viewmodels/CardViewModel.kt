package com.cookey.cardinfofinder.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cookey.cardinfofinder.interfaces.CardResponseInterface
import com.cookey.cardinfofinder.model.CardDetails
import com.cookey.cardinfofinder.repository.Repository

class CardViewModel: ViewModel(), CardResponseInterface {

    var repository: Repository = Repository()

    //Get success message from the server through the CardResponse Interface callback
    var successful = MutableLiveData<String>()

    //Get error message from the server through the CardResponse Interface callback
    var error = MutableLiveData<String>()

    //Get card details  from the server
    var cardData= MutableLiveData<CardDetails>()

    //Send card to server
    fun postData(cardNumber : String){
        repository.getCardDetails(cardNumber,this)
    }


    override fun loadingFailed(msg: String) {
        error.postValue(msg)
    }

    override fun loadingSuccessful(msg: String) {
        successful.postValue(msg)
    }

    override fun card(data: CardDetails?) {
        cardData.postValue(data)
    }
}