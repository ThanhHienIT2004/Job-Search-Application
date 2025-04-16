package com.mobile.jobsearchapplication.ui.features.profile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.data.model.user.UpdateInfoUser
import com.mobile.jobsearchapplication.data.repository.user.UserRepository
import com.mobile.jobsearchapplication.ui.base.BaseViewModel
import com.mobile.jobsearchapplication.ui.components.textField.auth.TextFieldModel
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.getLoggedInUserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    val isZoomImage: Boolean = false
)

class ProfileViewModel: BaseViewModel() {
    private val userRepository = UserRepository()

    private val _infoProfileState = MutableStateFlow(InfoProfileState())
    val infoProfileState = _infoProfileState.asStateFlow()

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState = _profileState.asStateFlow()

    init {
        getInfoApi()
    }

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

    fun onZoomImage() {
        _profileState.value = profileState.value.copy(
            isZoomImage = !_profileState.value.isZoomImage
        )
    }

    // -----------Info State--------------
    private fun getInfoApi() {
        CoroutineScope(Dispatchers.Main).launch {
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

    // --------------------- Text Field Update Profile ---------------------------
    val fullNameItem = TextFieldModel(
        onValueChange = { onChangeValueInfo(it, "fullName") },
        label = "Họ và tên"
    )
    val phoneNumberItem = TextFieldModel(
        onValueChange = { onChangeValueInfo(it, "phoneNumber") },
        label = "Số điện thoại"
    )
    val genderItem = TextFieldModel(
        onValueChange = { onChangeValueInfo(it, "gender") },
        label = "Giới tính"
    )

    val listItemsUpdate = listOf(
        fullNameItem,
        genderItem,
        phoneNumberItem
    )

    private fun makeBlankValueInfo() {
        _infoProfileState.value = _infoProfileState.value.copy(
            fullName = "",
            gender = "",
//            birthDay = "",
            phoneNumber = "",
            cvUrl = "",
        )
    }

    fun onChangeValueInfo(value: String, field: String) {
        _infoProfileState.value = when (field) {
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
                avatar = "",
                bio = "",
                fullName = _infoProfileState.value.fullName,
                phoneNumber = _infoProfileState.value.phoneNumber,
                birthDay = _infoProfileState.value.birthDay,
                gender = _infoProfileState.value.gender,
                cvUrl = _infoProfileState.value.cvUrl,
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