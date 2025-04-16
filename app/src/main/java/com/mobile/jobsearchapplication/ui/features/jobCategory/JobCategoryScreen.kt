package com.mobile.jobsearchapplication.ui.features.jobCategory

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.data.model.jobcategory.JobCategory


@Composable
fun SectionJobCategory(
    jobCategoryVM: JobCategoryViewModel,
    navController: NavController
) {
    val jobCategoryUiState by jobCategoryVM.uiState.collectAsState()

    Text(
        "Danh mục việc làm",
        fontSize = 24.sp, fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp, 8.dp)
    )
     when (jobCategoryUiState) {
         is JobCategoryUiState.Loading -> {
             CircularProgressIndicator(modifier = Modifier.padding(16.dp))
         }

         is JobCategoryUiState.Error ->  {
             val message = (jobCategoryUiState as JobCategoryUiState.Error).message
             Text(
                 text = "Lỗi: $message",
                 color = Color.Red, fontSize = 24.sp,
                 modifier = Modifier.padding(16.dp).fillMaxWidth().wrapContentWidth()
             )
         }

         is JobCategoryUiState.Success -> {
             val categories = (jobCategoryUiState as JobCategoryUiState.Success).jobCategories

             LazyRow(
                 contentPadding = PaddingValues(horizontal = 16.dp),
                 horizontalArrangement = Arrangement.spacedBy(12.dp)
             ) {
                 items(categories) { category ->
                     JobCategoryItem(category) { navController.navigate("") }
                 }
             }
         }
    }
}

@Composable
fun JobCategoryItem(
    category: JobCategory,
    onClick: () -> Unit
) {
    ElevatedCard (
        colors =  CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(
            modifier = Modifier
                .clickable(onClick = onClick)
                .size(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            AsyncImage(
                model = category.imageUrl,
                contentDescription = category.name,
                modifier = Modifier
                    .fillMaxWidth(0.9f).height(75.dp)
                    .clip(RoundedCornerShape(0.dp, 0.dp,20.dp, 20.dp))
                    .background(Color.LightGray, shape = CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.error)
            )

            Text(
                text = category.name,
                fontSize = 14.sp,
                textAlign = TextAlign.Center, maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}