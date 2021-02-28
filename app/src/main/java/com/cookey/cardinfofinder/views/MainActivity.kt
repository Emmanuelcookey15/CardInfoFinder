package com.cookey.cardinfofinder.views

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cards.pay.paycardsrecognizer.sdk.Card
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent
import com.cookey.cardinfofinder.R
import com.cookey.cardinfofinder.utils.isConnectedToTheInternet
import com.cookey.cardinfofinder.utils.setCardNumber
import com.cookey.cardinfofinder.viewmodels.CardViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {


    private var viewModel: CardViewModel? = null
    internal var snackbar: Snackbar? = null
    internal var view: View? = null


    val REQUEST_CODE_SCAN_CARD = 1

    lateinit var progressBar: LinearLayout
    lateinit var cardNumberInput: TextInputEditText
    lateinit var scanBtn: Button
    lateinit var frameLayout: FrameLayout



    @Volatile
    private var isOn = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress_bar)
        cardNumberInput = findViewById(R.id.edt_card_number)
        scanBtn = findViewById(R.id.scan_btn)
        frameLayout = findViewById(R.id.frame_layout)

        view = getView()
        if (view != null) {

            //if internet is off it display a buttom snack
            snackbar =
                    Snackbar.make(view!!, "Check your internet connection.", Snackbar.LENGTH_INDEFINITE)
            val snackBarView = snackbar!!.view
            snackBarView.setBackgroundColor(ContextCompat.getColor(this,
                R.color.red
            ))
            val textView =
                    snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.gravity = View.TEXT_ALIGNMENT_CENTER
            textView.setTextColor(ContextCompat.getColor(this,
                R.color.white
            ))
        }

        activityInteractions()


    }


    fun activityInteractions(){

        cardNumberListner()

        scanBtn.setOnClickListener {
            scanCard()
        }

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
            val k: String = cardNumberInput.text.toString().replace(" ", "")

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

    private fun getView(): View? {
        val vg = findViewById<ViewGroup>(android.R.id.content)
        var rv: View? = null

        if (vg != null)
            rv = vg.getChildAt(0)
        if (rv == null)
            rv = window.decorView.rootView
        return rv
    }


    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastReceiver)
        super.onPause()
        isOn = false
    }

    override fun onResume() {
        super.onResume()
        registerInternetCheckReceiver()
        isOn = true
    }

    private fun registerInternetCheckReceiver() {
        val internetFilter = IntentFilter()
        internetFilter.addAction("android.net.wifi.STATE_CHANGE")
        internetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(broadcastReceiver, internetFilter)
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onReceive(context: Context, intent: Intent) {
            if (isConnectedToTheInternet()) {
                snackbar?.dismiss()
            } else {
                snackbar?.show()
            }
        }
    }



}