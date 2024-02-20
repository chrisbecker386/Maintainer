/*
 * Created by Christopher Becker on 12/12/2023, 14:36
 * Copyright (c) 2023. All rights reserved.
 * Last modified 12/12/2023, 14:36
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

package de.chrisbecker386.maintainer.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.ui.theme.DIM_BIG_TWO
import de.chrisbecker386.maintainer.ui.theme.DIM_NO
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXL
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXXS
import de.chrisbecker386.maintainer.ui.theme.ICON_LIST
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun ImagePickerWithPreview(
    modifier: Modifier = Modifier,
    title: String = "no title",
    images: List<Int> = ICON_LIST,
    imageRes: Int = ICON_LIST.first(),
    onImageChange: (Int) -> Unit = {}

) {
    var selectedImage: Int by remember { mutableIntStateOf(imageRes) }

    UnevenCard {
        if (title.isNotEmpty()) {
            Text(
                text = title,
                color = colors.onBackground,
                style = typography.h4
            )
        }
        Spacer(modifier = Modifier.padding(DIM_XXXS))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            EvenCard(
                modifier = Modifier
                    .size(DIM_BIG_TWO)
                    .padding(top = DIM_XXS, bottom = DIM_XS)
            ) {
                Image(
                    modifier = Modifier.size(DIM_BIG_TWO),
                    painter = painterResource(id = selectedImage),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(colors.onBackground)
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = DIM_XS),
            border = BorderStroke(
                width = DIM_XXXXS,
                color = colors.onBackground
            ),
            shape = RoundedCornerShape(DIM_S),
            elevation = DIM_NO,
            backgroundColor = colors.background
        ) {
            ImagePickerHorizontal(
                modifier = Modifier.padding(all = DIM_XS),
                images = images,
                onImageChange = {
                    selectedImage = it
                    onImageChange(it)
                }
            )
        }
    }
}

@Composable
fun ImagePickerHorizontal(
    modifier: Modifier = Modifier,
    images: List<Int>,
    onImageChange: (Int) -> Unit = {}
) {
    var currentImage: Int? by remember { mutableStateOf(null) }

    LazyRow(
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(images) { index, imageRes ->
            Card(
                modifier = Modifier
                    .width(DIM_XXXL)
                    .height(DIM_XXXL)
                    .clickable {
                        currentImage = index
                        onImageChange(imageRes)
                    }
                    .padding(start = DIM_XXXS, end = DIM_XXXS),
                border = BorderStroke(
                    width = if (index == currentImage) DIM_XXS else DIM_XXXXS,
                    color = if (index == currentImage) colors.primary else colors.onBackground
                ),
                shape = RoundedCornerShape(DIM_XS),
                elevation = DIM_NO,
                backgroundColor = colors.background
            ) {
                Icon(
                    modifier = Modifier.padding(DIM_XS),
                    painter = painterResource(id = imageRes),
                    contentDescription = imageRes.toString(),
                    tint = colors.onBackground
                )
            }
        }
    }
}

@Preview(showBackground = false, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewImageSelection() {
    MaintainerTheme {
        Column(Modifier.fillMaxSize()) {
            ImagePickerWithPreview(
                modifier = Modifier.fillMaxWidth(),
                images = ICON_LIST,
                imageRes = ICON_LIST.first()
            )
        }
    }
}
