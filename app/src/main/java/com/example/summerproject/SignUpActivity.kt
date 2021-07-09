package com.example.summerproject

import android.content.Intent
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

private var firebaseAuth: FirebaseAuth? = null

class SignUpActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		firebaseAuth = FirebaseAuth.getInstance()

		val binding = ActivitySignUpBinding.inflate(layoutInflater)
		setContentView(binding.root)
		supportActionBar?.hide();

		//이메일 유효성 검사

		binding.email.addTextChangedListener(object : TextWatcher {
			override fun afterTextChanged(s: Editable?) {
				val emailRegex = "/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i"

				if(binding.email.text.toString().equals(emailRegex)){
					binding.btnRegister.isEnabled = true
				} else {
					binding.btnRegister.isEnabled = false
				}
			}

			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
				val emailRegex = "/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i"
				if(binding.email.text.toString().equals(emailRegex)){
					binding.btnRegister.isEnabled = true
				} else {
					binding.btnRegister.isEnabled = false
				}
			}
		})

		//비밀번호 -> 비밀번호 확인 검사
		binding.passwordConfirm.addTextChangedListener(object : TextWatcher {

			override fun afterTextChanged(p0: Editable?) {
				binding.btnRegister.isEnabled = binding.password.text.toString() == binding.passwordConfirm.text.toString() && binding.password.length() >= 7
				/*if(binding.password.text.toString().equals(binding.passwordConfirm.text.toString()) && binding.password.length() >= 7){
					binding.btnRegister.isEnabled = true
				}else{
					binding.btnRegister.isEnabled = false
				}*/
			}

			override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
				binding.btnRegister.isEnabled = false
			}

			override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

			}
		})

		//비밀번호 확인 -> 비밀번호 검사
		//TextWatcher : 원하는 규칙에 대해서 실시간으로 검증하기 위해
		binding.password.addTextChangedListener(object : TextWatcher {

			override fun afterTextChanged(p0: Editable?) {
				binding.btnRegister.isEnabled = binding.password.text.toString() == binding.passwordConfirm.text.toString() && binding.passwordConfirm.length() >= 7
			}

			override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
				binding.btnRegister.isEnabled = false
			}

			override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
		})

		binding.btnRegister.setOnClickListener {
			var email = binding.email.text.toString()
			var nickname = binding.nickname.text.toString()
			var password = binding.password.text.toString()
			var phoneNumber = binding.phoneNumber.text.toString()

			firebaseAuth!!.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener(this) {
					if (it.isSuccessful) {
						val user = firebaseAuth?.currentUser
						Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()

						val intent = Intent(this, MainActivity::class.java)
						startActivity(intent)

					} else {
						Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
					}

				}
		}
	}
}

// 파이어베이스 잘 들어가는지 확인 (O)
// 입력한 값이 파이어베이스에 들어가야 하니까 에딧텍스트에 입력 가능하게 해야함 (O)
// 에딧 텍스트를 찾아서 온클릭 리스너를 적용 시켜야 한다 (O)
// 이메일 비밀번호 전화번호 닉네임 유효성 검사
// 닉네임이랑 전화번호는 쿼리문 사용해서 파이어스토어 디비에 넣기
// 회원가입 성공시 로그인 페이지로 돌아가서 다시 로그인 시키기
// 이메일 인증
// 전화번호는 뺄지 말지 고민

// 회원가입시 파이어스토어에 닉네임과 전화번호 보내기
// 로그인
// 비밀번호 정규식 검사
// 추가 :