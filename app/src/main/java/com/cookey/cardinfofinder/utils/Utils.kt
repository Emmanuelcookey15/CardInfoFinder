package com.cookey.cardinfofinder.utils

import android.content.ContentValues
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.os.Build
import android.text.Editable
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import com.cookey.cardinfofinder.R


/** connection manager **/
@RequiresApi(Build.VERSION_CODES.M)
fun Context.isConnectedToTheInternet(): Boolean {
    val cnxManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    val netInfo : Network? = cnxManager?.activeNetwork
    return netInfo != null

}



fun setCardNumber(s: Editable){
    val space = ' '

    var pos = 0
    while (true) {
        if (pos >= s.length) break
        if (space == s[pos] && ((pos + 1) % 5 != 0 || pos + 1 == s.length)) {
            s.delete(pos, pos + 1)
        } else {
            pos++
        }
    }
    // Insert char where needed.
    pos = 4
    while (true) {
        if (pos >= s.length) break
        val c = s[pos]
        // Only if its a digit where there should be a space we insert a space
        if ("0123456789".indexOf(c) >= 0) {
            s.insert(pos, "" + space)
        }
        pos += 5
    }
}