# SummerProject(오렌지마켓)
## 목차소개
  1. 소개
  2. 기획서
     + 목적 및 필요성
       + 기획 의도
       + 분석
       + 차별화요소
       + 팀원
     + 개요
       + 프로젝트 소개
       + 주요기능
       + 적용기술       
     + 수행방법 및 일정
       + 수행방법
       + 추진일정
     + 비즈니스 모델
  3. 개발서
     + 교재
     + Google
  4. 각 클래스별 코드
     + MainActivity(LoginActivity)
     + SignUpActivity
     + ChatRoomActivity
     + MapsActivity
     + MypageFragment
  5. 기타
     + 일정관리
     + 추후목표
     
## 1. 소개
경기대학교 컴퓨터 공학도로서 코틀린(안드로이드 스튜디오)실력 향상을 위한 프로젝트로서 당근마켓을 모티브로 중고거래 어플을 제작해보는 여름방학 프로젝트

## 2. 기획서
  + ### 목적 및 필요성
    + #### 기획 의도
      2017년 구글 I/O에서 공식언어로 지정된 코틀린을 이용하여 어플을 개발하며 코틀린 언어 공부 및 실력 향상.
     
      기존 중고거래어플의 대면거래시 범죄의 취약함이 드러남에 따라 비대면으로 거래할 수 있도록 기획함
    + #### 분석
      안드로이드 스튜디오를 사용하여 회원, 상품, 채팅을 FireBase를 연동하여 구현

      FrontEnd 부분은 BottomNavigation을 이용하여 Activity와 Fragment를 적절히 섞어 사용.

    + #### 차별화요소
      현재 구글맵을 통해 사물함 마커를 찍어놨고, 추후에 사물함 예약등 비대면거래 플랫폼 추가 예정
      
    + #### 팀원
      경기대학교
      
      201611814 신범석
      
      201611851 정유성
      
      201611853 정재헌
      
      201713737 김규호

  + ### 개요
    + #### 프로젝트 소개
      중고거래마켓 어플 구현 (회원가입/로그인/상품등록 및 삭제/채팅/지도/회원정보관리)
    + #### 주요기능
      1. FireBase 연동을 이용한 로그인 및 회원가입 구현
      2. Activity와 Fragment별로 각 항목을 나누어 구현
      3. 이메일 인증을 통한 회원가입
    + #### 적용기술
      1. 개발 툴 : Android Studio
      2. Android Studio-FireBase 연동을 통한 Android Studio상에서 쿼리수정
      3. API : Marshmallow
   + ### 수행 방법 및 일정
     + #### 수행방법
       매일 한 기능씩 할당량을 정해놓은 후, 일주일에 주말을 제외한 3~5일 미팅을 가지며 프로젝트 진행 
       
     + #### 추진일정
       2021.07.01 프로젝트 시작
     
       2021.07.08 Firebase, Git 연동 완료
     
       2021.07.12 회원가입, 로그인 완료
     
       2021.07.19 로그아웃, 자동로그인 완료
     
       2021.07.23 회원정보수정 프래그먼트에 get data 기능추가

       2021.07.26 회원정보수정 동일패스워드인 경우 유효성 검사 추가, 회원탈퇴, 비밀번호 변경 Fragment추가

       2021.08.08 거래 게시판(홈화면, RecyclerView) 추가

       2021.08.10 홈화면 아이템 구분선, 아이템 등록 페이지 수정, 마이페이지에 닉네임, 이메일 나타나게 구현

       2021.08.13 상품등록 유효성검사, 상품 상세보기 Fragment, 마이페이지 UI 수정

       2021.08.18 상품 삭제 버튼, 채팅(기능/UI)완료

       2021.08.24 구글맵을 이용한 위치 서비스

  + ### 비즈니스 모델
     + #### 당근마켓
## 3. 개발서
  + ### 교재
    이것이 안드로이드다 with 코틀린
  + ### Google
    교재에 나오지 않은 내용 검색
    
## 4. 구현 사진
  + ### 메인화면(로그인 화면)
    ![KakaoTalk_20210827_155500127_05](https://user-images.githubusercontent.com/76707524/131331058-e24020dc-7e45-49d1-bf51-4397daede469.jpg)
    
  + ### 중고거래장터(홈 화면)
    ![KakaoTalk_20210827_155500127_01](https://user-images.githubusercontent.com/76707524/131331166-aeb9ab3c-431f-45d1-ae7c-11134c420ba2.jpg)
    
  + ### 채팅방목록
    ![KakaoTalk_20210827_155513341](https://user-images.githubusercontent.com/76707524/131331214-8c3fc3ec-9e19-4612-9363-330ae63937f9.jpg)

  + ### 채팅화면
    ![KakaoTalk_20210827_155500127](https://user-images.githubusercontent.com/76707524/131331244-a6cf385a-cb14-49ea-a6a5-34ef85eaf062.jpg)
    
  + ### 지도화면(사물함 위치)
    ![KakaoTalk_20210827_155500127_02](https://user-images.githubusercontent.com/76707524/131331299-83f35a05-0b12-475b-b1f6-4b738ae8c762.jpg)
    
  + ### 마이페이지
    ![KakaoTalk_20210827_155500127_04](https://user-images.githubusercontent.com/76707524/131331334-bfa2bc8f-4510-4860-864a-3ca65d60c781.jpg)
    
## 5. 각 클래스별 코드
  + ### MainActivity(LoginActivity)
```
package com.orangemarket.orangemarket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.orangemarket.orangemarket.databinding.ActivityMainBinding

private var firebaseAuth: FirebaseAuth? = null
var backKeyPressedTime : Long = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(firebaseAuth!!.currentUser!=null && firebaseAuth!!.currentUser!!.isEmailVerified){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.goToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            login(binding.email.text.toString(), binding.password.text.toString())
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(System.currentTimeMillis() > backKeyPressedTime+2500){
            backKeyPressedTime = System.currentTimeMillis()
            return
        }
        if(System.currentTimeMillis() <= backKeyPressedTime+2500){
            finishAffinity()
        }
    }

    private fun login(email: String, password: String){
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "모든 칸을 입력해주세요", Toast.LENGTH_SHORT).show()
        } else if(email.isNotEmpty() && password.isNotEmpty()){
            firebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    var user= firebaseAuth!!.currentUser!!.isEmailVerified
                    if (user) {
                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, "로그인 실패, 이메일 인증을 확인해주세요", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
```  
  + ### SignUpActivity
```
package com.orangemarket.orangemarket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.orangemarket.orangemarket.databinding.ActivityMainBinding

private var firebaseAuth: FirebaseAuth? = null
var backKeyPressedTime : Long = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(firebaseAuth!!.currentUser!=null && firebaseAuth!!.currentUser!!.isEmailVerified){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.goToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            login(binding.email.text.toString(), binding.password.text.toString())
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(System.currentTimeMillis() > backKeyPressedTime+2500){
            backKeyPressedTime = System.currentTimeMillis()
            return
        }
        if(System.currentTimeMillis() <= backKeyPressedTime+2500){
            finishAffinity()
        }
    }

    private fun login(email: String, password: String){
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "모든 칸을 입력해주세요", Toast.LENGTH_SHORT).show()
        } else if(email.isNotEmpty() && password.isNotEmpty()){
            firebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    var user= firebaseAuth!!.currentUser!!.isEmailVerified
                    if (user) {
                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this, "로그인 실패, 이메일 인증을 확인해주세요", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
```
  + ## ChatRoomActivity
```
package com.orangemarket.orangemarket.ui.chatdetail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.orangemarket.orangemarket.DBKey.Companion.DB_CHAT
import com.google.firebase.firestore.FirebaseFirestore
import com.orangemarket.orangemarket.databinding.ActivityChatroomBinding
import kotlin.properties.Delegates

private var chatItemAdapter = ChatItemAdapter
private var firebaseAuth: FirebaseAuth? = null
private var firebaseFirestore: FirebaseFirestore? = null
private lateinit var nickname:String
class ChatRoomActivity : AppCompatActivity() {
    private val binding by lazy { ActivityChatroomBinding.inflate(layoutInflater)}

    private val chatList = mutableListOf<ChatItem>()
    private val adapter = ChatItemAdapter()
    lateinit var chatDB : DatabaseReference
    var chatKey by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        val currentemail = firebaseAuth!!.currentUser?.email.toString()
        firebaseFirestore!!.collection("userinfo").document(currentemail).get()
            .addOnSuccessListener { documentSnapshot ->
                nickname = documentSnapshot.get("nickname").toString()
            }
        chatKey = intent.getLongExtra("chatKey", -1)


        initChatDB()

        initChatListRecyclerView()

        initSendButton()

    }


    private fun initChatDB() {
        chatDB = Firebase.database.reference.child(DB_CHAT).child("$chatKey")
        chatDB.addChildEventListener(object: ChildEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                val chatItem = snapshot.getValue(ChatItem::class.java)
                chatItem?:return

                // 채팅 추가;
                chatList.add(chatItem)
                adapter.submitList(chatList)
                adapter.notifyDataSetChanged()
                binding.chatListRecyclerView.scrollToPosition(chatList.size-1)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun initChatListRecyclerView() {
        binding.chatListRecyclerView.adapter = adapter
        binding.chatListRecyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun initSendButton() {
        binding.sendButton.setOnClickListener {
            firebaseAuth?.currentUser?:return@setOnClickListener
            val chatItem = ChatItem(
                senderId = firebaseAuth?.currentUser!!.uid,
                message = binding.messageEditText.text.toString(),
                senderNickname = nickname
            )
            chatDB.push().setValue(chatItem)
            binding.messageEditText.setText("")
            binding.chatListRecyclerView.scrollToPosition(chatList.size-1)

        }
    }
}
```
  + ## MapsActivity
```
package com.orangemarket.orangemarket.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.orangemarket.orangemarket.R
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.orangemarket.orangemarket.databinding.ActivityMapsBinding


class MapsActivity : com.orangemarket.orangemarket.BaseActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    val binding by lazy { ActivityMapsBinding.inflate(layoutInflater) }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        requirePermissions(permissions, 999)
    }

    override fun permissionGranted(requestCode: Int) {
        startProcess()
    }

    override fun permissionDenied(requestCode: Int) {
        Toast.makeText(this, "권한 승인이 필요합니다.", Toast.LENGTH_LONG).show()
    }

    private fun startProcess() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        updateLocation()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mMap.isMyLocationEnabled=true
    }

    @SuppressLint("MissingPermission")
    fun updateLocation() {
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            //interval = 1000
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.let {
                    for(location in it.locations) {
                        Log.d("Location", "${location.latitude} , ${location.longitude}")
                        setLastLocation(location)
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper()!!)
    }

    fun setLastLocation(lastLocation: Location) {
        val myLocation = LatLng(lastLocation.latitude, lastLocation.longitude)

        val marker1 = LatLng(37.300663155973176, 127.03110725918845)
        val marker2 = LatLng(37.29386675247959, 127.02823535266599)

        val markerOptions = MarkerOptions()
            .position(marker1)
            .title("신미주")

        val markerOptions2 = MarkerOptions()
            .position(marker2)
            .title("우리집")

        val cameraPosition = CameraPosition.Builder()
            .target(myLocation)
            .zoom(15.0f)
            .build()

        mMap.clear()
        mMap.addMarker(markerOptions)
        mMap.addMarker(markerOptions2)
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun onBackPressed() {
        finish()
    }
}
```
  + ## MypageFragment
```
package com.orangemarket.orangemarket.ui.mypage

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
import com.orangemarket.orangemarket.HomeActivity
import com.orangemarket.orangemarket.MainActivity
import com.orangemarket.orangemarket.databinding.AlertdialogEdittextBinding
import com.orangemarket.orangemarket.databinding.FragmentMypageBinding
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
```

## 5. 기타
  + ### 일정관리
    주 3회이상 무조건 미팅
  + ### 추후목표
    상품 등록시, 위치를 등록하여 Map에 표시.
    
    사물함 예약기능 및 사물함 위치 DB에추가, Map에 마커 표시
