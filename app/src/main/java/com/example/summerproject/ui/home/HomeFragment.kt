package com.example.summerproject.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.summerproject.R
import com.example.summerproject.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.example.summerproject.DBKey.Companion.DB_ARTICLES
import com.example.summerproject.DBKey.Companion.DB_USERS
import androidx.recyclerview.widget.DividerItemDecoration

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var articleDB: DatabaseReference
    private lateinit var userDB: DatabaseReference
    private lateinit var articleAdapter: ArticleAdapter

    private val articleList = mutableListOf<ArticleModel>()

    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val articleModel = snapshot.getValue(ArticleModel::class.java)
            articleModel ?: return

            articleList.add(articleModel) // 리스트에 새로운 항목을 더해서;
            articleAdapter.submitList(articleList) // 어뎁터 리스트에 등록;
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildRemoved(snapshot: DataSnapshot) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onCancelled(error: DatabaseError) {}

    }

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private var binding: FragmentHomeBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("sslee", "onViewCreated")

        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding

        articleList.clear() //리스트 초기화;

        initDB()

        initArticleAdapter()

        initArticleRecyclerView()

        initFloatingButton(view)

        // 데이터 가져오기;
        initListener()
    }

    private fun initListener() {
        articleDB.addChildEventListener(listener)
    }

    private fun initFloatingButton(view: View) {
        // 플로팅 버튼;
        binding!!.addFloatingButton.setOnClickListener {
            context?.let {
                if (auth.currentUser != null) {
                    val intent = Intent(it, AddArticleActivity::class.java)
                    startActivity(intent)
                } else {
                    Snackbar.make(view, "로그인 후 사용해주세요", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun initArticleRecyclerView() {
        // activity 일 때는 그냥 this 로 넘겼지만 (그자체가 컨텍스트라서) 그러나
        // 프레그 먼트의 경우에는 아래처럼. context
        binding?:return

        binding!!.recyclerView.layoutManager = LinearLayoutManager(context)
        binding!!.recyclerView.adapter = articleAdapter
    }

    private fun initArticleAdapter() {
        articleAdapter = ArticleAdapter {articleModel ->
                Intent(activity, DetailActivity()::class.java).apply {
                    putExtra("title", articleModel.title)
                    putExtra("imageurl", articleModel.imageUrl)
                    putExtra("price", articleModel.price)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run{context?.startActivity(this)}
        }
    }

    private fun initDB() {
        articleDB = Firebase.database.reference.child(DB_ARTICLES) // 디비 가져오기;
        userDB = Firebase.database.reference.child(DB_USERS)
    }

    override fun onDestroy() {
        super.onDestroy()

        articleDB.removeEventListener(listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        val recyclerView = requireView().findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.addItemDecoration(DividerItemDecoration(requireView().context, 1))

        articleAdapter.notifyDataSetChanged() // view 를 다시 그림;
    }

    private fun setArticleSample() {
        articleAdapter.submitList(mutableListOf<ArticleModel>().apply {
            add(ArticleModel("0", "AAA", 1000000, "5000원", ""))
            add(ArticleModel("0", "BBB", 2000000, "10000원", ""))
        })
    }




}