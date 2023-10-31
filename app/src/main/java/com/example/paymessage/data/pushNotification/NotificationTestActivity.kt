package com.example.paymessage.data.pushNotification

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class NotificationTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Rufen Sie die Testfunktion für die Benachrichtigung auf
        sendTestNotification()

        // Schließen Sie die Aktivität nach dem Test
        finish()
    }

    private fun sendTestNotification() {
        // Erstellen Sie eine Instanz von NotificationHandler mit dem Anwendungscontext.
        val notificationHandler = NotificationHandler(applicationContext)
        Log.d("pushTest", "push gestartet")
        // Rufe die Methode displayNotification auf, um eine Testbenachrichtigung zu senden.
        notificationHandler.displayNotification("Test Title", "Dies ist eine Testbenachrichtigung.")
        Log.d("pushTest2", "push test angekommen")
    }
}