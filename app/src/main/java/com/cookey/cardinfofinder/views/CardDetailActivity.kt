package com.cookey.cardinfofinder.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.cookey.cardinfofinder.R
import com.cookey.cardinfofinder.model.CardDetails

class CardDetailActivity : AppCompatActivity() {

    var cardDetails : CardDetails? = null


    lateinit var tvCardBrand: TextView
    lateinit var tvCardType: TextView


    lateinit var bankName: TextView
    lateinit var bankUrl: TextView
    lateinit var bankPhone: TextView


    lateinit var countryName: TextView
    lateinit var countryAlpha: TextView
    lateinit var countryCurrency: TextView
    lateinit var countryNumeric: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)

        supportActionBar?.title = "Card Details"

        tvCardBrand = findViewById(R.id.tv_card_brand)
        tvCardType = findViewById(R.id.tv_card_type)

        bankName = findViewById(R.id.bank_name)
        bankUrl = findViewById(R.id.bank_url)
        bankPhone = findViewById(R.id.bank_phone)


        countryName = findViewById(R.id.country_name)
        countryAlpha = findViewById(R.id.country_alpha)
        countryCurrency = findViewById(R.id.country_currency)
        countryNumeric = findViewById(R.id.country_numeric)

        if (intent != null) {
            cardDetails = intent.getSerializableExtra("data") as CardDetails
            if (cardDetails == null) {
                finish()

                //if the data is null, it should close the page, nothing to display
            }
            displayData(cardDetails)
        }

    }


    private fun displayData(cardDetails : CardDetails?){
        tvCardBrand.text = cardDetails?.brand ?: "Not available"
        tvCardType.text = cardDetails?.type ?: "Not available"

        bankName.text = cardDetails?.bank?.name ?: "Not available"
        bankUrl.text = cardDetails?.bank?.url ?: "Not available"
        bankPhone.text = cardDetails?.bank?.phone ?: "Not available"

        countryName.text = cardDetails?.country?.name ?: "Not available"
        countryAlpha.text = cardDetails?.country?.alpha2  ?: "Not available"
        countryCurrency.text = cardDetails?.country?.currency  ?: "Not available"
        countryNumeric.text = cardDetails?.country?.numeric  ?: "Not available"
    }
}