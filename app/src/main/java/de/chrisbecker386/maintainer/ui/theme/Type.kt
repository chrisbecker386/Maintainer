package de.chrisbecker386.maintainer.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import de.chrisbecker386.maintainer.R

val nunitoSansFont = FontFamily(
    Font(R.font.nunito_sans_black, weight = FontWeight.Black),
    Font(R.font.nunito_sans_black_italic, weight = FontWeight.Black, style = FontStyle.Italic),
    Font(R.font.nunito_sans_bold, weight = FontWeight.Bold),
    Font(R.font.nunito_sans_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.nunito_sans_extra_bold, weight = FontWeight.ExtraBold),
    Font(
        R.font.nunito_sans_extra_bold_italic,
        weight = FontWeight.ExtraBold,
        style = FontStyle.Italic
    ),
    Font(R.font.nunito_sans_extra_light, weight = FontWeight.ExtraLight),
    Font(
        R.font.nunito_sans_extra_light_italic,
        weight = FontWeight.ExtraLight,
        style = FontStyle.Italic
    ),
    Font(R.font.nunito_sans_light, weight = FontWeight.Light),
    Font(R.font.nunito_sans_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.nunito_sans_regular),
    Font(R.font.nunito_sans_semi_bold, weight = FontWeight.SemiBold),
    Font(
        R.font.nunito_sans_semi_bold_italic,
        weight = FontWeight.SemiBold,
        style = FontStyle.Italic
    )
)
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 96.sp,
        letterSpacing = (-1.5).sp
    ),
    h2 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Bold,
        fontSize = 60.sp,
        letterSpacing = (-0.5).sp
    ),
    h3 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 48.sp,
        letterSpacing = 0.sp
    ),
    h4 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        letterSpacing = 0.25.sp
    ),
    h5 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Light,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 20.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Light,
        fontStyle = FontStyle.Italic,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),
    body1 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    body2 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    button = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp
    ),
    caption = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),
    overline = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = 1.5.sp
    )
)
