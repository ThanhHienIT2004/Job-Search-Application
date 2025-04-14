import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mobile.jobsearchapplication.R

@Composable
fun IconEditProfile(
    value: Boolean,
    onClick: (Boolean) -> Unit
) {
    IconButton(
        onClick = { onClick(!value) }
    ) {
        Image(
            painter = painterResource(R.drawable.ic_edit_profile),
            contentDescription = " Icon Edit Profile",
            modifier = Modifier.size(36.dp)
        )
    }
}

@Composable
fun IconUpdateProfile(
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = { onEditClick() },
        modifier = modifier
            .padding(5.dp)
            .size(40.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_edit),
            contentDescription = "Edit Icon"
        )
    }
}



