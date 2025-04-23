package com.mobile.jobsearchapplication.ui.features.profile

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.data.model.user.UpdateInfoUser
import com.mobile.jobsearchapplication.data.repository.user.UserRepository
import com.mobile.jobsearchapplication.ui.base.BaseViewModel
import com.mobile.jobsearchapplication.ui.components.textField.account.TextFieldModel
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.getLoggedInUserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

data class InfoProfileState(
    val fullName: String = "",
    val phoneNumber: String = "",
    val avatar: String = "",
    val bio: String = "",
    val birthDay: String = "",
    val gender: String = "",
    val cvUrl: String = "",
)

data class ProfileState(
    var isModeEditor: Boolean = false,
    val isUpdateInfoSuccess: Boolean = false,
)

class ProfileViewModel: BaseViewModel() {
    private val userRepository = UserRepository()

    private val _infoProfileState = MutableStateFlow(InfoProfileState())
    val infoProfileState = _infoProfileState.asStateFlow()

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()

    fun onIconEdit() {
        _profileState.value = _profileState.value.copy(
            isModeEditor = !_profileState.value.isModeEditor,
            isUpdateInfoSuccess = false
        )

        if (_profileState.value.isModeEditor) {
            makeBlankValueInfo()
        } else {
            getInfoApi()
        }
    }

    // -----------Info State--------------
    fun getInfoApi() {
        CoroutineScope(Dispatchers.IO).launch {
            val uuid = getLoggedInUserId()
            val response = userRepository.getInfo(uuid)
            if (response.isSuccess) {
                _infoProfileState.value = _infoProfileState.value.copy(
                    avatar = (response.data?.avatar) ?: "Chưa cập nhật thông tin",
                    fullName = (if(response.data?.fullName!!.isNotBlank()) response.data.fullName.toString() else{ "Chưa cập nhật thông tin" }).toString(),
                    bio = (if(response.data.bio!!.isNotBlank()) response.data.bio.toString() else{ "Chưa cập nhật thông tin" }).toString(),
                    gender = (if(response.data.gender!!.isNotBlank()) response.data.gender.toString() else{ "Chưa cập nhật thông tin" }).toString(),
                    birthDay = (if(response.data.birthDay!!.isNotBlank()) response.data.birthDay.toString() else{ "Chưa cập nhật thông tin" }).toString(),
                    phoneNumber = (if(response.data.phoneNumber!!.isNotBlank()) response.data.phoneNumber.toString() else{ "Chưa cập nhật thông tin" }).toString(),
                    cvUrl = (if(response.data.cvUrl!!.isNotBlank()) response.data.cvUrl.toString() else{ "Chưa cập nhật thông tin" }).toString(),
                )
            }
        }
    }

    fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
        return try {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun updateImageProfile(context: Context, uri: Uri) {
        viewModelScope.launch {
            try {
                // Lưu file từ Uri vào bộ nhớ tạm
                val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg" // Mặc định là JPEG nếu không xác định được
                val fileExtension = when (mimeType) {
                    "image/png" -> "png"
                    else -> "jpg"
                }
                val file = File(context.cacheDir, "temp_avatar.$fileExtension")
                context.contentResolver.openInputStream(uri)?.use { input ->
                    FileOutputStream(file).use { output ->
                        val bytesCopied = input.copyTo(output)
                        println("Bytes copied: $bytesCopied") // Debug log
                    }
                }

                if (!file.exists() || file.length() == 0L) {
                    Toast.makeText(context, "File trống hoặc không tồn tại", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
                val avatarPart = MultipartBody.Part.createFormData("avatar", file.name, requestFile)

                // Gọi API qua repository
                val response = userRepository.updateImage(
                    uuid = getLoggedInUserId(),
                    avatar = avatarPart,
                    cv = null // Rõ ràng truyền cv = null
                )
                if (response.isSuccess) {
                    val newAvatarUrl = response.data?.avatar ?: ""
                    onChangeValueInfo(newAvatarUrl, "avatar") // Cập nhật avatar mới
                } else {
                    Toast.makeText(context, "Error1: ${response.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // --------------------- Text Field Update Profile ---------------------------
    val bioItem = TextFieldModel(
        onValueChange = { onChangeValueInfo(it, "bio") },
        label = "Miêu tả về bản thân",
        messageError = "Tối đa 250 ký tự"
    )
    val fullNameItem = TextFieldModel(
        onValueChange = { onChangeValueInfo(it, "fullName") },
        label = "Họ và tên",
        messageError = "Tối đa 50 ký tự"
    )
    val phoneNumberItem = TextFieldModel(
        onValueChange = { onChangeValueInfo(it, "phoneNumber") },
        label = "Số điện thoại",
        messageError = "Số điện thoại không hợp lệ"
    )
    val genderItem = TextFieldModel(
        onValueChange = { onChangeValueInfo(it, "gender") },
        label = "Giới tính"
    )

    val listItemsUpdate = listOf(
        bioItem,
        fullNameItem,
        phoneNumberItem
    )

    private fun makeBlankValueInfo() {
        _infoProfileState.value = _infoProfileState.value.copy(
            bio = "",
            fullName = "",
            gender = "",
            phoneNumber = "",
            cvUrl = "",
        )
    }

    fun onChangeValueInfo(value: String, field: String) {
        _infoProfileState.value = when (field) {
            "bio" -> _infoProfileState.value.copy(bio = value)
            "fullName" -> _infoProfileState.value.copy(fullName = value)
            "phoneNumber" -> _infoProfileState.value.copy(phoneNumber = value)
            "birthDay" -> _infoProfileState.value.copy(birthDay = value)
            "gender" -> _infoProfileState.value.copy(gender = value)
            "cvUrl" -> _infoProfileState.value.copy(cvUrl = value)
            else -> _infoProfileState.value
        }
    }

    fun updateInfoApi() {
        viewModelScope.launch {
            val uuid = getLoggedInUserId()
            val request = UpdateInfoUser(
                bio = _infoProfileState.value.bio,
                fullName = _infoProfileState.value.fullName,
                phoneNumber = _infoProfileState.value.phoneNumber,
                birthDay = _infoProfileState.value.birthDay,
                gender = _infoProfileState.value.gender,
            )
            val response = withContext(Dispatchers.IO) { userRepository.updateInfo(uuid, request) }
            if (response.isSuccess) {
                _profileState.value = _profileState.value.copy(
                    isUpdateInfoSuccess = true
                )
            } else {
                _profileState.value = _profileState.value.copy(
                    isUpdateInfoSuccess = false
                )
            }
        }
    }
}