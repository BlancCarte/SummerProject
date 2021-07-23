package com.example.summerproject.ui.mypage

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Keep
import androidx.fragment.app.Fragment
import com.example.summerproject.HomeActivity
import com.example.summerproject.databinding.FragmentModifyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


private var firebaseAuth: FirebaseAuth? = null
private var firebaseFirestore: FirebaseFirestore? = null

class ModifyFragment : Fragment() {
    private var mainActivity: HomeActivity? =null
    private var mBinding : FragmentModifyBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity=context as HomeActivity

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentModifyBinding.inflate(inflater,container,false)
        mBinding = binding
        return mBinding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        //var currentemail = firebaseAuth!!.currentUser?.email.toString()
        //Log.d("테스트", currentemail)
    }



    override fun onResume() {
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

 */     var currentemail = firebaseAuth!!.currentUser?.email.toString()
        mBinding!!.modifyEmail!!.setText(currentemail)

        //데이터 가져오기
        firebaseFirestore!!.collection("userinfo").document(currentemail).get().addOnSuccessListener { documentSnapshot->
            val email = documentSnapshot.get("email").toString()
            val nickname = documentSnapshot.get("nickname").toString()
            val password = documentSnapshot.get("password").toString()
            val phoneNumber = documentSnapshot.get("phoneNumber").toString()

            mBinding!!.modifyNickname.setText(nickname)
            mBinding!!.modifyPhoneNumber.setText(phoneNumber)
        }



        //닉네임 유효성검사
        mBinding!!.modifyNickname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var regex = Regex("[가-힣a-zA-Z0-9]{2,10}")
                var nickname = mBinding!!.modifyNickname.text.toString()
                if(nickname.matches(regex)){
                    mBinding!!.modifyNicknameTest.setTextColor(Color.parseColor("#369F36"))
                    mBinding!!.modifyNicknameTest.setText("별명이 입력되었습니다.")
                }
                else {
                    mBinding!!.modifyNicknameTest.setTextColor(Color.parseColor("#ff0000"))
                    mBinding!!.modifyNicknameTest.setText("별명을 형식에 맞춰 입력해주세요.")

                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        //비밀번호 -> 비밀번호 확인 검사
        mBinding!!.modifyPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var regex = Regex("(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@!%*#?&]).{8,15}")
                var pw =mBinding!!.modifyPassword.text.toString()
                if (pw.matches(regex)) {
                    mBinding!!.modifyPasswordCheckText.setTextColor(Color.parseColor("#369F36"))
                    mBinding!!.modifyPasswordCheckText.setText("비밀번호가 입력되었습니다.")
                    if (mBinding!!.modifyPasswordConfirm.text.toString() == (mBinding!!.modifyPassword.text.toString())) {
                        mBinding!!.modifyPasswordConfirmCheckText.setTextColor(Color.parseColor("#369F36"))
                        mBinding!!.modifyPasswordConfirmCheckText.setText("비밀번호가 일치합니다.")

                    }
                }
                else {
                    mBinding!!.modifyPasswordCheckText.setTextColor(Color.parseColor("#ff0000"))
                    mBinding!!.modifyPasswordCheckText.setText("비밀번호를 형식에 맞춰 입력해주세요.")
                    mBinding!!.modifyPasswordConfirmCheckText.setTextColor(Color.parseColor("#ff0000"))
                    mBinding!!.modifyPasswordConfirmCheckText.setText("비밀번호가 일치하지 않습니다.")
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
                }
                else if (!(mBinding!!.modifyPasswordConfirm.text.toString() == (mBinding!!.modifyPassword.text.toString()))) {
                    mBinding!!.modifyPasswordConfirmCheckText.setTextColor(Color.parseColor("#ff0000"))
                    mBinding!!.modifyPasswordConfirmCheckText.setText("비밀번호가 일치하지 않습니다.")

                } else {
                    mBinding!!.modifyPasswordConfirmCheckText.setTextColor(Color.parseColor("#ff0000"))
                    mBinding!!.modifyPasswordConfirmCheckText.setText("비밀번호가 일치하지 않습니다.")

                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){}

        })

        //전화번호
        mBinding!!.modifyPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var phnum = mBinding!!.modifyPhoneNumber.text.toString()
                var regex = Regex("01[016789][0-9]{3,4}[0-9]{4}$")
                if (phnum.matches(regex)) {
                    mBinding!!.modifyPhoneNumberTest.setTextColor(Color.parseColor("#369F36"))
                    mBinding!!.modifyPhoneNumberTest.setText("입력되었습니다.")
                }
                else {
                    mBinding!!.modifyPhoneNumberTest.setTextColor(Color.parseColor("#ff0000"))
                    mBinding!!.modifyPhoneNumberTest.setText("핸드폰 형식이 아닙니다.")
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        data class UserDTO(
            var password1: String? = null,
            var nickname1: String? = null,
            var phoneNumber1: String? = null
        )

        mBinding!!.btnModify.setOnClickListener {
            var userDTO = UserDTO()
            userDTO.nickname1 = mBinding!!.modifyNickname.text.toString()
            userDTO.password1 = mBinding!!.modifyPassword.text.toString()
            userDTO.phoneNumber1 = mBinding!!.modifyPhoneNumber.text.toString()
            var currentemail = firebaseAuth!!.currentUser?.email.toString()

            var ref = Firebase.firestore!!.collection("userinfo").document(currentemail)

            var map = mutableMapOf<String,Any>()

            map["phoneNumber"] = userDTO.phoneNumber1!!
            map["password"] = userDTO.password1!!
            map["nickname"] = userDTO.nickname1!!

            ref.update(map).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseAuth!!.currentUser!!.updatePassword(userDTO.password1!!)
                    Toast.makeText(context, "업데이트", Toast.LENGTH_SHORT).show()
                    mainActivity?.replaceFragment(MyPageFragment())
                }
                else{
                    Toast.makeText(context, "업데이트 실패", Toast.LENGTH_SHORT).show()
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