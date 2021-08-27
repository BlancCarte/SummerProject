package com.example.summerproject.ui.mypage

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.summerproject.HomeActivity
import com.example.summerproject.MainActivity
import com.example.summerproject.databinding.AlertdialogEdittextBinding
import com.example.summerproject.databinding.FragmentMypageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


private var firebaseAuth: FirebaseAuth? = null
private var firebaseFirestore: FirebaseFirestore? = null

class MyPageFragment : Fragment() {
    private var mainActivity: HomeActivity? = null
    private var mBinding: FragmentMypageBinding? = null

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
        val binding = FragmentMypageBinding.inflate(inflater, container, false)

        mBinding = binding
        return mBinding?.root
    }

    override fun onResume() {
        val currentemail = firebaseAuth!!.currentUser?.email.toString()

        firebaseFirestore!!.collection("userinfo").document(currentemail).get()
            .addOnSuccessListener { documentSnapshot ->
                val nickname = documentSnapshot.get("nickname").toString()
                mBinding!!.nickname.setText(nickname)
                mBinding!!.email.setText(currentemail)
            }

        mBinding!!.sell.setOnClickListener{
            Toast.makeText(context, "판매내역 실행", Toast.LENGTH_SHORT).show()
        }

        mBinding!!.buy.setOnClickListener{
            Toast.makeText(context, "구매내역 실행", Toast.LENGTH_SHORT).show()
        }
        mBinding!!.interest.setOnClickListener{
            Toast.makeText(context, "찜목록 실행", Toast.LENGTH_SHORT).show()
        }

        mBinding!!.profileModify.setOnClickListener {
            val currentemail = firebaseAuth!!.currentUser?.email.toString()

            firebaseFirestore!!.collection("userinfo").document(currentemail).get()
                .addOnSuccessListener { documentSnapshot ->
                    val password = documentSnapshot.get("password").toString()

                    val builder = AlertDialog.Builder(context)
                    val builderItem = AlertdialogEdittextBinding.inflate(layoutInflater)
                    var editText = builderItem.checkPassword.text
                    with(builder) {
                        setTitle("비밀번호 확인")
                        setMessage("비밀번호를 입력하세요")
                        setView(builderItem.root)
                        setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                            if (editText.toString() == password) {
                                mainActivity?.replaceFragment(ModifyFragment())
                            } else {
                                Toast.makeText(context, "비밀번호 불일치", Toast.LENGTH_SHORT).show()
                            }
                        }
                        show()
                    }
                }
        }

        mBinding!!.profilePassword.setOnClickListener {
            val currentemail = firebaseAuth!!.currentUser?.email.toString()
            firebaseFirestore!!.collection("userinfo").document(currentemail).get()
                .addOnSuccessListener { documentSnapshot ->
                    val password = documentSnapshot.get("password").toString()
                    val builder = AlertDialog.Builder(context)
                    val builderItem = AlertdialogEdittextBinding.inflate(layoutInflater)
                    var editText = builderItem.checkPassword.text
                    with(builder) {
                        setTitle("비밀번호 확인")
                        setMessage("비밀번호를 입력하세요")
                        setView(builderItem.root)
                        setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                            if (editText.toString() == password) {
                                mainActivity?.replaceFragment(ModifyPasswordFragment())
                            } else {
                                Toast.makeText(context, "비밀번호 불일치", Toast.LENGTH_SHORT).show()
                            }
                        }
                        show()
                    }
                }
        }

        mBinding!!.profileDelete.setOnClickListener {
            val user = firebaseAuth!!.currentUser!!
            var currentemail = firebaseAuth!!.currentUser?.email.toString()
            firebaseFirestore!!.collection("userinfo").document(currentemail).get()
                .addOnSuccessListener { documentSnapshot ->
                    val password = documentSnapshot.get("password").toString()
                    val builder = AlertDialog.Builder(context)
                    val builderItem = AlertdialogEdittextBinding.inflate(layoutInflater)
                    var editText = builderItem.checkPassword.text

                    with(builder) {
                        setTitle("비밀번호 확인")
                        setMessage("비밀번호를 입력하세요")
                        setView(builderItem.root)
                        setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                            if (editText.toString() == password) {
                                firebaseFirestore!!.collection("userinfo").document(currentemail)
                                    .delete()
                                user.delete().addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(context, "탈퇴 완료", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(activity, MainActivity::class.java)
                                        startActivity(intent)
                                    } else {
                                        Toast.makeText(context, "오류", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(context, "비밀번호 불일치", Toast.LENGTH_SHORT).show()
                            }
                        }
                        show()
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