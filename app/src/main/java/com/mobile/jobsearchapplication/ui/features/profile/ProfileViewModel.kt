package com.mobile.jobsearchapplication.ui.features.profile

import com.mobile.jobsearchapplication.data.model.user.UpdateInfoUser
import com.mobile.jobsearchapplication.data.repository.user.UserRepository
import com.mobile.jobsearchapplication.ui.base.BaseViewModel
import com.mobile.jobsearchapplication.ui.components.textField.auth.TextFieldModel
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.getLoggedInUserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class InfoProfileState(
    val fullName: String = "",
    val phoneNumber: String = "",
    val avatar: String = "",
    val bio: String = "",
    val birthDay: String = "",
    val gender: String = "",
    val location: String = "",
    val cvUrl: String = "",
    val education: String = "",
    val experience: String = ""
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
            isModeEditor = !_profileState.value.isModeEditor
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
                    phoneNumber = (if(response.data.phoneNumber!!.isNotBlank()) response.data.phoneNumber.toString() else{ "Chưa cập nhật thông tin" }).toString(),
                    birthDay = (if(response.data.birthDay!!.isNotBlank()) response.data.birthDay.toString() else{ "Chưa cập nhật thông tin" }).toString(),
                    gender = (if(response.data.gender!!.isNotBlank()) response.data.gender.toString() else{ "Chưa cập nhật thông tin" }).toString(),
                    location = (if(response.data.location!!.isNotBlank()) response.data.location.toString() else{ "Chưa cập nhật thông tin" }).toString(),
                    cvUrl = (if(response.data.cvUrl!!.isNotBlank()) response.data.cvUrl.toString() else{ "Chưa cập nhật thông tin" }).toString(),
                    education = (if(response.data.education!!.isNotBlank()) response.data.education.toString() else{ "Chưa cập nhật thông tin" }).toString(),
                    experience = (if(response.data.experience!!.isNotBlank()) response.data.experience.toString() else{ "Chưa cập nhật thông tin" }).toString()
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
    val avatarItem = TextFieldModel(
        onValueChange = { onChangeValueInfo(it, "avatar") },
        label = "Ảnh đại diện"
    )
    val bioItem = TextFieldModel(
        onValueChange = { onChangeValueInfo(it, "bio") },
        label = "Giới thiệu"
    )
    val birthDayItem = TextFieldModel(
        onValueChange = { onChangeValueInfo(it, "birthDay") },
        label = "Ngày sinh"
    )
    val genderItem = TextFieldModel(
        onValueChange = { onChangeValueInfo(it, "gender") },
        label = "Giới tính"
    )
    val locationItem = TextFieldModel(
        onValueChange = { onChangeValueInfo(it, "location") },
        label = "Địa chỉ"
    )
    val cvUrlItem = TextFieldModel(
        onValueChange = { onChangeValueInfo(it, "cvUrl") },
        label = "CV"
    )
    val educationItem = TextFieldModel(
        onValueChange = { onChangeValueInfo(it, "education") },
        label = "Học vấn"
    )
    val experienceItem = TextFieldModel(
        onValueChange = { onChangeValueInfo(it, "experience") },
        label = "Kinh nghiệm",
        isImeActionDone = true
    )

    val listItemsUpdate = listOf(
        fullNameItem,
        phoneNumberItem,
        birthDayItem,
        genderItem,
        locationItem,
        cvUrlItem,
        educationItem,
        experienceItem
    )

    private fun makeBlankValueInfo() {
        _infoProfileState.value = _infoProfileState.value.copy(
            fullName = "",
            phoneNumber = "",
            avatar = "",
            bio = "",
            birthDay = "",
            gender = "",
            location = "",
            cvUrl = "",
            education = "",
            experience = ""
        )
    }

    private fun onChangeValueInfo(value: String, field: String) {
        _infoProfileState.value = when (field) {
            "fullName" -> _infoProfileState.value.copy(fullName = value)
            "phoneNumber" -> _infoProfileState.value.copy(phoneNumber = value)
            "avatar" -> _infoProfileState.value.copy(avatar = value)
            "bio" -> _infoProfileState.value.copy(bio = value)
            "birthDay" -> _infoProfileState.value.copy(birthDay = value)
            "gender" -> _infoProfileState.value.copy(gender = value)
            "location" -> _infoProfileState.value.copy(location = value)
            "cvUrl" -> _infoProfileState.value.copy(cvUrl = value)
            "education" -> _infoProfileState.value.copy(education = value)
            "experience" -> _infoProfileState.value.copy(experience = value)
            else -> _infoProfileState.value
        }
    }

    fun updateInfoApi() {
        CoroutineScope(Dispatchers.Main).launch {
            val uuid = getLoggedInUserId()
            val request = UpdateInfoUser(
                _infoProfileState.value.fullName,
                _infoProfileState.value.phoneNumber,
                _infoProfileState.value.avatar,
                _infoProfileState.value.bio,
                _infoProfileState.value.birthDay,
                _infoProfileState.value.gender,
                _infoProfileState.value.location,
                _infoProfileState.value.cvUrl,
                _infoProfileState.value.education,
                _infoProfileState.value.experience
            )
            val response = userRepository.updateInfo(uuid, request)
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