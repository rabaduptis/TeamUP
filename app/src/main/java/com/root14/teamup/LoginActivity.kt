package com.root14.teamup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.root14.teamup.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    var googleSignInClient: GoogleSignInClient? = null
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var launcher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdge2Edge()

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Check if user is signed in (non-null) and update UI accordingly.
        if (firebaseAuth.currentUser == null) {
            configureGoogleClient()

            binding.btnLoginGoogle.setOnClickListener { // Launch Sign In
                signInToGoogle()
            }

            launcher = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
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
        } else {
            // User is already signed in -> Launch Main Activity
            nav2MainActivity()
        }
    }

    /**
     * Navigate to MainActivity
     */
    private fun nav2MainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    /**
     * Enable edge-to-edge display.
     */
    private fun enableEdge2Edge() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * Configure Google Sign In
     */
    private fun configureGoogleClient() {
        // Configure Google Sign In
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) // for the requestIdToken, this is in the values.xml file that
                // is generated from your google-services.json
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // Set the dimensions of the sign-in button.
        val signInButton = findViewById<SignInButton>(R.id.btn_LoginGoogle)
        signInButton.setSize(SignInButton.SIZE_WIDE)
    }

    /**
     * Launch Sign In
     */
    private fun signInToGoogle() {
        val signInIntent: Intent = googleSignInClient!!.signInIntent
        launcher.launch(signInIntent)
    }


    // Firebase sign out -> logout from google account
    override fun onDestroy() {
        super.onDestroy()
        Firebase.auth.signOut()
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        firebaseAuth.let { firebaseAuth ->
            firebaseAuth.signInWithCredential(
                GoogleAuthProvider.getCredential(
                    account.idToken, null
                )
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    Log.d("LoginActivity", "Firebase authentication succeeded")
                    // Launch main activity with user
                    nav2MainActivity()
                } else {
                    // Sign in failure
                    Log.w("LoginActivity", "Firebase authentication failed", task.exception)
                }
            }
        }
    }

    /**
     * Show Toast Message
     */
    private fun showToastMessage(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
    }

}