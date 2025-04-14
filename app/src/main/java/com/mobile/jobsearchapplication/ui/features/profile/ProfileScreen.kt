package com.mobile.jobsearchapplication.ui.features.profile

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.icon.IconUpdateProfile
import com.mobile.jobsearchapplication.ui.components.textField.auth.TextFieldCustom
import com.mobile.jobsearchapplication.ui.components.topBar.BackButton
import com.mobile.jobsearchapplication.ui.components.topBar.TitleTopBar
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.getCurrentUserEmail

@Composable
fun ProfileScreen(navController: NavController) {
    val profileVM: ProfileViewModel = viewModel()
    val profileState by profileVM.profileState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(profileState.isUpdateInfoSuccess) {
        if (profileState.isModeEditor && profileState.isUpdateInfoSuccess) {
            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
            profileVM.onIconEdit()
        }
    }

    BaseScreen(
        actionsTop = {
            BackButton(navController)
            TitleTopBar(text = "Thông tin cá nhân")
        }
    ) { padding ->
        LazyColumn (
            modifier = Modifier.fillMaxSize()
                .background(Color(0xFFF8F8FC))
                .padding(padding)
        ) {
            item {
                TopProfileScreen(
                    profileVM,
                )

                BottomProfileScreen(
                    profileVM
                )
            }
        }
    }
}

@Composable
fun TopProfileScreen(
    profileVM: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ic_avatar),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(130.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .border(3.dp, Color.Gray, CircleShape)
                    .shadow(8.dp, CircleShape)
            )

            Text(
                text = getCurrentUserEmail(),
                fontSize = 24.sp, fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = "bio hghj",
                fontSize = 16.sp, fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        IconUpdateProfile(
            { profileVM.onIconEdit() },
            Modifier.align(Alignment.TopEnd)
        )

    }
}


@Composable
fun BottomProfileScreen(
    profileVM: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    val profileState by profileVM.profileState.collectAsState()

    Column (
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFCFCFF), RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
            .shadow(2.dp, RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!profileState.isModeEditor) {
            SectionInfoProfile(profileVM)
        } else {
            SectionUpdatedProfile(profileVM)
        }
    }
}

@Composable
fun SectionInfoProfile(
    profileVM: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    val infoProfileState by profileVM.infoProfileState.collectAsState()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        InfoProfileItem("Họ và tên", infoProfileState.fullName)
        InfoProfileItem("Ngày sinh", infoProfileState.birthDay)
        InfoProfileItem("Giới tính", infoProfileState.gender)
        InfoProfileItem("Địa chỉ", infoProfileState.location)
        InfoProfileItem("Số điện thoại", infoProfileState.phoneNumber)
        InfoImageProfileItem(profileVM,"CV", infoProfileState.cvUrl, R.drawable.ic_avatar)
        InfoProfileItem("Học vấn", infoProfileState.education)
        InfoProfileItem("Kinh nghiệm", infoProfileState.experience)
    }
}

@Composable
fun InfoProfileItem(title: String, value: String) {
    Row(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "$title: ",
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
        Text(
            text = value,
            modifier = Modifier.weight(2f),
            fontSize = 14.sp,fontWeight = FontWeight.Bold,
            color = Color(0xB3131313),
        )
    }
}

@Composable
fun InfoImageProfileItem(
    profileVM: ProfileViewModel,
    title: String,
    value: String,
    painter: Int,
) {
    val profileState by profileVM.profileState.collectAsState()

    Row(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "$title: ",
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
        Row(
            modifier = Modifier.weight(2f)
                .clickable {
                    profileVM.onZoomImage()
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = value,
                fontSize = 14.sp,fontWeight = FontWeight.Bold,
                color = Color(0xB3131313),
            )
            Icon(
                imageVector = Icons.Filled.Link,
                contentDescription = "Icon Link"
            )
        }
    }

    if (profileState.isZoomImage) {
        Icon(
            painter = painterResource(painter),
            contentDescription = "Info Image",
            modifier = Modifier.fillMaxSize(0.6f)
//                .offset(y = (-500).dp)
                .zIndex(1f),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun SectionUpdatedProfile(
    profileVM: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    val infoProfileState by profileVM.infoProfileState.collectAsState()
    profileVM.fullNameItem.value = infoProfileState.fullName
    profileVM.phoneNumberItem.value = infoProfileState.phoneNumber
    profileVM.avatarItem.value = infoProfileState.avatar
    profileVM.bioItem.value = infoProfileState.bio
    profileVM.birthDayItem.value = infoProfileState.birthDay
    profileVM.genderItem.value = infoProfileState.gender
    profileVM.locationItem.value = infoProfileState.location
    profileVM.cvUrlItem.value = infoProfileState.cvUrl
    profileVM.educationItem.value = infoProfileState.education
    profileVM.experienceItem.value = infoProfileState.experience

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        profileVM.listItemsUpdate.forEach {
            TextFieldCustom(model = it)
        }

        Button(
            onClick = { profileVM.updateInfoApi() },
            modifier = Modifier.padding(0.dp, 10.dp)
                .fillMaxWidth(0.5f)
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(2.dp, Color.Gray),

            ) {
            Text(
                "Cập nhật",
                fontSize = 20.sp, color = Color.Black
            )
        }
    }

}

