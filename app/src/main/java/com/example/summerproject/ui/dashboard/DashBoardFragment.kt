package com.example.summerproject.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.summerproject.databinding.FragmentDashboardBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*


class DashBoardFragment : Fragment() {

    //    private var currentUser: String ?= null
    private var firebaseFirestore: FirebaseFirestore? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private var currentUser2: String? = null          // 현재 닉네임

    private lateinit var registration: ListenerRegistration    // 문서 수신
    private val chatList = arrayListOf<ChatLayout>()    // 리사이클러 뷰 목록
    private lateinit var adapter: ChatAdapter   // 리사이클러 뷰 어댑터

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root

        var currentemail = firebaseAuth?.currentUser?.email.toString()

        firebaseFirestore!!.collection("Chat").document(currentemail).get()
            .addOnSuccessListener { documentSnapshot ->
                currentUser2 = documentSnapshot.get("nickname").toString()
                Log.d("aaaa", currentUser2!!)

                Toast.makeText(context, "현재 닉네임은 ${currentUser2}입니다.", Toast.LENGTH_SHORT).show()
                // 리사이클러 뷰 설정
                binding.rvList.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = ChatAdapter(currentUser2!!, chatList)
                binding.rvList.adapter = adapter

                // 채팅창이 공백일 경우 버튼 비활성화
                binding.etChatting.addTextChangedListener { text ->
                    binding.btnSend.isEnabled = text.toString() != ""
                }

                // 입력 버튼
                binding.btnSend.setOnClickListener {
                    // 입력 데이터
                    val data = hashMapOf(
                        "nickname" to currentUser2!!,
                        "contents" to binding.etChatting.text.toString(),
                        "time" to Timestamp.now()
                    )
                    // Firestore에 기록
                    firebaseFirestore!!.collection("Chat").add(data)
                        .addOnSuccessListener {
                            binding.etChatting.text.clear()
                            Log.w("ChatFragment", "Document added: $it")
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "전송하는데 실패했습니다", Toast.LENGTH_SHORT).show()
                            Log.w("ChatFragment", "Error occurs: $e")
                        }
                }
            }
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var currentemail = firebaseAuth?.currentUser?.email.toString()

        firebaseFirestore!!.collection("Chat").document(currentemail).get()
            .addOnSuccessListener { documentSnapshot ->
                currentUser2 = documentSnapshot.get("nickname").toString()

                chatList.add(ChatLayout("알림", "$currentUser2 닉네임으로 입장했습니다.", ""))
                val enterTime = Date(System.currentTimeMillis())

                registration = firebaseFirestore!!.collection("Chat")
                    .orderBy("time", Query.Direction.DESCENDING)
                    .limit(1)
                    .addSnapshotListener { snapshots, e ->
                        // 오류 발생 시
                        if (e != null) {
                            Log.w("ChatFragment", "Listen failed: $e")
                            return@addSnapshotListener
                        }

                        // 원하지 않는 문서 무시
                        if (snapshots!!.metadata.isFromCache) return@addSnapshotListener

                        // 문서 수신
                        for (doc in snapshots.documentChanges) {
                            val timestamp = doc.document["time"] as com.google.firebase.Timestamp

                            // 문서가 추가될 경우 리사이클러 뷰에 추가
                            if (doc.type == DocumentChange.Type.ADDED && timestamp.toDate() > enterTime) {
                                val nickname = doc.document["nickname"].toString()
                                val contents = doc.document["contents"].toString()

                                // 타임스탬프를 한국 시간, 문자열로 바꿈
                                val sf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREA)
                                sf.timeZone = TimeZone.getTimeZone("Asia/Seoul")
                                val time = sf.format(timestamp.toDate())

                                val item = ChatLayout(nickname, contents, time)
                                chatList.add(item)
                            }
                            adapter.notifyDataSetChanged()
                        }
                    }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        registration.remove()
        _binding = null
    }

}