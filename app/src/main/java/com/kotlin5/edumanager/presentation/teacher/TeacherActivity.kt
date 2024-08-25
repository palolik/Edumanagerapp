package com.kotlin5.edumanager.presentation.teacher

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.kotlin5.edumanager.R
import com.kotlin5.edumanager.databinding.ActivityAdminBinding
import com.kotlin5.edumanager.databinding.ActivityTeacherBinding
import com.kotlin5.edumanager.presentation.admin.adapter.CourseList
import com.kotlin5.edumanager.presentation.admin.viewmodel.MainReviewActivityViewModel
import com.kotlin5.edumanager.presentation.auth.SignInActivity

class TeacherActivity : AppCompatActivity() {
    private lateinit var context: Context
    private lateinit var binding: ActivityTeacherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val logoutButton: Button = binding.logout
        logoutButton.setOnClickListener {
            logout()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }
    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(context, SignInActivity::class.java)
        context.startActivity(intent)
        (context as AppCompatActivity).finish()
    }

}
