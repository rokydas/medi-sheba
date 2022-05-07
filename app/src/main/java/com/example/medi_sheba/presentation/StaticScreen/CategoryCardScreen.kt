package com.example.medi_sheba.presentation.StaticScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.medi_sheba.R
import com.example.medi_sheba.ui.theme.PrimaryColor
import org.w3c.dom.Text

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    name:String,
    contentName: String,
    painter: Painter,


){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 7.dp, vertical = 5.dp)
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .background(Color.White)
    ) {
        Image(
            painter = painter,
            contentDescription = contentName,
            modifier = Modifier
                .width(60.dp)
                .padding(vertical = 10.dp)
        )
        Text(text = name,
            modifier = Modifier.padding(bottom = 5.dp),
            style = TextStyle(fontSize = 14.sp)
        )
    }
}



