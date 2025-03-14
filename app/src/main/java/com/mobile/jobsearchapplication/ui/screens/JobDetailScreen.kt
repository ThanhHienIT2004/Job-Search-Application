package com.mobile.jobsearchapplication.ui.screens

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.WorkOff
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.model.SimilarJob

@Composable
fun JobDetailScreen(navController: NavHostController, jobTitle: String) {
    BaseScreen(
        showBackButton = true,
        onBackClick = { navController.popBackStack() },
        actionsBot = { BottomActionBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
        ){
            // Nội dung chi tiết công việc
            JobDetailContent(jobTitle)
        }
    }
}

@Composable
fun JobDetailContent(jobTitle: String?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            // Ảnh minh họa (tạm dùng Box làm ảnh placeholder)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.LightGray)
            ) {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = "Placeholder",
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                    tint = Color.Gray
                )
            }
        }

        item {
            // Tiêu đề công việc
            jobTitle?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }

        item {
            // Mức lương
            Text(
                text = "35.000 - 40.000 đ / ngày",
                fontSize = 15.sp,
                color = Color.Red
            )
            Text(
                text = "3 giờ trước",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Photo user",
                    modifier = Modifier
                        .size(40.dp)
                )
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationCity, // Change to an appropriate business icon
                            contentDescription = "Company",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Công ty Campuchia",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp)) // Spacing between rows

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn, // Use location icon
                            contentDescription = "Location",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Nơi làm việc: KCN Tân Bình, Hồ Chí Minh",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        item {
            Divider(
                color = Color.Black, // Màu đen
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Thông tin công việc:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Mô tả công việc
            Text(
                text = """Chúng tôi hiện đang tuyển dụng vị trí Chuyên viên Phát triển Kinh doanh để gia nhập đội ngũ năng động của công ty. Ứng viên sẽ chịu trách nhiệm xây dựng và duy trì mối quan hệ với khách hàng, tìm kiếm cơ hội hợp tác mới, đồng thời phối hợp với các phòng ban để phát triển chiến lược kinh doanh hiệu quả. Công việc đòi hỏi bạn phải có kỹ năng đàm phán xuất sắc, tư duy phân tích nhạy bén và ít nhất 3 năm kinh nghiệm trong lĩnh vực liên quan. Ngoài ra, sự sáng tạo, tinh thần trách nhiệm cao và khả năng làm việc dưới áp lực là những phẩm chất chúng tôi đặc biệt tìm kiếm. Nếu bạn sẵn sàng thử sức trong một môi trường chuyên nghiệp, nơi bạn có thể phát huy tối đa tiềm năng của mình và đóng góp vào sự phát triển chung của công ty, hãy nhanh tay gửi CV về cho chúng tôi để cùng nhau tạo nên những thành tựu vượt bậc trong tương lai! Chúng tôi hiện đang tuyển dụng vị trí Chuyên viên Phát triển Kinh doanh để gia nhập đội ngũ năng động của công ty. Ứng viên sẽ chịu trách nhiệm xây dựng và duy trì mối quan hệ với khách hàng, tìm kiếm cơ hội hợp tác mới, đồng thời phối hợp với các phòng ban để phát triển chiến lược kinh doanh hiệu quả. Công việc đòi hỏi bạn phải có kỹ năng đàm phán xuất sắc, tư duy phân tích nhạy bén và ít nhất 3 năm kinh nghiệm trong lĩnh vực liên quan. Ngoài ra, sự sáng tạo, tinh thần trách nhiệm cao và khả năng làm việc dưới áp lực là những phẩm chất chúng tôi đặc biệt tìm kiếm. Nếu bạn sẵn sàng thử sức trong một môi trường chuyên nghiệp, nơi bạn có thể phát huy tối đa tiềm năng của mình và đóng góp vào sự phát triển chung của công ty, hãy nhanh tay gửi CV về cho chúng tôi để cùng nhau tạo nên những thành tựu vượt bậc trong tương lai! Chúng tôi hiện đang tuyển dụng vị trí Chuyên viên Phát triển Kinh doanh để gia nhập đội ngũ năng động của công ty. Ứng viên sẽ chịu trách nhiệm xây dựng và duy trì mối quan hệ với khách hàng, tìm kiếm cơ hội hợp tác mới, đồng thời phối hợp với các phòng ban để phát triển chiến lược kinh doanh hiệu quả. Công việc đòi hỏi bạn phải có kỹ năng đàm phán xuất sắc, tư duy phân tích nhạy bén và ít nhất 3 năm kinh nghiệm trong lĩnh vực liên quan. Ngoài ra, sự sáng tạo, tinh thần trách nhiệm cao và khả năng làm việc dưới áp lực là những phẩm chất chúng tôi đặc biệt tìm kiếm. Nếu bạn sẵn sàng thử sức trong một môi trường chuyên nghiệp, nơi bạn có thể phát huy tối đa tiềm năng của mình và đóng góp vào sự phát triển chung của công ty, hãy nhanh tay gửi CV về cho chúng tôi để cùng nhau tạo nên những thành tựu vượt bậc trong tương lai!""",
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                // Dòng "Liên hệ ngay: 096682****"
                Text(
                    text = "Liên hệ ngay: 096682****",
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    fontSize = 16.sp
                )

                // Hình thức trả lương
                InfoItem(
                    icon = Icons.Default.AttachMoney,
                    label = "Hình thức trả lương",
                    value = "Theo ngày"
                )

                // Loại công việc
                InfoItem(
                    icon = Icons.Default.Schedule,
                    label = "Loại công việc",
                    value = "Bán thời gian"
                )

                // Ngành nghề
                InfoItem(
                    icon = Icons.Default.Storefront, // Chọn icon phù hợp
                    label = "Ngành nghề",
                    value = "Bán hàng"
                )

                // Kinh nghiệm
                InfoItem(
                    icon = Icons.Default.WorkOff,
                    label = "Kinh nghiệm",
                    value = "Không yêu cầu"
                )

                // Giới tính
                InfoItem(
                    icon = Icons.Default.Person, // Hoặc custom icon nếu cần
                    label = "Giới tính",
                    value = "Nữ"
                )

                // Tên công ty
                InfoItem(
                    icon = Icons.Default.Business,
                    label = "Tên công ty",
                    value = "Minh"
                )

                // Số lượng tuyển dụng
                InfoItem(
                    icon = Icons.Default.Group,
                    label = "Số lượng tuyển dụng",
                    value = "3"
                )

                // Học vấn tối thiểu
                InfoItem(
                    icon = Icons.Default.School,
                    label = "Học vấn tối thiểu",
                    value = "Không yêu cầu"
                )

                // Chứng chỉ / Kỹ năng
                InfoItem(
                    icon = Icons.Default.Badge, // Hoặc bất kỳ icon nào bạn thấy phù hợp
                    label = "Chứng chỉ / Kỹ năng",
                    value = "Nữ, khéo léo, tỉ mỉ, cẩn thận, có trách nhiệm"
                )

                // Tuổi tối thiểu
                InfoItem(
                    icon = Icons.Default.CalendarMonth,
                    label = "Tuổi tối thiểu",
                    value = "18"
                )

                // Tuổi tối đa
                InfoItem(
                    icon = Icons.Default.CalendarMonth,
                    label = "Tuổi tối đa",
                    value = "45"
                )
            }
            SimilarJobsScreen()

        }

    }
}

@Composable
fun BottomActionBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color(0xFFF6F0FF)) // Màu nền nhạt
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Nút gọi
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable { /* TODO: Xử lý gọi */ }
                .padding(horizontal = 10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Gọi",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Gọi",
                fontSize = 12.sp,
                color = Color.Black
            )
        }

        // Nút chat
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable { /* TODO: Xử lý chat */ }
                .padding(horizontal = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Chat,
                contentDescription = "Chat",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Chat",
                fontSize = 12.sp,
                color = Color.Black
            )
        }

        // Nút ứng tuyển
        Button(
            onClick = { /* TODO: Xử lý ứng tuyển */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF6F61) // Màu cam đỏ
            ),
            shape = RoundedCornerShape(50),
            modifier = Modifier.height(48.dp)
        ) {
            Text(
                text = "Ứng tuyển",
                color = Color.White,
                fontSize = 14.sp
            )
        }

    }
}

@Composable
fun InfoItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null, // mô tả cho accessibility
            tint = Color.Gray,
            modifier = Modifier.size(15.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$label: "
        )
        Text(text = value, fontWeight = FontWeight.SemiBold
        )
    }
}
@Composable
fun SimilarJobItem(job: SimilarJob) {
    Card(
        modifier = Modifier
            .width(200.dp)             // Độ rộng tùy ý
            .wrapContentHeight()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
//        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            // 1) Ảnh hoặc placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)      // Chiều cao cho ảnh
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,  // Tạm icon người
                    contentDescription = "Job Image",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Gray
                )

                // Nếu là đối tác (isPartner = true), hiển thị badge
                if (job.isPartner) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .background(
                                color = Color.Red,
                                shape = RoundedCornerShape(bottomStart = 8.dp)
                            )
                    ) {
                        Text(
                            text = "ĐỐI TÁC",
                            color = Color.White,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 2) Tiêu đề
            Text(
                text = job.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            // 3) Tên công ty
            Text(
                text = job.company,
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 4) Mức lương
            Text(
                text = job.wage,
                fontSize = 13.sp,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 5) Thời gian đăng + Địa điểm
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Icon thời gian
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = job.postedTime,
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Icon địa điểm
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = job.location,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun SimilarJobsSection(
    similarJobs: List<SimilarJob>,
    onSeeAllClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Tiêu đề + "Xem Tất cả"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tin đăng tương tự",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            TextButton(onClick = onSeeAllClick) {
                Text("Xem Tất cả >")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Danh sách công việc ngang
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(similarJobs) { job ->
                SimilarJobItem(job)
            }
        }
    }
}
@Composable
fun SimilarJobsScreen() {
    val sampleJobs = listOf(
        SimilarJob(
            id = 1,
            title = "TUYỂN 3 NHÂN VIÊN ĐÓNG GÓI",
            company = "Cthop",
            wage = "Đến 250.000 đ/ngày",
            postedTime = "9 giờ trước",
            location = "Phường 6"
        ),
        SimilarJob(
            id = 2,
            title = "(LƯƠNG TUẦN) GRGR TƯ...",
            company = "CÔNG TY TNHH ...",
            wage = "Đến 400.000 đ/ngày",
            postedTime = "3 tuần trước",
            location = "Xã T...",
            isPartner = true
        ),
        SimilarJob(
            id = 3,
            title = "NHÂN VIÊN HÀNG ...",
            company = "HOPV",
            wage = "Đến 200.000 đ/ngày",
            postedTime = "1 ngày trước",
            location = "Quận 1"
        )
    )

    // Gọi composable
    SimilarJobsSection(
        similarJobs = sampleJobs,
        onSeeAllClick = {
            // Xử lý sự kiện "Xem Tất cả"
        }
    )
}

