package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.screens.components.BackButton

@Composable
fun SearchScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }
    val recentSearches = listOf(
        "Cộng Đồng Albion Online Việt Nam",
        "Kiệt Tuấn",
        "BT Thu Anh",
        "Linh Xinh",
        "BrilliantCrypto Việt Nam",
        "Cộng Đồng Google Ads Việt Nam",
        "kim soo hyun",
        "Cộng đồng Auto C# Việt Nam - Ktea",
        "chế độ choncc",
        "Cộng đồng Automation Browser",
        "Ngọc Trinh",
        "tai nạn của đàm vĩnh hưng",
        "BLACK MMO",
        "câu cá albion online"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Row(){
            BackButton(navController)
                // Thanh tìm kiếm
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("Tìm kiếm", fontSize = 16.sp) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(40.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFF2E2E2E),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Gray
                    )
                )
            }
        }



        Text(
            text = "Mới đây",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(recentSearches.filter { it.contains(searchText, ignoreCase = true) }) { item ->
                SearchItem(item) {
                    searchText = it
                }
            }
        }
    }


@Composable
fun SearchItem(title: String, onSelect: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect(title) }
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar hoặc Icon (nếu không có hình thì dùng Icon mặc định)
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Thay bằng hình thực tế trong drawable
            contentDescription = "Avatar",
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                color = Color.White
            )
            Text(
                text = "9+ thông tin mới", // Nội dung phụ
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.baseline_delete_24), // Dấu ba chấm
            contentDescription = "More",
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
    }
}
