package com.example.paymessage.ui

import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.paymessage.data.datamodels.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FireBaseViewModel : ViewModel() {

    val firebaseAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    private val _user: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val user: LiveData<FirebaseUser?>
        get() = _user

    lateinit var profileRef : DocumentReference

    init {
        setupUserEnv()
    }

    //Wird aufgerufen wenn der User eingeloggt wurde
    fun setupUserEnv(){
        _user.value = firebaseAuth.currentUser

        //if(user != null)
        firebaseAuth.currentUser?.let {
            //Lege Profildaten an
            profileRef = firestore.collection("Profile").document(firebaseAuth.currentUser!!.uid)
        }
    }

    fun getChatDocumentReference(chatIdentifier: String) : DocumentReference{
        return firestore.collection("chats").document(chatIdentifier)
    }

    fun addMessageToChat(chatIdentifier: String, message : Message) {
        firestore
            .collection("chats")
            .document(chatIdentifier)
            .collection("messages")
            .add(message)
    }




    fun signUp(email: String, password: String, extra: String = "") {

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            //Wenn Task fertig ist dann überprüfe z.B. ob der User eingeloggt wurde
            //oder ob es Fehler gab o.Ä.
            setupUserEnv()
            val profile = Profile("User", extra)
            profileRef.set(profile)
        }


    }


    fun signOut(){
        firebaseAuth.signOut()
        _user.value = firebaseAuth.currentUser // = null
    }

    fun signIn(email: String, password: String) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            //Wenn Task fertig ist dann überprüfe z.B. ob der User eingeloggt wurde
            //oder ob es Fehler gab o.Ä.

            setupUserEnv()
        }
    }
}