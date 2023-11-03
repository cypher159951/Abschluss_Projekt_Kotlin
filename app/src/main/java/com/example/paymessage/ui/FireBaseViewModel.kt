package com.example.paymessage.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.paymessage.data.datamodels.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

// Eine ViewModel-Klasse, die die Interaktion mit Firebase-Authentifizierung und Firestore-Datenbank handhabt.
class FireBaseViewModel : ViewModel() {

    // Initialisierung von Firebase-Authentifizierung und Firestore-Instanzen.
    val firebaseAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    // Eine MutableLiveData-Instanz, die den aktuellen angemeldeten Firebase-Benutzer speichert.
    private val _user: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val user: LiveData<FirebaseUser?>
        get() = _user

    // Eine späte Initialisierung für die Profildaten-Referenz.
    lateinit var profileRef: DocumentReference

    // Initialisierungsfunktion, die die Umgebung des Benutzers einrichtet.
    init {
        setupUserEnv()
    }

    // Funktion, die aufgerufen wird, um die Umgebung des Benutzers einzurichten.
    fun setupUserEnv() {
        _user.value = firebaseAuth.currentUser

        firebaseAuth.currentUser?.let {
            // Lege Profildaten an, wenn ein Benutzer angemeldet ist.
            profileRef = firestore.collection("Profile").document(firebaseAuth.currentUser!!.uid)
        }
    }


    // Funktion zum Registrieren eines neuen Benutzers.
    fun signUp(email: String, password: String, extra: String = "") {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

            // Überprüfe nach Abschluss des Tasks, ob der Benutzer erfolgreich registriert wurde.
            setupUserEnv()
            val profile = Profile("User", extra)
            profileRef.set(profile)
        }
    }

    // Funktion zum Abmelden des Benutzers.
    fun signOut() {
        firebaseAuth.signOut()
        _user.value = firebaseAuth.currentUser // = null
    }

    // Funktion zum Anmelden des Benutzers.
    fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

            // Überprüfe nach Abschluss des Tasks, ob der Benutzer erfolgreich angemeldet wurde.
            setupUserEnv()
        }
    }
}