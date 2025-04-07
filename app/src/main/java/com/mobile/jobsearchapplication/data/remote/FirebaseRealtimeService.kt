package com.mobile.jobsearchapplication.data.remote

import com.google.firebase.database.FirebaseDatabase
import com.mobile.jobsearchapplication.ui.screens.Job

// --- Firebase Service ---
class FirebaseRealtimeService {
    private val database = FirebaseDatabase.getInstance().reference

    // Phương thức để thêm người dùng vào Firebase
    fun addUser(user: com.mobile.jobsearchapplication.ui.screens.User, onResult: (Boolean) -> Unit) {
        val userRef = database.child("users").child(user.id)
        userRef.setValue(user)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    // Phương thức để lấy danh sách các công việc (Job)
    fun getJobs(onResult: (List<Job>) -> Unit) {
        val jobsRef = database.child("jobs")
        jobsRef.get().addOnSuccessListener { snapshot ->
            val jobs = mutableListOf<Job>()
            for (jobSnapshot in snapshot.children) {
                val job = jobSnapshot.getValue(Job::class.java)
                job?.let { jobs.add(it) }
            }
            onResult(jobs)
        }.addOnFailureListener {
            // Xử lý lỗi khi lấy dữ liệu
        }
    }
}
