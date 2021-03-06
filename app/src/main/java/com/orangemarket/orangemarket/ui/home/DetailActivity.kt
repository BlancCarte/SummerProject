package com.orangemarket.orangemarket.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.orangemarket.orangemarket.DBKey.Companion.CHILD_CHAT
import com.orangemarket.orangemarket.DBKey.Companion.DB_USERS
import com.orangemarket.orangemarket.MainActivity
import com.orangemarket.orangemarket.ui.chatlist.ChatListItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.orangemarket.orangemarket.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {


    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseFirestore: FirebaseFirestore? = null
    private lateinit var binding: ActivityDetailBinding
    private lateinit var selleremail: String
    private lateinit var title1: String
    private lateinit var sellerId: String
    private lateinit var userDB: DatabaseReference
    private lateinit var dbkey1: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        title = "상세정보"
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getSerializableExtra("title")
        val imageurl = intent.getSerializableExtra("imageurl")
        val price = intent.getSerializableExtra("price")
        val content = intent.getSerializableExtra("content")
        val sellerEmail = intent.getSerializableExtra("sellerEmail")
        val nickname = intent.getSerializableExtra("nickname")
        selleremail = intent.getSerializableExtra("sellerEmail") as String
        title1 = intent.getStringExtra("title") as String
        sellerId = intent.getStringExtra("sellerId") as String

        binding.titleTextView.text = title.toString()
        binding.sellerNicknameTextView.text = sellerEmail.toString()
        binding.priceTextView.text = price.toString()
        binding.contentTextView.text = content.toString()

        Glide.with(binding.titleimageView)
            .load(imageurl)
            .into(binding.titleimageView)

        binding.deleteButton.isVisible = sellerEmail == firebaseAuth?.currentUser?.email
        userDB = Firebase.database.reference.child(DB_USERS)
        val currentemail = firebaseAuth!!.currentUser?.email.toString()

        firebaseFirestore!!.collection("userinfo").document(currentemail).get()
            .addOnSuccessListener {
                binding.deleteButton.setOnClickListener {
                    val key = nickname.toString().plus(",").plus(title)
                    Firebase.database.reference.child("Articles").child(key).removeValue()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }

        binding.submitButton.setOnClickListener {
            initArticleAdapter2()
        }
    }

    private fun initArticleAdapter() {
        val buyerId = firebaseAuth?.currentUser?.uid.toString()
        val sellerId = sellerId
        val itemTitle = title1
        val key1 = buyerId.plus(sellerId).plus(itemTitle)
        if (firebaseAuth?.currentUser?.uid != sellerId) {
            // 채팅방 생성
            val chatRoom = ChatListItem(
                buyerId = firebaseAuth?.currentUser?.uid.toString(),
                sellerId = sellerId,
                itemTitle = title1,
                key = System.currentTimeMillis(),
                key1 = key1
            )
            userDB.child(firebaseAuth?.currentUser!!.uid) // 계속 워닝 떠서 !! 처리;
                .child(CHILD_CHAT)
                .child(key1)
                .setValue(chatRoom)

            userDB.child(sellerId)
                .child(CHILD_CHAT)
                .child(key1)
                .setValue(chatRoom)

            val num = 1
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("num", num)
            startActivity(intent)
            finish()
        } else {
            // 내가 올린 아이템 일때
            Toast.makeText(this, "내가 올린 제품입니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initArticleAdapter2() {
        val database = FirebaseDatabase.getInstance().reference

        val buyerId = firebaseAuth?.currentUser?.uid.toString()
        val sellerId = sellerId
        val itemTitle = title1
        val key1 = buyerId.plus(sellerId).plus(itemTitle)
        database.child("Users").child(buyerId).child("chat").child(key1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    dbkey1 = snapshot.child("key1").value.toString()
                    if (dbkey1 != key1) {
                        initArticleAdapter()
                    } else {
                        Toast.makeText(this@DetailActivity, "이미 생성된 채팅방입니다", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }
    override fun onBackPressed() {
        finish()
    }

}