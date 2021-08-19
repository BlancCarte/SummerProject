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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
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