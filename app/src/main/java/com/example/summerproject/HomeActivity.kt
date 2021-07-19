package com.example.summerproject


import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.summerproject.databinding.ActivityHomeBinding
import com.example.summerproject.ui.home.HomeFragment
import com.example.summerproject.ui.mypage.MypageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.homeToolbar.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        val navController = findNavController(R.id.frameLayout)
        appBarConfiguration = AppBarConfiguration(
            setOf
                (
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_mypage
            )
        )

        val bottomNavView: BottomNavigationView = binding.bottomNavView


        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavView.setupWithNavController(navController)


        setFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.frameLayout)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun setFragment() {
        val homeFragment: HomeFragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout, homeFragment)
        transaction.commit()
    }

    fun goMypage() {
        val mypageFragment: MypageFragment = MypageFragment()
        val transaction = supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout, mypageFragment)
        transaction.addToBackStack("mypage")
        transaction.commit()
    }

    fun goBack(){
        onBackPressed()
    }
}
