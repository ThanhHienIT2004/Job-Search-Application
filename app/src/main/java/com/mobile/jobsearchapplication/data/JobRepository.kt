//package com.mobile.jobsearchapplication.data
//
//import android.content.Context
//import com.mobile.jobsearchapplication.model.Job
//import java.sql.Connection
//import java.sql.DriverManager
//import java.sql.ResultSet
//
//class JobRepository(context: Context) {
//
//    // Kết nối SQL Server hoặc SQLite
//    private val connection: Connection? = try {
//        val url = "jdbc:mysql://<YOUR_DB_HOST>:<PORT>/<DATABASE_NAME>"
//        val user = "<USERNAME>"
//        val password = "<PASSWORD>"
//        DriverManager.getConnection(url, user, password)
//    } catch (e: Exception) {
//        e.printStackTrace()
//        null
//    }
//
//    // Lấy danh sách công việc từ SQL
//    fun getJobs(): List<Job> {
//        val jobs = mutableListOf<Job>()
//        val query = "SELECT * FROM jobs"
//        try {
//            connection?.createStatement()?.use { statement ->
//                val resultSet: ResultSet = statement.executeQuery(query)
//                while (resultSet.next()) {
//                    jobs.add(
//                        Job(
//                            id = resultSet.getInt("id"),
//                            title = resultSet.getString("title"),
//                            location = resultSet.getString("location"),
//                            type = resultSet.getString("type")
//                        )
//                    )
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return jobs
//    }
//
//    // Lọc công việc theo tiêu chí tìm kiếm
//    fun searchJobs(searchText: String): List<Job> {
//        return getJobs().filter {
//            it.title.contains(searchText, ignoreCase = true) ||
//                    it.location.contains(searchText, ignoreCase = true) ||
//                    it.type.contains(searchText, ignoreCase = true)
//        }
//    }
//}
