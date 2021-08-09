package com.example.summerproject.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.summerproject.DBKey.Companion.DB_ARTICLES
import com.example.summerproject.DBKey.Companion.DB_USERS
import com.example.summerproject.R
import com.example.summerproject.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

const val TITLE = "title"
const val IMAGEURL = "imageurl"
const val PRICE = "price"
private var firebaseAuth: FirebaseAuth? = null

data class ChatListItem(
    val buyerId: String,
    val sellerId: String,
    val itemTitle: String,
    val key: Long
){
    constructor():this("","","",0)
}


class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var articleDB: DatabaseReference
    private lateinit var userDB: DatabaseReference
    private lateinit var articleAdapter: HomeAdapter

    private val articleList = mutableListOf<ArticleModel>()

    private var mBinding: FragmentHomeBinding? = null

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        mBinding = binding
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        articleList.clear() //리스트 초기화;

        initDB()

        initArticleAdapter(view)

        initArticleRecyclerView()

        // 데이터 가져오기;
        initListener()

        initFloatingButton(view)
    }
    private fun initListener() {
        articleDB.addChildEventListener(listener)
    }

    private fun initArticleRecyclerView() {
        // activity 일 때는 그냥 this 로 넘겼지만 (그자체가 컨텍스트라서) 그러나
        // 프레그 먼트의 경우에는 아래처럼. context
        mBinding?:return

        mBinding!!.recyclerView.layoutManager = LinearLayoutManager(context)
        mBinding!!.recyclerView.adapter = articleAdapter
    }

    private fun initArticleAdapter(view: View) {
        articleAdapter = HomeAdapter {articleModel ->
            Intent(activity, DetailActivity()::class.java).apply {
                putExtra("title", articleModel.title)
                putExtra("imageUrl", articleModel.imageUrl)
                putExtra("price", articleModel.price)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }.run{context?.startActivity(this)}
        }
    }

    private fun initDB() {
        articleDB = Firebase.database.reference.child(DB_ARTICLES) // 디비 가져오기;
        userDB = Firebase.database.reference.child(DB_USERS)
    }

    private fun initFloatingButton(view: View) {
        // 플로팅 버튼;
        mBinding!!.addFloatingButton.setOnClickListener {
            context?.let {
                if (firebaseAuth!!.currentUser != null) {
                    val intent = Intent(it, AddArticleActivity::class.java)
                    startActivity(intent)
                } else {
                    Snackbar.make(view, "로그인 후 사용해주세요", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}




