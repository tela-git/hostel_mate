package com.example.hostelmate.core.presentation.components

import android.icu.text.ListFormatter.Width
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostelmate.ui.theme.HostelMateTheme

@Composable
fun LongButton(
    onClick: () -> Unit,
    title: String,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        enabled = isEnabled,
        modifier = modifier
            .sizeIn(minHeight = 48.dp, minWidth = 200.dp, maxHeight = 48.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}

@PreviewLightDark
@Composable
fun LongButtonPreview() {
    HostelMateTheme {
        LongButton(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            onClick = { },
            title = "Let's go"
        )
    }
}