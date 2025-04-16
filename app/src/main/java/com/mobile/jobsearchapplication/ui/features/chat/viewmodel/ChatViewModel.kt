package com.mobile.jobsearchapplication.ui.features.chat.viewmodel//package com.mobile.jobsearchapplication.ui.features.chat.viewmodel
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.viewModelScope
//import io.getstream.chat.android.client.ChatClient
//import io.getstream.chat.android.models.User
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class ChatViewModel(
//    private val app: Application,
//    private val client: ChatClient  // Truyền ChatClient vào từ Application hoặc DI
//) : AndroidViewModel(app) {
//
//    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
//    val connectionState: StateFlow<ConnectionState> = _connectionState
//
//    sealed class ConnectionState {
//        object Disconnected : ConnectionState()
//        object Connecting : ConnectionState()
//        object Connected : ConnectionState()
//        data class Failed(val error: Throwable) : ConnectionState()
//    }
//
//    init {
//        connectUser()
//    }
//
//    private fun connectUser() {
//        viewModelScope.launch {
//            _connectionState.value = ConnectionState.Connecting
//            try {
//                //  Lấy thông tin người dùng và token từ nguồn dữ liệu của bạn
//                //  (ví dụ: SharedPreferences, database, hoặc backend)
//                val userId = "user_id_from_your_auth" // Thay bằng ID người dùng thực tế
//                val userName = "User Name" // Thay bằng tên người dùng thực tế
//                val userImage = "URL_to_user_image" // Thay bằng URL ảnh người dùng (nếu có)
//                val userToken = "user_token_from_your_backend" // Thay bằng token thực tế
//
//                val user = User(
//                    id = userId,
//                    extraData = mutableMapOf(
//                        "name" to userName,
//                        "image" to userImage
//                    )
//                )
//
//                val result = client.connectUser(
//                    user = user,
//                    token = userToken,
//                ).await()  // Sử dụng await() để đợi kết quả
//
//                if (result.isSuccess) {
//                    _connectionState.value = ConnectionState.Connected
//                } else {
//                    _connectionState.value = ConnectionState.Failed(result.errorOrNull() ?: Exception("Connection failed"))
//                }
//            } catch (e: Exception) {
//                _connectionState.value = ConnectionState.Failed(e)
//            }
//        }
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        if (connectionState.value == ConnectionState.Connected) {
//            client.disconnect()
//        }
//    }
//}