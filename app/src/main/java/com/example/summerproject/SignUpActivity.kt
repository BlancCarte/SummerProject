package com.example.summerproject

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.summerproject.databinding.ActivityMainBinding
import com.example.summerproject.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

private var firebaseAuth: FirebaseAuth? = null
private var firebaseFirestore: FirebaseFirestore? = null

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        val binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide();

        //이메일 유효성검사
        binding.email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var regex =
                    Regex("[0-9a-zA-Z]([-.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}")
                var email = binding.email.text.toString()
                if (email.matches(regex)) {
                    binding.emailTest.setTextColor(Color.parseColor("#369F36"))
                    binding.emailTest.setText("이메일이 입력되었습니다.")
                    binding.btnRegister.isEnabled = true

                }
                else{
                    binding.emailTest.setTextColor(Color.parseColor("#ff0000"))
                    binding.emailTest.setText("이메일을 형식에 맞춰 입력해주세요")
                    binding.btnRegister.isEnabled = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        //닉네임 유효성검사
        binding.nickname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var regex = Regex("[가-힣a-zA-Z0-9]{2,10}")
                var nickname = binding.nickname.text.toString()
                if(nickname.matches(regex)){
                    binding.nicknameTest.setTextColor(Color.parseColor("#369F36"))
                    binding.nicknameTest.setText("별명이 입력되었습니다.")
                    binding.btnRegister.isEnabled = true
                }
                else {
                    binding.nicknameTest.setTextColor(Color.parseColor("#ff0000"))
                    binding.nicknameTest.setText("별명을 형식에 맞춰 입력해주세요.")
                    binding.btnRegister.isEnabled = false

                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        //비밀번호 -> 비밀번호 확인 검사
        binding.password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var regex = Regex("(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@!%*#?&]).{8,15}")
                var pw = binding.password.text.toString()
                if (pw.matches(regex)) {
                    binding.passwordCheckText.setTextColor(Color.parseColor("#369F36"))
                    binding.passwordCheckText.setText("비밀번호가 입력되었습니다.")
                    binding.btnRegister.isEnabled = true
                    if (binding.passwordConfirm.text.toString() == (binding.password.text.toString())) {
                        binding.passwordConfirmCheckText.setTextColor(Color.parseColor("#369F36"))
                        binding.passwordConfirmCheckText.setText("비밀번호가 일치합니다.")
                        binding.btnRegister.isEnabled = true
                    }
                    else if (binding.passwordConfirm.text.toString() != (binding.password.text.toString())){
                        binding.passwordConfirmCheckText.setTextColor(Color.parseColor("#ff0000"))
                        binding.passwordConfirmCheckText.setText("비밀번호가 일치하지 않습니다.")
                        binding.btnRegister.isEnabled = false
                    }
                }
                else {
                    binding.passwordCheckText.setTextColor(Color.parseColor("#ff0000"))
                    binding.passwordCheckText.setText("비밀번호를 형식에 맞춰 입력해주세요.")
                    binding.btnRegister.isEnabled = false
                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        //비밀번호 확인 -> 비밀번호 검사
        //TextWatcher : 원하는 규칙에 대해서 실시간으로 검증하기 위해
        binding.passwordConfirm.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var pw = binding.password.text.toString()
                var pwconfirm = binding.passwordConfirm.text.toString()
                if (binding.passwordConfirm.text.toString() == (binding.password.text.toString())) {
                    binding.passwordConfirmCheckText.setTextColor(Color.parseColor("#369F36"))
                    binding.passwordConfirmCheckText.setText("비밀번호가 일치합니다.")
                    binding.btnRegister.isEnabled = true
                }
                else if (binding.passwordConfirm.text.toString() != (binding.password.text.toString())) {
                    binding.passwordConfirmCheckText.setTextColor(Color.parseColor("#ff0000"))
                    binding.passwordConfirmCheckText.setText("비밀번호가 일치하지 않습니다.")
                    binding.btnRegister.isEnabled = false

                } else {
                    binding.passwordConfirmCheckText.setTextColor(Color.parseColor("#ff0000"))
                    binding.passwordConfirmCheckText.setText("비밀번호가 일치하지 않습니다.")
                    binding.btnRegister.isEnabled = false

                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){}

        })

        //전화번호
        binding.phoneNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var phnum = binding.phoneNumber.text.toString()
                var regex = Regex("01[016789][0-9]{3,4}[0-9]{4}$")
                if (phnum.matches(regex)) {
                    binding.phoneNumberTest.setTextColor(Color.parseColor("#369F36"))
                    binding.phoneNumberTest.setText("입력되었습니다.")
                    binding.btnRegister.isEnabled = true
                }
                else {
                    binding.phoneNumberTest.setTextColor(Color.parseColor("#ff0000"))
                    binding.phoneNumberTest.setText("핸드폰 형식이 아닙니다.")
                    binding.btnRegister.isEnabled = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        // 게터세터
        data class UserDTO(
            var uid: String? = null,
            var email: String? = null,
            var password: String? = null,
            var nickname: String? = null,
            var phoneNumber: String? = null
        )

        binding.btnRegister.setOnClickListener {

            var userDTO = UserDTO()
            userDTO.uid = firebaseAuth?.currentUser?.uid
            userDTO.email = binding.email.text.toString()
            userDTO.nickname = binding.nickname.text.toString()
            userDTO.password = binding.password.text.toString()
            var passwordconfrim = binding.passwordConfirm.text.toString()
            userDTO.phoneNumber = binding.phoneNumber.text.toString()

            if(userDTO.email!!.isNotEmpty() && userDTO.nickname!!.isNotEmpty() &&userDTO.password!!.isNotEmpty()&& passwordconfrim!!.isNotEmpty()&& userDTO.phoneNumber!!.isNotEmpty() ){
                firebaseAuth!!.createUserWithEmailAndPassword(userDTO.email!!, userDTO.password!!)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            firebaseFirestore?.collection("userinfo")
                                ?.document(userDTO.email!!)?.set(userDTO)
                            val user = firebaseAuth!!.currentUser
                            user!!.sendEmailVerification()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(
                                            this,
                                            "회원가입 완료, 인증 이메일이 발송되었습니다.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                    }
                                }
                        } else {
                            Toast.makeText(this, "회원가입 실패, 이미 존재하는 이메일입니다.", Toast.LENGTH_SHORT).show()
                        }

                    }
            }else{
                Toast.makeText(this, "빈칸을 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }

    }

}

// 파이어베이스 잘 들어가는지 확인 (O)
// 입력한 값이 파이어베이스에 들어가야 하니까 에딧텍스트에 입력 가능하게 해야함 (O)
// 에딧 텍스트를 찾아서 온클릭 리스너를 적용 시켜야 한다 (O)
// 닉네임이랑 전화번호는 쿼리문 사용해서 파이어스토어 디비에 넣기(O)
// 회원가입 성공시 로그인 페이지로 돌아가서 다시 로그인 시키기(O)
// 회원가입시 파이어스토어에 닉네임과 전화번호 보내기(O)
// 로그인(O)

// 이메일 비밀번호 전화번호 닉네임 유효성 검사(O)
// 이메일 인증(O)
// 추가 : 게시판