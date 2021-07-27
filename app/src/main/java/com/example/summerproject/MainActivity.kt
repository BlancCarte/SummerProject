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
var backKeyPressedTime : Long = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(firebaseAuth!!.currentUser!=null && firebaseAuth!!.currentUser!!.isEmailVerified){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.goToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
/*
        binding.password.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {
                binding.btnLogin.isEnabled =  binding.password.length() >= 7
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnLogin.isEnabled = false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
*/
        binding.btnLogin.setOnClickListener {
            login(binding.email.text.toString(), binding.password.text.toString())
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        if(System.currentTimeMillis() > backKeyPressedTime+2500){
            backKeyPressedTime = System.currentTimeMillis()

            return
        }

        if(System.currentTimeMillis() <= backKeyPressedTime+2500){
            finishAffinity()
        }
    }

    private fun login(email: String, password: String){
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "모든 칸을 입력해주세요", Toast.LENGTH_SHORT).show()
        } else if(email.isNotEmpty() && password.isNotEmpty()){
            firebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    var user= firebaseAuth!!.currentUser!!.isEmailVerified
                    if (user) {
                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, "로그인 실패, 이메일 인증을 확인해주세요", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}