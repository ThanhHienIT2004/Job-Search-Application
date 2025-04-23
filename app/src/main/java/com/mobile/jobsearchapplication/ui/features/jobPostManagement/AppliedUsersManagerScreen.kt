package com.mobile.jobsearchapplication.ui.features.jobPostManagement

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivities
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mobile.jobsearchapplication.data.model.jobapplication.AppliedUserWithApplication
import com.mobile.jobsearchapplication.ui.features.jobDetail.toVietnameseGender1

@Composable
fun AppliedUsersManagerScreen(
    jPMViewModel: JobPostManagementViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val uiState = jPMViewModel.uiState.collectAsState()
    val appliedUsers = (uiState.value as JobPostManagementUiState.Success).appliedUsers
    Column {
        appliedUsers.forEach { appliedUser ->
            AppliedUserItem(navController, jPMViewModel, appliedUser)
        }
    }
}

@Composable
fun AppliedUserItem(
    navController: NavController,
    jPMViewModel: JobPostManagementViewModel,
    appliedUser: AppliedUserWithApplication,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    ElevatedCard(
        modifier = modifier.height(150.dp).padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "http://192.168.1.35:8080/${appliedUser.avatar}",
                contentDescription = "Avatar",
                modifier = Modifier.weight(1.5f).padding(12.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            // Thong tin co ban
            Column(
                modifier = Modifier.weight(3f).padding(4.dp)
            ) {
                Text(
                    text = "Họ tên: ${appliedUser.userId}",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = "Ngày sinh: ${appliedUser.birthDay}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Giới tính: ${appliedUser.gender.toString().toVietnameseGender1()}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(0.4f).padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(Color(0xFFF5F4F4)),
                    elevation = CardDefaults.cardElevation(2.dp),
                ) {
                    Text(
                        text = "Link CV",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF4A58AF)
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                            .clickable {
                                val cvUrl = "http://192.168.1.35:8080/${appliedUser.applicationCvUrl}"
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    setData(Uri.parse(cvUrl))
                                }
                                startActivities(context, arrayOf(Intent.createChooser(intent, "Mở CV bằng")), null)
                            }
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f).fillMaxHeight().padding(4.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (appliedUser.applicationStatus == "PENDING") {
                    ButtonAppliedUser(jPMViewModel, appliedUser)
                }
            }
        }
    }
}

@Composable
fun ButtonAppliedUser(
    jPMViewModel: JobPostManagementViewModel,
    appliedUser: AppliedUserWithApplication
) {
    val context = LocalContext.current
    FloatingActionButton(
        onClick = { jPMViewModel.updateStatusAppliedJob(
            userId = appliedUser.userId,
            jobId =  appliedUser.jobId,
            status = "Accepted",
            context = context
        ) },
        modifier = Modifier.size(50.dp, 40.dp),
        shape = RoundedCornerShape(8.dp),
        containerColor = Color(0xFF43A047),
        contentColor = Color.Black
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Add Icon Button"
        )
    }

    FloatingActionButton(
        onClick = { jPMViewModel.updateStatusAppliedJob(
            userId = appliedUser.userId,
            jobId =  appliedUser.jobId,
            status = "Declined",
            context = context
        ) },
        modifier = Modifier.size(50.dp, 40.dp),
        shape = RoundedCornerShape(8.dp),
        containerColor = Color(0xFFE53935),
        contentColor = Color.Black
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close Icon Button"
        )
    }

}


