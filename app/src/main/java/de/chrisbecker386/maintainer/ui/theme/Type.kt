/*
 * Created by Christopher Becker on 13/04/2023, 17:40
 * Copyright (c) 2023. All rights reserved.
 * Last modified 13/04/2023, 17:40
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package de.chrisbecker386.maintainer.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        fontWeight = FontWeight.Light,
        fontSize = 53.sp,
        letterSpacing = (-1.5).sp
    ),
    h2 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Bold,
        fontSize = 37.sp,
        letterSpacing = 0.25.sp
    ),
    h3 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp,
        letterSpacing = 0.25.sp
    ),
    h4 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        letterSpacing = 0.25.sp
    ),
    h5 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        letterSpacing = 0.1.sp
    ),
    body1 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = 0.5.sp
    ),
    body2 = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        letterSpacing = 0.25.sp
    ),
    button = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        letterSpacing = 1.25.sp
    ),
    caption = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        letterSpacing = 0.4.sp
    ),
    overline = TextStyle(
        fontFamily = nunitoSansFont,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        letterSpacing = 1.5.sp
    )
)

@Preview(showBackground = true)
@Composable
fun PreviewTyping() {
    MaintainerTheme {
        Column(Modifier.width(intrinsicSize = IntrinsicSize.Max).height(intrinsicSize = IntrinsicSize.Max)) {
            val typeLevel = listOf<String>(
                "h1",
                "h2",
                "h3",
                "h4",
                "h5",
                "h6",
                "subtitle1",
                "subtitle2",
                "body1",
                "body2",
                "button",
                "caption",
                "overline"
            )
            typeLevel.forEach { type ->
                val style = when (type) {
                    "h1" -> MaterialTheme.typography.h1
                    "h2" -> MaterialTheme.typography.h2
                    "h3" -> MaterialTheme.typography.h3
                    "h4" -> MaterialTheme.typography.h4
                    "h5" -> MaterialTheme.typography.h5
                    "h6" -> MaterialTheme.typography.h6
                    "subtitle1" -> MaterialTheme.typography.subtitle1
                    "subtitle2" -> MaterialTheme.typography.subtitle2
                    "body1" -> MaterialTheme.typography.body1
                    "body2" -> MaterialTheme.typography.body2
                    "button" -> MaterialTheme.typography.button
                    "caption" -> MaterialTheme.typography.caption
                    "overline" -> MaterialTheme.typography.overline
                    else -> MaterialTheme.typography.h6
                }
                Text(text = "$type: Nunito Sans ", style = style, maxLines = 1, modifier = Modifier.padding(8.dp))
            }
        }
    }
}
