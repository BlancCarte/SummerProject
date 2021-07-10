package com.example.summerproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.example.summerproject.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private var firebaseAuth: FirebaseAuth? = null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide();

        binding.goToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.password.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {
                binding.btnLogin.isEnabled = binding.password.length() >= 7
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.btnLogin.isEnabled = false
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.btnLogin.setOnClickListener {
            login(binding.email.text.toString(), binding.password.text.toString())
        }
    }

    private fun login(email: String, password: String){
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Email과 Password를 입력하세요", Toast.LENGTH_SHORT).show()
        }
        if(email.isNotEmpty() && password.isNotEmpty()){
            firebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(this){ task->
                if(task.isSuccessful){
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, logintest::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
