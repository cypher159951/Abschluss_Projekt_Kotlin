package com.example.paymessage.data.autoRefresh

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Window
import android.widget.ProgressBar

class NewsRepositoryCallback(private val context: Context) : RepositoryCallback {


    private var progressDialog: Dialog? = null



    override fun showLoading() {
        if (context is Activity && !(context as Activity).isFinishing) {
            progressDialog = Dialog(context)
            progressDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progressDialog?.setCancelable(false)
            progressDialog?.setContentView(ProgressBar(context))
            progressDialog?.show()
            Log.d("push benachrichtigung", " empfangen")
        }
    }

    override fun hideLoading() {
        progressDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }
}