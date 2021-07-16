package com.example.redditmemes

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel

import com.google.android.material.snackbar.Snackbar

class MemeViewModel: ViewModel() {
    init{
        Log.i("MemeViewModel","MemeViewModel Created")
    }

    var urlImage: String? = null
    var title: String? = null
    var upvotes: String? = null



    fun showSnackBar(message: String?, activity: Activity?) {
        if (null != activity && null != message) {
            Snackbar.make(
                activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCleared() {
        super.onCleared()

        Log.i("MemeViewModel","MemeViewModel Destroyed")
    }
}