package com.kotlin5.edumanager.presentation.courses


import android.content.Context
import android.content.Intent
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.kotlin5.edumanager.R
import com.kotlin5.edumanager.presentation.auth.SignInActivity
import com.kotlin5.edumanager.presentation.courses.adapter.AddCourseActivity

class DrawerManager(
    private val context: Context,
    private val drawerLayout: DrawerLayout,
    private val toolbar: androidx.appcompat.widget.Toolbar,
    private val navView: NavigationView // Correct type for navView
) {

    private val toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
        context as AppCompatActivity,
        drawerLayout,
        toolbar,
        R.string.open,
        R.string.close
    )

    init {
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    fun setupNavigationDrawer() {
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val intent = Intent(context, CourseActivity::class.java)
                    context.startActivity(intent)
                }
                R.id.addcoursebutton -> {
                    val intent = Intent(context, AddCourseActivity::class.java)
                    context.startActivity(intent)
                }
                R.id.third -> {
                    Toast.makeText(context, "Third Item Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.logout -> {
                    logout()
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(context, SignInActivity::class.java)
        context.startActivity(intent)
        (context as AppCompatActivity).finish()
    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        return toggle.onOptionsItemSelected(item)
    }
}
