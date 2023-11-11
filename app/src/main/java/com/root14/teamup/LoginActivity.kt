package com.root14.teamup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.root14.teamup.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val RC_SIGN_IN = 1001
    var googleSignInClient: GoogleSignInClient? = null
    private var firebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUi()


        binding.btnLoginGoogle.setOnClickListener { // Launch Sign In
            signInToGoogle()
        }
        configureGoogleClient()

    }

    private fun setUpUi() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun configureGoogleClient() {
        // Configure Google Sign In
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) // for the requestIdToken, this is in the values.xml file that
                // is generated from your google-services.json
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // Set the dimensions of the sign-in button.
        val signInButton = findViewById<SignInButton>(R.id.btn_LoginGoogle)
        signInButton.setSize(SignInButton.SIZE_WIDE)
        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()
    }

    fun signInToGoogle() {
        val signInIntent: Intent = googleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                showToastMessage("Google Sign in Succeeded")
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("LoginActivity", "Google sign in failed", e)
                showToastMessage("Google Sign in Failed $e")
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d("LoginActivity", "firebaseAuthWithGoogle:" + account.id)
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user: FirebaseUser? = firebaseAuth?.currentUser
                        Log.d(
                            "LoginActivity",
                            "signInWithCredential:success: currentUser: " + user?.email
                        )
                        showToastMessage("Firebase Authentication Succeeded ")
                        //-> start main activity with user??
                        //launchMainActivity(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("LoginActivity", "signInWithCredential:failure", task.exception)
                        showToastMessage("Firebase Authentication failed:" + task.exception)
                    }
                })
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
    }

}