package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.components.BackButton

@Composable
fun SearchScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }
    val recentSearches = listOf(
        "Cộng Đồng Albion Online Việt Nam", "Kiệt Tuấn", "BT Thu Anh", "Linh Xinh",
        "BrilliantCrypto Việt Nam", "Cộng Đồng Google Ads Việt Nam", "kim soo hyun",
        "Cộng đồng Auto C# Việt Nam - Ktea", "chế độ choncc", "Cộng đồng Automation Browser",
        "Ngọc Trinh", "tai nạn của đàm vĩnh hưng", "BLACK MMO", "câu cá albion online"
    )

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .windowInsetsPadding(WindowInsets.statusBars),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            // Thanh tìm kiếm
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackButton(navController)
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("Tìm kiếm...", fontSize = 16.sp, color = Color.Gray) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Blue
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    )
                )
            }
        }

        item {
            Text(
                text = "Tìm kiếm gần đây",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }

        items(recentSearches.filter { it.contains(searchText, ignoreCase = true) }) { item ->
            SearchItem(item) { searchText = it }
        }
    }
}

@Composable
fun SearchItem(title: String, onSelect: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect(title) }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(22.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "9+ thông tin mới",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.baseline_delete_24),
            contentDescription = "Delete",
            tint = Color.Gray,
            modifier = Modifier
                .size(24.dp)
                .clickable { /* Xóa mục này */ }
        )
    }
    Divider(
        color = Color.LightGray,
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen() {
    val fakeNavController = rememberNavController()
    SearchScreen(navController = fakeNavController)
}
