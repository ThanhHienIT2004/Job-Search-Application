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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.emptyState.EmptyState
import com.mobile.jobsearchapplication.ui.components.textField.account.TextFieldCustom
import com.mobile.jobsearchapplication.ui.components.textField.account.TextFieldDatePicker
import com.mobile.jobsearchapplication.ui.components.textField.account.TextFieldMenu
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
            BackButton(navController, "menu_screen")
            TitleTopBar(text = "Thông tin cá nhân")
            Spacer(Modifier.weight(1f))
            IconUpdateProfile(
                isEditing = profileState.isModeEditor,
                onEditClick = { profileVM.onIconEdit() },
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
        ) {
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

@Composable
fun TopProfileScreen(
    profileVM: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    val profileState by profileVM.profileState.collectAsState()
    val infoProfileState by profileVM.infoProfileState.collectAsState()

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(10.dp),
    ) {
        Column (
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .then(
                        if (profileState.isModeEditor) Modifier.size(140.dp)
                        else Modifier.size(120.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = "http://172.19.16.1:8080/${infoProfileState.avatar.takeIf { it.isNotBlank() }}",
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(120.dp)
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .border(1.dp, Color.Black, CircleShape),
                    contentScale = ContentScale.Crop
                )
                if (profileState.isModeEditor) {
                    GetAvatarOnAlbum(profileVM, Modifier.align(Alignment.BottomEnd))
                }
            }

            Text(
                text = getCurrentUserEmail(),
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                modifier = Modifier.padding(top = 20.dp)
            )
            Text(
                text = infoProfileState.bio,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xB3101010),
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }


}

@Composable
fun TabsMenuProfile(
    profileVM: ProfileViewModel
) {
    val profileState by profileVM.profileState.collectAsState()
    val tabs = listOf("Hồ sơ", "CV")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.White,
                contentColor = Color.Black,
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title, style = MaterialTheme.typography.titleMedium) }
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
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
        )
        Text(
            text = value,
            modifier = Modifier.weight(2f),
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF0E1010),
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
            profileVM.onChangeValueInfo(it.toString(), "cvUrl")
            profileVM.updateInfoApi()
        }
    }

    // Launcher để yêu cầu permission
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            pickImageLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Quyền truy cập ảnh bị từ chối", Toast.LENGTH_SHORT).show()
        }
    }

    val bitmap = remember(infoProfileState.cvUrl) {
        if (infoProfileState.cvUrl.isNotBlank() && infoProfileState.cvUrl.startsWith("content://")) {
            profileVM.loadBitmapFromUri(context, Uri.parse(infoProfileState.cvUrl))
        } else {
            "aaaaa"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF1F1FD)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Ảnh CV
        AsyncImage(
            model = tempAvatarUri ?: infoProfileState.cvUrl.takeIf { it.isNotBlank() }
                    ?: "https://assets.grok.com/users/8892a3bf-ddc4-4dd0-bd6a-ffcc87a4b000/generated/aH1uo7DGtHaJOs5k/image.jpg",
            contentDescription = "Avatar",
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
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

    profileVM.bioItem.value = infoProfileState.bio
    profileVM.bioItem.isError = infoProfileState.bio.length > 250

    profileVM.fullNameItem.value = infoProfileState.fullName
    profileVM.fullNameItem.isError = infoProfileState.fullName.length > 50

    profileVM.genderItem.value = infoProfileState.gender

    profileVM.phoneNumberItem.value = infoProfileState.phoneNumber
    profileVM.phoneNumberItem.isError = infoProfileState.phoneNumber.length > 11

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        profileVM.listItemsUpdate.forEach {
            TextFieldCustom(model = it, isNumeric = it.label == "Số điện thoại")
        }

        TextFieldMenu(model = profileVM.genderItem, modifier = Modifier.padding(bottom = 16.dp))

        TextFieldDatePicker(
            value = infoProfileState.birthDay,
            onDateSelected = { profileVM.onChangeValueInfo(it, "birthDay") },
        )

        Button(
            onClick = { profileVM.updateInfoApi() },
            modifier = Modifier
                .padding(0.dp, 20.dp)
                .fillMaxWidth(0.5f)
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(2.dp, Color.Gray),

            ) {
            Text(
                "Cập nhật",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
            )
        }
    }
}

@Composable
fun GetAvatarOnAlbum(
    profileVM: ProfileViewModel,
    modifier: Modifier = Modifier
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
            profileVM.updateImageProfile(context, it) // Đổi từ updateCv thành updateAvatar
        }
    }

    // Launcher để yêu cầu permission
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            pickImageLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Quyền truy cập ảnh bị từ chối", Toast.LENGTH_SHORT).show()
        }
    }

    AsyncImage(
        model = if (tempAvatarUri != null) {
            tempAvatarUri
        } else if (infoProfileState.avatar.isNotBlank()) {
            "http://172.19.16.1:8080/${infoProfileState.avatar}"
        } else {
            null
        },
        contentDescription = "Avatar",
        modifier = Modifier
            .size(120.dp)
            .aspectRatio(1f)
            .clip(CircleShape)
            .border(3.dp, Color.Black, CircleShape),
        placeholder = painterResource(id = R.drawable.ic_avatar),
        error = painterResource(id = R.drawable.error),
        contentScale = ContentScale.Crop
    )
    FloatingActionButton(
        onClick = {
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
        modifier = modifier.size(32.dp),
        containerColor = Color.White,
        elevation = FloatingActionButtonDefaults.elevation(2.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ModeEdit,
            contentDescription = "Edit",
            tint = Color(0xFF1976D2)
        )
    }
}

