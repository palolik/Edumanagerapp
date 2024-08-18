package com.kotlin5.edumanager.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kotlin5.edumanager.R
import com.kotlin5.edumanager.databinding.ActivitySignInBinding
import com.kotlin5.edumanager.presentation.courses.CourseActivity
import com.kotlin5.edumanager.presentation.admin.AdminActivity // Example for admin
import com.kotlin5.edumanager.presentation.teacher.TeacherActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        firebaseAuth.currentUser?.uid?.let { uid ->
                            firestore.collection("users").document(uid).get()
                                .addOnSuccessListener { document ->
                                    val role = document.getString("role")
                                    navigateToRoleBasedActivity(role)
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Failed to fetch user data: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                    } else {
                        Toast.makeText(this, task.exception?.message ?: "Sign-in failed", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.currentUser?.uid?.let { uid ->
            firestore.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    val role = document.getString("role")
                    if (role != null) {
                        navigateToRoleBasedActivity(role)
                    }
                }
                .addOnFailureListener { e ->
                    // Handle failure
                }
        }
    }

    private fun navigateToRoleBasedActivity(role: String?) {
        val intent = when (role) {
            "admin" -> Intent(this, AdminActivity::class.java)
            "student" -> Intent(this, CourseActivity::class.java)
            else -> Intent(this, TeacherActivity::class.java)
        }
        startActivity(intent)
        finish() // Optional: Finish SignInActivity to prevent going back
    }
}
