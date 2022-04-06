package com.example.codegram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.codegram.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

private val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null){
            goPostsActivity()
        }

        binding.btnLogin.setOnClickListener {
            binding.btnLogin.isEnabled = false //disable once clicked
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if(email.isBlank() || password.isBlank()){
                Toast.makeText(this, "Email/Password cannot be Empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //FireBase
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                binding.btnLogin.isEnabled = false
                if(task.isSuccessful){
                    Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
                    goPostsActivity()
                }else{
                    binding.btnLogin.isEnabled = true
                    Log.i(TAG, "Sign In Failed",task.exception)
                    Toast.makeText(this, "Sign In failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goPostsActivity() {
        Log.i(TAG,"Going To PostsActivity")
        val intent = Intent(this, PostsActivity::class.java)
        startActivity(intent)
        finish() //end this activity after starting PostsActivity
    }
}