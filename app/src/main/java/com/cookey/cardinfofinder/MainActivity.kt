package com.cookey.cardinfofinder

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cards.pay.paycardsrecognizer.sdk.Card
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent
import com.cookey.cardinfofinder.utils.setCardNumber
import com.cookey.cardinfofinder.viewmodels.CardViewModel
import com.cookey.cardinfofinder.views.CardDetailActivity
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {


    private var viewModel: CardViewModel? = null
    val REQUEST_CODE_SCAN_CARD = 1

    lateinit var progressBar: ProgressBar
    lateinit var cardNumberInput: TextInputEditText
    lateinit var tvCardNumber: TextView
    lateinit var scanBtn: Button
    lateinit var frameLayout: FrameLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress_bar)
        cardNumberInput = findViewById(R.id.edt_card_number)
        tvCardNumber = findViewById(R.id.tv_card_number)
        scanBtn = findViewById(R.id.scan_btn)
        frameLayout = findViewById(R.id.frame_layout)

    }


    fun activityInteractions(){

        cardNumberListner()

        //Instantiate view model
        viewModel = ViewModelProvider(this).get(CardViewModel::class.java)

        viewModel?.error?.observe(this, Observer {
            progressDialog(false)
        })

        viewModel?.successful?.observe(this, Observer {
            progressDialog(false)
        })

        //To observe result from server and send through intent to display page
        viewModel?.cardData?.observe(this, Observer {
            if (it != null) {
                val i = Intent(
                    this@MainActivity,
                    CardDetailActivity::class.java
                )
                i.putExtra("data", it)
                startActivity(i)
            }
            progressDialog(false)
        })
    }


    private fun cardNumberListner() {
        cardNumberInput.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) { // Remove all spacing char


                //Logic to space card number
                setCardNumber(s)
                if (s.isNotEmpty()) {
                    tvCardNumber.text = s.toString()
                } else {
                    tvCardNumber.text = getString(R.string.card_number_sample)
                }

                //Get card details from server when edit text completed
                postCardDetailsToServer(s)
            }
        })
    }


    //To call the ocr scan
    private fun scanCard() {
        val intent: Intent = ScanCardIntent.Builder(this).build()
        startActivityForResult(intent, REQUEST_CODE_SCAN_CARD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SCAN_CARD) {
            if (resultCode == Activity.RESULT_OK) {
                val card: Card? = data?.getParcelableExtra(ScanCardIntent.RESULT_PAYCARDS_CARD)

                setCard(card)
            } else if (resultCode == Activity.RESULT_CANCELED) {
                @ScanCardIntent.CancelReason val reason: Int = data?.getIntExtra(
                    ScanCardIntent.RESULT_CANCEL_REASON,
                    ScanCardIntent.BACK_PRESSED
                )
                    ?: ScanCardIntent.BACK_PRESSED

            } else if (resultCode == ScanCardIntent.RESULT_CODE_ERROR) {
                //Log.i(cards.pay.sample.demo.CardDetailsActivity.TAG, "Scan failed")
            }
        }
    }

    //Result from ocr and send to view model to post to the server
    private fun setCard(card: Card?) {
        if (card != null) {
            //show user progress bar before posting to the server
            progressDialog(true)
            viewModel?.postData(card.cardNumber.toString())
        }
    }

    private fun postCardDetailsToServer(s: Editable) {
        if (s.length == 7 || s.length == 9) {
            val k: String = tvCardNumber.text.toString().replace(" ", "")

            //Show user progress bar before posting to the server
            progressDialog(true)
            viewModel?.postData(k)
        }
    }

    fun progressDialog(bol: Boolean) {
        if (bol) {
            progressBar.visibility = View.VISIBLE
            scanBtn.isEnabled = false
            cardNumberInput.isEnabled = false

        } else {
            progressBar.visibility = View.GONE
            scanBtn.isEnabled = true
            cardNumberInput.isEnabled = true
            cardNumberInput.text?.clear()
        }
    }



}