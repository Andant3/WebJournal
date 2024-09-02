package com.example.webjournal.view

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.example.webjournal.R
import com.example.webjournal.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        auth = Firebase.auth

        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(applicationContext, R.color.color_primary_dark))
        )
        supportActionBar?.hide()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(binding.navHostFragmentContainer.id) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.action_loginFragment_to_journalFragment)
            //reload()
        }
    }

    private fun reload() {
        TODO("Not yet implemented")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragmentContainer.id) as NavHostFragment
        val navController = navHostFragment.navController
        val currentFragmentId = navController.currentDestination?.id

        when(currentFragmentId){
            R.id.loginFragment ->{
                menuInflater.inflate(R.menu.main_menu, menu)
            }
            R.id.journalFragment ->{
                menuInflater.inflate(R.menu.main_menu, menu)
            }
            R.id.addPageFragment ->{
                menuInflater.inflate(R.menu.add_menu, menu)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_signOut -> if (auth != null
                && auth.currentUser != null) {

                auth.signOut()
                val navHostFragment =
                    supportFragmentManager.findFragmentById(binding.navHostFragmentContainer.id) as NavHostFragment
                val navController = navHostFragment.navController
                navController.navigate(R.id.action_journalFragment_to_loginFragment)
            }
            R.id.action_saveItem -> {
                var fragment: AddPageFragment = supportFragmentManager.findFragmentById(R.id.addPageFragment) as AddPageFragment
                fragment.saveJournal()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

