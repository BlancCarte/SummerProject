package com.example.summerproject


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.summerproject.databinding.ActivityHomeBinding
import com.example.summerproject.ui.dashboard.DashboardFragment
import com.example.summerproject.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth


private var firebaseAuth: FirebaseAuth? = null

class HomeActivity  : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        firebaseAuth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar.homeToolbar)

        val transaction =supportFragmentManager.beginTransaction()
        transaction.add(
            R.id.fragment, HomeFragment()
        )
        transaction.commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }


    //액션버튼 클릭 했을 때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.action_search->{ //검색 버튼 눌렀을 때
                Toast.makeText(applicationContext, "검색 실행", Toast.LENGTH_SHORT).show()
                return super.onOptionsItemSelected(item)
            }
            R.id.action_notification-> { //알림 버튼 눌렀을 때
                Toast.makeText(applicationContext, "알림 실행", Toast.LENGTH_SHORT).show()
                return super.onOptionsItemSelected(item)
            }
            R.id.action_userinfo->{//회원정보 눌렀을 때
                Toast.makeText(applicationContext, "회원정보 실행", Toast.LENGTH_SHORT).show()
                return super.onOptionsItemSelected(item)
            }
            R.id.action_option->{//회원정보 눌렀을 때
                Toast.makeText(applicationContext, "설정 실행", Toast.LENGTH_SHORT).show()
                return super.onOptionsItemSelected(item)
            }
            R.id.action_logout-> { //로그아웃 버튼 눌렀을 때
                Toast.makeText(applicationContext, "로그아웃 실행", Toast.LENGTH_SHORT).show()
                firebaseAuth!!.signOut();
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun replaceFragment(){
        val textFragment = DashboardFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment_activity_home, textFragment).commit()

    }
}