package com.orangemarket.orangemarket.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.orangemarket.orangemarket.DBKey.Companion.DB_ARTICLES
import com.orangemarket.orangemarket.DBKey.Companion.DB_USERS
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.orangemarket.orangemarket.databinding.FragmentHomeBinding
import com.orangemarket.orangemarket.R


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var articleDB: DatabaseReference
    private lateinit var userDB: DatabaseReference
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var nickname:String
    private var binding: FragmentHomeBinding? = null
    private var firebaseFirestore: FirebaseFirestore? = null
    private var firebaseAuth: FirebaseAuth? = null
    private val articleList = mutableListOf<com.orangemarket.orangemarket.ArticleModel>()

    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val articleModel = snapshot.getValue(com.orangemarket.orangemarket.ArticleModel::class.java)
            articleModel ?: return

            articleList.add(articleModel) // 리스트에 새로운 항목을 더해서;
            articleAdapter.submitList(articleList) // 어뎁터 리스트에 등록;

            val mLayoutManager = LinearLayoutManager(activity)
            mLayoutManager.reverseLayout = true
            mLayoutManager.stackFromEnd = true
            binding?.recyclerView?.setLayoutManager(mLayoutManager)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildRemoved(snapshot: DataSnapshot) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onCancelled(error: DatabaseError) {}

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding

        articleList.clear() //리스트 초기화;

        initDB()

        getNickname()

        initArticleAdapter()

        initArticleRecyclerView()

        initFloatingButton(view)

        initListener()
    }

    private fun initDB() {
        articleDB = Firebase.database.reference.child(DB_ARTICLES) // 디비 가져오기;
        userDB = Firebase.database.reference.child(DB_USERS)
    }

    private fun getNickname(){
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        val currentemail = firebaseAuth!!.currentUser?.email.toString()
        firebaseFirestore!!.collection("userinfo").document(currentemail).get()
            .addOnSuccessListener { documentSnapshot ->
                nickname = documentSnapshot.get("nickname").toString()
            }
    }

    private fun initArticleAdapter() {
        articleAdapter = ArticleAdapter { articleModel ->
            Intent(activity, DetailActivity()::class.java).apply {
                putExtra("title", articleModel.title)
                putExtra("imageurl", articleModel.imageUrl)
                putExtra("price", articleModel.price)
                putExtra("content", articleModel.content)
                putExtra("sellerEmail", articleModel.sellerEmail)
                putExtra("time", articleModel.createdAt)
                putExtra("nickname", articleModel.nickname)
                putExtra("sellerId", articleModel.sellerId)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }.run { context?.startActivity(this) }
        }
    }

    private fun initArticleRecyclerView() {
        // activity 일 때는 그냥 this 로 넘겼지만 (그자체가 컨텍스트라서) 그러나
        // 프레그 먼트의 경우에는 아래처럼. context
        binding ?: return
        binding!!.recyclerView.layoutManager = LinearLayoutManager(context)
        binding!!.recyclerView.adapter = articleAdapter
    }

    private fun initFloatingButton(view: View) {
        // 플로팅 버튼;
        binding!!.addFloatingButton.setOnClickListener {
            context?.let {
                if (firebaseAuth?.currentUser != null) {
                    val intent = Intent(it, AddArticleActivity::class.java)
                    startActivity(intent)
                } else {
                    Snackbar.make(view, "로그인 후 사용해주세요", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun initListener() {
        articleDB.addChildEventListener(listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        val recyclerView = requireView().findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.addItemDecoration(DividerItemDecoration(requireView().context, 1))
        articleAdapter.notifyDataSetChanged() // view 를 다시 그림;

    }

    override fun onDestroy() {
        super.onDestroy()
        articleDB.removeEventListener(listener)
    }
}