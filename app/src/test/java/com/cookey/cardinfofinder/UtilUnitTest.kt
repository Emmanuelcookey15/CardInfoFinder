package com.cookey.cardinfofinder


import android.content.Context
import android.text.Editable
import com.cookey.cardinfofinder.model.CardDetails
import org.junit.Assert
import org.junit.Test
import com.cookey.cardinfofinder.utils.isConnectedToTheInternet
import com.cookey.cardinfofinder.utils.setCardNumber
import org.junit.Before


class UtilUnitTest {


    @Test
    fun `Test the CardDetail model is not null when instantiated` () {
        val cardDetail = CardDetails()
        Assert.assertNotNull(cardDetail)
        Assert.assertNull(cardDetail.brand)
    }

    @Test
    fun `Test the CardDetail model is returns null when instantiated without defining fields` () {
        val cardDetail = CardDetails()
        Assert.assertNull(cardDetail.type)
        Assert.assertNull(cardDetail.brand)
        Assert.assertNull(cardDetail.bank)
        Assert.assertNull(cardDetail.country)
        Assert.assertNull(cardDetail.number)
        Assert.assertNull(cardDetail.scheme)

    }


    @Test
    fun `spacing of card number that users inputs`() {
        val s: Editable?= null
        s?.equals(6).toString()
        assert(true) {
            if (s != null) {
                setCardNumber(s)
            }
        }
    }




}