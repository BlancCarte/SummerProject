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
import com.example.summerproject.databinding.FragmentModifyPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


private var firebaseAuth: FirebaseAuth? = null
private var firebaseFirestore: FirebaseFirestore? = null


class ModifyPasswordFragment : Fragment() {

    private var mainActivity: HomeActivity? = null
    private var mBinding: FragmentModifyPasswordBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as HomeActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentModifyPasswordBinding.inflate(inflater, container, false)
        mBinding = binding
        return mBinding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
    }

    override fun onResume() {

        //비밀번호 -> 비밀번호 확인 검사
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

        data class UserDTO(
            var password: String? = null
        )

        mBinding!!.btnModify.setOnClickListener {
            var userDTO = UserDTO()
            userDTO.password = mBinding!!.modifyPassword.text.toString()
            var passwordconfrim = mBinding!!.modifyPasswordConfirm.text.toString()
            var currentemail = firebaseAuth!!.currentUser?.email.toString()

            var ref = Firebase.firestore!!.collection("userinfo").document(currentemail)

            firebaseFirestore!!.collection("userinfo").document(currentemail).get()
                .addOnSuccessListener { documentSnapshot ->
                    val password = documentSnapshot.get("password").toString()

                    var map = mutableMapOf<String, Any>()
                    map["password"] = userDTO.password!!

                    if (userDTO.password!!.isNotEmpty() && passwordconfrim!!.isNotEmpty() && userDTO.password != password) {
                        ref.update(map).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                firebaseAuth!!.currentUser!!.updatePassword(userDTO.password!!)
                                Toast.makeText(context, "업데이트", Toast.LENGTH_SHORT).show()
                                firebaseAuth!!.signOut()
                                val intent = Intent(activity, MainActivity::class.java)
                                startActivity(intent)
                                mainActivity?.replaceFragment(MyPageFragment())
                            } else {
                                Toast.makeText(context, "업데이트 실패", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else if(userDTO.password == password){
                        Toast.makeText(context, "이전 비밀번호와 일치합니다", Toast.LENGTH_SHORT).show()
                    } else if(userDTO.password!!.isEmpty() || passwordconfrim!!.isEmpty()){
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