package com.mobile.jobsearchapplication.ui.components.menu_bar.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobile.jobsearchapplication.ui.components.icon.IconEditAndTextField
import com.mobile.jobsearchapplication.ui.features.profile.ProfileViewModel

@Composable
fun MenuBarProfile(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
    ) {
        val viewModel: MenuBarProfileViewModel = viewModel()
        val menuBarState by viewModel.menuBarState.collectAsState()

        viewModel.listMainMenuBarProfile.forEach { item ->
            Card(
                modifier =
                    if (item == menuBarState.onMainMenuBar) {
                        Modifier.padding(top = 20.dp)
                            .size(72.dp)
                    } else {
                        Modifier.padding(12.dp, 16.dp)
                            .size(64.dp)
                            .clickable {
                                viewModel.onMainMenuBar(item)
                            }
                    },
                shape =
                    if (item == menuBarState.onMainMenuBar) {
                        RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
                    } else {
                        CircleShape
                    },
                colors = CardDefaults.cardColors(Color(0xFFFCFCFF)),
                elevation = CardDefaults.elevatedCardElevation(4.dp),
                border = BorderStroke(2.dp, Color.Gray)
            ) {
                Image(
                    painter = painterResource(item.icon), contentDescription = item.title,
                    modifier = Modifier.padding(10.dp).size(48.dp)
                )
            }
        }
    }
}

@Composable
fun MenuItemProfile(
    modifier: Modifier = Modifier
) {
    val menuBarVM: MenuBarProfileViewModel = viewModel()
    val profileVMl: ProfileViewModel = viewModel()

    val menuBarState by menuBarVM.menuBarState.collectAsState()
    val profileState by profileVMl.profileState.collectAsState()

    Card (
        modifier = modifier.fillMaxWidth().defaultMinSize(minHeight = 450.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(Color(0xFFFCFDFF)),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        menuBarVM.getListMenuItem(menuBarState.onMainMenuBar).forEach { item ->
            Row(
                modifier = Modifier.padding(10.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.title,
                    fontSize = 18.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                if (!profileState.onIconEditProfile) {
                    Text(
                        text = item.content,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(2.5f)
                            .border(1.dp, Color.Gray, RoundedCornerShape(20.dp))
                            .padding(10.dp)
                    )
                } else {
                    IconEditAndTextField(
                        item.content,
                        { item.content = it },
                        { profileVMl.onIconEditProfile(true) },
                        modifier = Modifier.weight(2.5f)
                    )
                }
            }
        }
    }
}