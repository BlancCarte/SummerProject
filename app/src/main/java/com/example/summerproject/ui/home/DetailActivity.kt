package com.example.summerproject.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.summerproject.DBKey.Companion.CHILD_CHAT
import com.example.summerproject.DBKey.Companion.DB_USERS
import com.example.summerproject.HomeActivity
import com.example.summerproject.databinding.ActivityDetailBinding
import com.example.summerproject.ui.chatList.ChatListFragment
import com.example.summerproject.ui.chatList.ChatListItem
import com.example.summerproject.ui.chatdetail.ChatRoomActivity
import com.example.summerproject.ui.mypage.ModifyFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class DetailActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseFirestore: FirebaseFirestore? = null
    private lateinit var binding: ActivityDetailBinding
    private lateinit var selleremail:String
    private lateinit var title1:String
    private lateinit var sellerId:String

    private lateinit var userDB: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        setTitle("상세정보")
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var title = intent.getSerializableExtra("title")
        var imageurl = intent.getSerializableExtra("imageurl")
        var price = intent.getSerializableExtra("price")
        var content = intent.getSerializableExtra("content")
        var sellerEmail = intent.getSerializableExtra("sellerEmail")
        var nickname = intent.getSerializableExtra("nickname")
        selleremail = intent.getSerializableExtra("sellerEmail") as String
        title1 = intent.getStringExtra("title") as String
        sellerId = intent.getStringExtra("sellerId") as String
        val key = title.toString().plus(price).plus(content)
        binding.titleTextView.text = title.toString()
        binding.sellerNicknameTextView.text = sellerEmail.toString()
        binding.priceTextView.text = price.toString()
        binding.contentTextView.text = content.toString()
        Glide.with(binding.titleimageView)
            .load(imageurl)
            .into(binding.titleimageView)
        if (sellerEmail != firebaseAuth?.currentUser?.email) {
            binding.deleteButton.isVisible = false
        } else {
            binding.deleteButton.isVisible = true
        }
        userDB = Firebase.database.reference.child(DB_USERS)
        val currentemail = firebaseAuth!!.currentUser?.email.toString()

        firebaseFirestore!!.collection("userinfo").document(currentemail).get()
            .addOnSuccessListener { documentSnapshot ->
                binding.deleteButton.setOnClickListener {
                    val key = nickname.toString().plus(",").plus(title)
                    Firebase.database.reference.child("Articles").child(key).removeValue()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
            }
        binding.submitButton.setOnClickListener{
            initArticleAdapter(this)

        }
    }

    private fun initArticleAdapter(view: DetailActivity) {
           if(firebaseAuth?.currentUser != null){
                // 로그인 상태;
                if(firebaseAuth?.currentUser?.uid != sellerId){
                    // 채팅방 생성
                    val chatRoom = ChatListItem(
                        buyerId = firebaseAuth?.currentUser?.uid.toString(),
                        sellerId = sellerId,
                        itemTitle = title1,
                        key = System.currentTimeMillis()
                    )

                    userDB.child(firebaseAuth?.currentUser!!.uid) // 계속 워닝 떠서 !! 처리;
                        .child(CHILD_CHAT)
                        .push()
                        .setValue(chatRoom)

                    userDB.child(sellerId)
                        .child(CHILD_CHAT)
                        .push()
                        .setValue(chatRoom)

                    Toast.makeText(view, "채팅리스트 확인바람.", Toast.LENGTH_LONG).show()
                    var num = 1
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("num", num)
                    startActivity(intent)



                }else{
                    // 내가 올린 아이템 일때
                    Toast.makeText(view, "내가 올린 아이템입니다.", Toast.LENGTH_LONG).show()
                }
            }else{
                // 로그아웃 상태;
                Toast.makeText(view, "로그인 후 사용해주세요", Toast.LENGTH_LONG).show()
            }


        }
    }
