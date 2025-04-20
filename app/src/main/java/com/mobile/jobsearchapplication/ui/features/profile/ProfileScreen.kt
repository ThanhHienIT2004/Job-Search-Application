package com.mobile.jobsearchapplication.ui.features.profile

import IconUpdateProfile
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.emptyState.EmptyState
import com.mobile.jobsearchapplication.ui.components.textField.auth.TextFieldCustom
import com.mobile.jobsearchapplication.ui.components.textField.auth.TextFieldDatePicker
import com.mobile.jobsearchapplication.ui.components.topBar.BackButton
import com.mobile.jobsearchapplication.ui.components.topBar.TitleTopBar
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.getCurrentUserEmail
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.isUserLoggedIn

@Composable
fun ProfileScreen(
    navController: NavController,
    profileVM: ProfileViewModel = viewModel()
) {
    val profileState by profileVM.profileState.collectAsState()
    val context = LocalContext.current

    val queryScreen = navController.currentBackStackEntryAsState().value?.destination?.route.toString()
    LaunchedEffect(queryScreen) {
        if (queryScreen == "update_profile_screen") {
            profileVM.onIconEdit()
        } else {
            if (isUserLoggedIn()) profileVM.getInfoApi()
        }
    }

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
                .padding(padding),
        ) {
            item {
                if (!isUserLoggedIn()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        EmptyState(
                            icon = R.drawable.img_go_log_in,
                            message = "Hãy đăng nhập để sử dụng chức năng này",
                            onClick = { navController.navigate("auth_screen") }
                        )
                    }
                } else {
                    TopProfileScreen(profileVM)
                    TabsMenuProfile(profileVM)
                }
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
            .padding(top = 16.dp, bottom = 20.dp)
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
                color = Color(0xFF323333),
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = "bio hghj",
                fontSize = 16.sp, fontWeight = FontWeight.Bold,
                color = Color(0xFF323333),
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
fun TabsMenuProfile(
    profileVM: ProfileViewModel
) {
    val profileState by profileVM.profileState.collectAsState()
    val tabs = listOf("Hồ sơ", "CV")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title, fontSize = 16.sp, color = Color.Gray) }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> if (!profileState.isModeEditor)
                    SectionInfoProfile(profileVM)
                else SectionUpdatedProfile(profileVM)
            1 -> SectionCVProfile(profileVM)
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
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        InfoProfileItem("Họ và tên", infoProfileState.fullName)
        InfoProfileItem("Giới tính", infoProfileState.gender)
        InfoProfileItem("Ngày sinh", infoProfileState.birthDay)
        InfoProfileItem("Số điện thoại", infoProfileState.phoneNumber)
    }
}

@Composable
fun InfoProfileItem(title: String, value: String) {
    Row(
        modifier = Modifier.padding(16.dp, 24.dp)
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
fun SectionCVProfile(
    profileVM: ProfileViewModel
) {
    val profileState by profileVM.profileState.collectAsState()
    val infoProfileState by profileVM.infoProfileState.collectAsState()
    val context = LocalContext.current

    // State để lưu Uri ảnh tạm thời (trước khi upload)
    var tempAvatarUri by remember { mutableStateOf<Uri?>(null) }
    // Launcher để chọn ảnh
    val pickImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            tempAvatarUri = it
            // Cập nhật avatar tạm thời trong ViewModel
            profileVM.onChangeValueInfo(it.toString(), "cvUrl")
            // Upload ảnh (hoặc xử lý local)
            profileVM.updateInfoApi()
        }
    }

    // Launcher để yêu cầu permission
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Mở album
            pickImageLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Quyền truy cập ảnh bị từ chối", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (profileState.isModeEditor) {
            Text(
                text = "Chỉnh sửa CV",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Ảnh CV
        AsyncImage(
            model = tempAvatarUri ?: infoProfileState.cvUrl.takeIf { it.isNotBlank() }
            ?: "https://th.bing.com/th/id/OIP.dhFjdSsJIvsp5WrEsGfVQQHaKe?w=1414&h=2000&rs=1&pid=ImgDetMain",
            contentDescription = "Avatar",
            modifier = Modifier.fillMaxSize()
                .clickable {
                    if (profileState.isModeEditor) {
                        val permission =
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                Manifest.permission.READ_MEDIA_IMAGES
                            } else {
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            }
                        if (ContextCompat.checkSelfPermission(
                                context,
                                permission
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            pickImageLauncher.launch("image/*")
                        } else {
                            requestPermissionLauncher.launch(permission)
                        }
                    }
                },
            contentScale = ContentScale.Crop
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
    profileVM.genderItem.value = infoProfileState.gender
    profileVM.phoneNumberItem.value = infoProfileState.phoneNumber

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        profileVM.listItemsUpdate.forEach {
            TextFieldCustom(model = it, isNumeric = it.label == "Số điện thoại")
        }

        TextFieldDatePicker(
            value = infoProfileState.birthDay,
            onDateSelected = { profileVM.onChangeValueInfo(it, "birthDay") },
        )

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

