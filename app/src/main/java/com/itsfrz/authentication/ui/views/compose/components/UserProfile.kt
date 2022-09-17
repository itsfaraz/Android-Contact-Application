package com.itsfrz.authentication.ui.views.compose.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.itsfrz.authentication.R
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100
import com.itsfrz.authentication.ui.views.compose.ui.theme.DangerRed200

@Composable
fun UserProfile(
    userImage : String,
    userImageSize : Dp = 120.dp,
    updateImage : () -> Unit
) {
    Box(
        modifier = Modifier.wrapContentSize().padding(5.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        if (userImage.isNotBlank()){
            AsyncImage(modifier = Modifier
                .border(width = 1.dp, color = Color.White, shape = CircleShape)
                .clip(CircleShape)
                .size(userImageSize),
                model = (userImage),
                error = painterResource(id = R.drawable.profile),
                contentDescription = "Contact Image", )
        }else{
            Icon(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Dummy Profile",
                modifier = Modifier
                    .border(width = 1.dp, color = Color.White, shape = CircleShape)
                    .clip(CircleShape)
                    .size(userImageSize),
                tint = Color.LightGray
            )
        }

        Icon(
            modifier = Modifier.clickable { updateImage() },
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit Image",
            tint = Color.White
        )
    }
}

@Preview
@Composable
fun UserProfilePreview() {
    UserProfile(userImage = "") {
        
    }
}