import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mobile.jobsearchapplication.R

@Composable
fun IconUpdateProfile(
    isEditing: Boolean,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = { onEditClick() },
        modifier = modifier
            .size(32.dp),
        containerColor = if(isEditing) Color(0xFF0722BB) else Color(0xFF6878D3),
        contentColor = if(isEditing) Color.White else Color(0xFFB5B8CC),
        shape = RoundedCornerShape(8.dp),
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 2.dp,
            pressedElevation = 8.dp
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_edit),
            contentDescription = "Icon Edit Profile",
            modifier = Modifier.size(20.dp)
        )
    }
}



