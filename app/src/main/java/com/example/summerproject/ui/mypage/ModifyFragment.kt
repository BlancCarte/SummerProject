package com.example.summerproject.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.summerproject.databinding.FragmentModifyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private var firebaseAuth: FirebaseAuth? = null
private var firebaseFirestore: FirebaseFirestore? = null

class ModifyFragment : Fragment() {
    private var mBinding : FragmentModifyBinding? = null
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
        data class UserDTO(
            var password: String? = null,
            var nickname: String? = null,
            var phoneNumber: String? = null
        )

        mBinding!!.btnModify.setOnClickListener {
            var userDTO = UserDTO()
            userDTO.nickname = mBinding!!.modifyNickname.text.toString()
            userDTO.password = mBinding!!.modifyPassword.text.toString()
            userDTO.phoneNumber = mBinding!!.modifyPhoneNumber.text.toString()
            var currentemail = firebaseAuth!!.currentUser?.email.toString()

            var ref = Firebase.firestore!!.collection("userinfo").document(currentemail)

            var map = mutableMapOf<String,Any>()

            map["phoneNumber"] = userDTO.phoneNumber!!
            map["password"] = userDTO.password!!
            map["nickname"] = userDTO.nickname!!

            ref.update(map).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseAuth!!.currentUser!!.updatePassword(userDTO.password!!)
                    Toast.makeText(context, "업데이트", Toast.LENGTH_SHORT).show()
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
