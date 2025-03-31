package com.mobile.jobsearchapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ModeComment
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mobile.jobsearchapplication.R

sealed class Button(val icon: ImageVector, val text: String) {
    object Favorite : Button(Icons.Filled.Favorite, "9.2k")
    object Share : Button(Icons.Filled.Share, "Chia se")
    object Comment : Button(Icons.Filled.ModeComment, "Binh luan")
}

val buttonPostItems = listOf(Button.Favorite, Button.Share, Button.Comment)

@Composable
fun PostItemList(navController: NavController, jobTitle: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(9f)
            ) {
                // info user
                UserInfoInPost(jobTitle)

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Content
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .padding(horizontal = 4.dp, vertical = 6.dp)
                            .clickable { navController.navigate("job_detail_screen/${jobTitle}") },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        val paperState = rememberPagerState(pageCount = { 2 })
                        HorizontalPager(
                            state = paperState,
                            modifier = Modifier.fillMaxWidth()
                        ) { page ->
                            Column (
                                modifier = Modifier
                                    .padding(8.dp)
                            ) {
                            ContentPostItem(page)
                            }
                        }
                    }
                }
            }

            // Buttons
            Column (
                modifier = Modifier
                    .weight(1.5f)
            ) {
                ButtonsPost(listsItem = buttonPostItems)
            }
        }
    }
}

// khung avatar va info user
@Composable
fun UserInfoInPost(jobTitle: String) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Image(
            painter = painterResource(R.drawable.ic_image),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(56.dp)
                .weight(2f)
                .clip(CircleShape)
        )

        // Info User
        Column(
            modifier = Modifier
                .weight(8f)
                .padding(5.dp)
        ) {
            Text(
                text = jobTitle,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.AccessTime,
                    contentDescription = "Clock",
                    tint = Color.Gray,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Today • 23 min",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

// Khung content
@Composable
fun ContentPostItem(page: Int) {
    
    // Name job
    Text(
        text = "Tên cong viec",
        fontSize = 14.sp, fontWeight = FontWeight.Bold,
    )

    // Salary
    Text(
        text = "Mức lương: 102039102 - 123812983912",
        fontSize = 14.sp
    )

    // Age
    Text(
        text = "Yeu cau do tuoi",
        fontSize = 14.sp
    )

    // Suggest
    Text(
        text = "Yeu cau bang cap",
        fontSize = 14.sp
    )

    Row() {
        // Quantity
        Text(
            text = "So luong nhan vien: ",
            fontSize = 14.sp
        )
        Spacer(Modifier.weight(1f))
        // Gender
        Text(
            text = "Gioi tinh: ",
            fontSize = 14.sp
        )
    }

    Spacer(Modifier.height(8.dp))
    // phan trang
    Row(
        modifier = Modifier.fillMaxWidth()
            .wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for(i in 1..page) {
            IconButton(
                onClick = {},
                modifier = Modifier.size(8.dp)
            ) {
                Icon(
                    imageVector = if (i == 1) Icons.Filled.CheckCircle else Icons.Default.StopCircle,
                    contentDescription = "",
                    modifier = Modifier.size(8.dp)
                )

            }
        }
    }
}


// nut mo rong, thu nho mo ta
@Composable
fun ExpandableText(
    text: String,
    maxLength: Int = 50,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val displayText = if (expanded || text.length <= maxLength) text else "${text.take(maxLength)}..."

    Column(modifier = modifier) {
        Text(
            text = displayText,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = if (expanded) Int.MAX_VALUE else 2, // Limit to 2 lines when not expanded
            overflow = TextOverflow.Ellipsis
        )
        if (text.length > maxLength) {
            Text(
                text = if (expanded) "Thu gọn" else "Xem thêm",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 2.dp)
                    .clickable { expanded = !expanded }
            )
        }
    }
}

@Composable
fun ButtonsPost(listsItem: List<Button>) {
    var isFavorite by remember { mutableStateOf(false) }
    Spacer(Modifier.height(8.dp))
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        listsItem.forEach { button ->
            IconButton(
                onClick = { if(button.icon == Icons.Filled.Favorite) isFavorite = !isFavorite },
                modifier = Modifier
                    .size(20.dp)
            ) {
                Icon(
                    imageVector = button.icon,
                    contentDescription = button.text,
                    tint = if (isFavorite && button.icon == Icons.Filled.Favorite) Color.Red else Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(button.text, fontSize = 9.sp)
            Spacer(Modifier.height(8.dp))
        }

        Spacer(Modifier.height(22.dp))
        // chatting
        IconButton(
            onClick = {  },
            modifier = Modifier
                .size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = "Chatting",
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        }
        Text("Lien he", fontSize = 9.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun JobListItemPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        PostItemList(navController, jobTitle = "Mobile Developer")
//        UserInfoInPost(jobTitle = "Lapajdas dasdjk")
    }
}
