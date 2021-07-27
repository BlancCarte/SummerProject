package com.example.summerproject.ui.mypage

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.summerproject.HomeActivity
import com.example.summerproject.MainActivity
import com.example.summerproject.databinding.FragmentModifyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private var firebaseAuth: FirebaseAuth? = null
private var firebaseFirestore: FirebaseFirestore? = null

class ModifyFragment : Fragment() {
	private var mainActivity: HomeActivity? = null
	private var mBinding: FragmentModifyBinding? = null

	override fun onAttach(context: Context) {
		super.onAttach(context)
		mainActivity = context as HomeActivity

	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val binding = FragmentModifyBinding.inflate(inflater, container, false)
		mBinding = binding
		return mBinding?.root
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		firebaseAuth = FirebaseAuth.getInstance()
		firebaseFirestore = FirebaseFirestore.getInstance()
	}

	override fun onResume() {

		val currentemail = firebaseAuth!!.currentUser?.email.toString()

		firebaseFirestore!!.collection("userinfo").document(currentemail).get()
			.addOnSuccessListener { documentSnapshot ->
				val email = documentSnapshot.get("email").toString()
				val nickname = documentSnapshot.get("nickname").toString()
				val phoneNumber = documentSnapshot.get("phoneNumber").toString()

				mBinding!!.modifyEmail.setText(email)
				mBinding!!.modifyNickname.setText(nickname)
				mBinding!!.modifyPhoneNumber.setText(phoneNumber)
			}

/*
      //이메일 유효성검사
      mBinding!!.modifyNickname.addTextChangedListener(object : TextWatcher {
         override fun afterTextChanged(p0: Editable?) {
            var regex =
               Regex("[0-9a-zA-Z]([-.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}")
            var email = mBinding!!.modifyNickname.text.toString()
            if (email.matches(regex)) {
               mBinding!!.modifyEmailTest.setTextColor(Color.parseColor("#369F36"))
               mBinding!!.modifyEmailTest.setText("이메일이 입력되었습니다.")

            }
            else{
               mBinding!!.modifyEmailTest.setTextColor(Color.parseColor("#ff0000"))
               mBinding!!.modifyEmailTest.setText("이메일을 형식에 맞춰 입력해주세요")

            }
         }

         override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

         override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
      })

 */

		//닉네임 유효성검사
		mBinding!!.modifyNickname.addTextChangedListener(object : TextWatcher {
			override fun afterTextChanged(p0: Editable?) {
				var regex = Regex("[가-힣a-zA-Z0-9]{2,10}")
				var nickname = mBinding!!.modifyNickname.text.toString()
				if (nickname.matches(regex)) {
					mBinding!!.modifyNicknameTest.setTextColor(Color.parseColor("#369F36"))
					mBinding!!.modifyNicknameTest.setText("별명이 입력되었습니다.")
					mBinding!!.btnModify.isEnabled = true
				} else {
					mBinding!!.modifyNicknameTest.setTextColor(Color.parseColor("#ff0000"))
					mBinding!!.modifyNicknameTest.setText("별명을 형식에 맞춰 입력해주세요.")
					mBinding!!.btnModify.isEnabled = false

				}
			}

			override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

			override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
		})

		//비밀번호 -> 비밀번호 확인 검사
		/*
		mBinding!!.modifyPassword.addTextChangedListener(object : TextWatcher {
			override fun afterTextChanged(p0: Editable?) {
				var regex = Regex("(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@!%*#?&]).{8,15}")
				var pw = mBinding!!.modifyPassword.text.toString()
				if (pw.matches(regex)) {
					mBinding!!.modifyPasswordCheckText.setTextColor(Color.parseColor("#369F36"))
					mBinding!!.modifyPasswordCheckText.setText("비밀번호가 입력되었습니다.")
					if (mBinding!!.modifyPasswordConfirm.text.toString() == (mBinding!!.modifyPassword.text.toString())) {
						mBinding!!.modifyPasswordConfirmCheckText.setTextColor(Color.parseColor("#369F36"))
						mBinding!!.modifyPasswordConfirmCheckText.setText("비밀번호가 일치합니다.")
						mBinding!!.btnModify.isEnabled = true
					} else if (mBinding!!.modifyPasswordConfirm.text.toString() != (mBinding!!.modifyPassword.text.toString())){
						mBinding!!.modifyPasswordConfirmCheckText.setTextColor(Color.parseColor("#ff0000"))
						mBinding!!.modifyPasswordConfirmCheckText.setText("비밀번호가 일치하지 않습니다.")
						mBinding!!.btnModify.isEnabled = false
					}
				} else {
					mBinding!!.modifyPasswordCheckText.setTextColor(Color.parseColor("#ff0000"))
					mBinding!!.modifyPasswordCheckText.setText("비밀번호를 형식에 맞춰 입력해주세요.")
					mBinding!!.modifyPasswordConfirmCheckText.setTextColor(Color.parseColor("#ff0000"))
					mBinding!!.modifyPasswordConfirmCheckText.setText("비밀번호가 일치하지 않습니다.")
					mBinding!!.btnModify.isEnabled = false
				}

			}

			override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

			override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
		})

		//비밀번호 확인 -> 비밀번호 검사
		//TextWatcher : 원하는 규칙에 대해서 실시간으로 검증하기 위해
		mBinding!!.modifyPasswordConfirm.addTextChangedListener(object : TextWatcher {
			override fun afterTextChanged(p0: Editable?) {
				var pw = mBinding!!.modifyPassword.text.toString()
				var pwconfirm = mBinding!!.modifyPasswordConfirm.text.toString()
				if (mBinding!!.modifyPasswordConfirm.text.toString() == (mBinding!!.modifyPassword.text.toString())) {
					mBinding!!.modifyPasswordConfirmCheckText.setTextColor(Color.parseColor("#369F36"))
					mBinding!!.modifyPasswordConfirmCheckText.setText("비밀번호가 일치합니다.")
					mBinding!!.btnModify.isEnabled = true
				} else if (!(mBinding!!.modifyPasswordConfirm.text.toString() == (mBinding!!.modifyPassword.text.toString()))) {
					mBinding!!.modifyPasswordConfirmCheckText.setTextColor(Color.parseColor("#ff0000"))
					mBinding!!.modifyPasswordConfirmCheckText.setText("비밀번호가 일치하지 않습니다.")
					mBinding!!.btnModify.isEnabled = false
				} else {
					mBinding!!.modifyPasswordConfirmCheckText.setTextColor(Color.parseColor("#ff0000"))
					mBinding!!.modifyPasswordConfirmCheckText.setText("비밀번호가 일치하지 않습니다.")
					mBinding!!.btnModify.isEnabled = false
				}

			}

			override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

			override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

		})
*/
		//전화번호
		mBinding!!.modifyPhoneNumber.addTextChangedListener(object : TextWatcher {
			override fun afterTextChanged(p0: Editable?) {
				var phnum = mBinding!!.modifyPhoneNumber.text.toString()
				var regex = Regex("01[016789][0-9]{3,4}[0-9]{4}$")
				if (phnum.matches(regex)) {
					mBinding!!.modifyPhoneNumberTest.setTextColor(Color.parseColor("#369F36"))
					mBinding!!.modifyPhoneNumberTest.setText("입력되었습니다.")
					mBinding!!.btnModify.isEnabled = true
				} else {
					mBinding!!.modifyPhoneNumberTest.setTextColor(Color.parseColor("#ff0000"))
					mBinding!!.modifyPhoneNumberTest.setText("핸드폰 형식이 아닙니다.")
					mBinding!!.btnModify.isEnabled = false
				}
			}

			override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

			override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
		})

		data class UserDTO(
			var password: String? = null,
			var nickname: String? = null,
			var phoneNumber: String? = null
		)

		mBinding!!.btnModify.setOnClickListener {
			var userDTO = UserDTO()
			userDTO.nickname = mBinding!!.modifyNickname.text.toString()
			//userDTO.password = mBinding!!.modifyPassword.text.toString()
			userDTO.phoneNumber = mBinding!!.modifyPhoneNumber.text.toString()
			//var passwordconfrim = mBinding!!.modifyPasswordConfirm.text.toString()
			var currentemail = firebaseAuth!!.currentUser?.email.toString()

			var ref = Firebase.firestore!!.collection("userinfo").document(currentemail)

			ref.get()
				.addOnSuccessListener { documentSnapshot ->
					//val password = documentSnapshot.get("password").toString()

					var map = mutableMapOf<String, Any>()

					map["phoneNumber"] = userDTO.phoneNumber!!
					//map["password"] = userDTO.password!!
					map["nickname"] = userDTO.nickname!!

					if (userDTO.nickname!!.isNotEmpty() && userDTO.phoneNumber!!.isNotEmpty()) {
						ref.update(map).addOnCompleteListener { task ->
							if (task.isSuccessful) {
								Toast.makeText(context, "업데이트", Toast.LENGTH_SHORT).show()
								firebaseAuth!!.signOut();
								val intent = Intent(activity, MainActivity::class.java)
								startActivity(intent)
							} else {
								Toast.makeText(context, "업데이트 실패", Toast.LENGTH_SHORT).show()
							}
						}
					} else if(userDTO.nickname!!.isEmpty() ||  userDTO.phoneNumber!!.isEmpty()){
						Toast.makeText(context, "빈칸을 확인해주세요", Toast.LENGTH_SHORT).show()
					} else{
						Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
					}
				}
		}
		super.onResume()
	}

	override fun onDestroyView() {
		mBinding = null
		super.onDestroyView()
	}
}
//1. 버튼 리스너에 공란시 입력불가출력 (O)
//2. 회원정보수정 동일패스워드인 경우 유효성 검사 추가 (O)
//2. 마이페이지 회원탈퇴 기능 추가 (O)