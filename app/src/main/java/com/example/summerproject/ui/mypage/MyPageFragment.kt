package com.example.summerproject.ui.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.summerproject.HomeActivity
import com.example.summerproject.MainActivity
import com.example.summerproject.databinding.FragmentMypageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private var firebaseAuth: FirebaseAuth? = null
private var firebaseFirestore: FirebaseFirestore? = null

class MyPageFragment : Fragment() {
    private var mainActivity: HomeActivity? =null
    private var mBinding: FragmentMypageBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity=context as HomeActivity

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMypageBinding.inflate(inflater, container, false)

        mBinding = binding
        return mBinding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
    }

    override fun onStart() {
        super.onStart()
        mBinding?.profileModify?.setOnClickListener() {
            mainActivity?.replaceFragment(ModifyFragment())
        }
    }

    override fun onResume() {
        mBinding!!.profileDelete.setOnClickListener {
            val user = firebaseAuth!!.currentUser!!

            var currentemail = firebaseAuth!!.currentUser?.email.toString()

            firebaseFirestore!!.collection("userinfo").document(currentemail).delete()

            user.delete().addOnCompleteListener { task->
                if(task.isSuccessful){
                    Toast.makeText(context, "탈퇴 완료", Toast.LENGTH_SHORT).show()
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(context, "오류", Toast.LENGTH_SHORT).show()
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

