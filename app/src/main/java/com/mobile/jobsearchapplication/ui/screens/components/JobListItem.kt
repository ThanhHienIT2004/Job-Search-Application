package com.mobile.jobsearchapplication.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun JobListItem(navController: NavController, jobTitle: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { navController.navigate("job_detail_screen/${jobTitle}") },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image Placeholder
            Surface(
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.CenterVertically),
                shape = RoundedCornerShape(8.dp),
                color = Color.LightGray
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = jobTitle.first().uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = jobTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

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

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Lương: 1,000,000 - 12,000,000",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Limited Description
                ExpandableText(
                    text = "Mô tả công việc ngắnádasdasdasdasdjasgdjaghdgahjsdgjassgdjgsdjasgsjdgjjhagsdjagsjdgajsdgasgdjhagsjdhgajgdjasgdjasghdasjdgajsghdjagsdhagsjdgáhgdjas gọn...",
                    modifier = Modifier.widthIn() // Limiting width
                )
            }

            // Action Buttons
            ActionButtons(modifier = Modifier.align(Alignment.CenterVertically))
        }
    }
}

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
fun ActionButtons(modifier: Modifier = Modifier) {
    var isFavorite by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { isFavorite = !isFavorite },
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (isFavorite) Color.Red else Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun JobListItemPreview() {
//    MaterialTheme {
//        JobListItem(jobTitle = "Mobile Developer")
//    }
//}