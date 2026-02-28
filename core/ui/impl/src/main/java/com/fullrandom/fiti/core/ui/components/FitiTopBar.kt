package com.fullrandom.fiti.core.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FitiTopBar(
    title: String?,
    modifier: Modifier = Modifier,
    leftIcon: ImageVector? = null,
    leftIconContentDescription: String? = null,
    onLeftIconClick: (() -> Unit)? = null,
    rightIcon: ImageVector? = null,
    rightIconContentDescription: String? = null,
    onRightIconClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leftIcon != null && onLeftIconClick != null) {
            IconButton(onClick = onLeftIconClick) {
                Icon(
                    imageVector = leftIcon,
                    contentDescription = leftIconContentDescription,
                )
            }
        } else {
            Spacer(modifier = Modifier.size(48.dp))
        }

        Text(
            text = title ?: "",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f),
        )

        if (rightIcon != null && onRightIconClick != null) {
            IconButton(onClick = onRightIconClick) {
                Icon(
                    imageVector = rightIcon,
                    contentDescription = rightIconContentDescription,
                )
            }
        } else {
            Spacer(modifier = Modifier.size(48.dp))
        }
    }
}

@Preview(showBackground = true, name = "FitiTopBar - full")
@Composable
private fun FitiTopBarFullPreview() {
    MaterialTheme {
        FitiTopBar(
            title = "Product details",
            leftIcon = Icons.AutoMirrored.Filled.ArrowBack,
            leftIconContentDescription = "Back",
            onLeftIconClick = {},
            rightIcon = Icons.Default.MoreVert,
            rightIconContentDescription = "More",
            onRightIconClick = {},
        )
    }
}

@Preview(showBackground = true, name = "FitiTopBar - title only")
@Composable
private fun FitiTopBarTitleOnlyPreview() {
    MaterialTheme {
        FitiTopBar(title = "Summary")
    }
}
