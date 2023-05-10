/*
 * Created by Christopher Becker on 10/05/2023, 13:03
 * Copyright (c) 2023. All rights reserved.
 * Last modified 10/05/2023, 13:03
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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.data.model.CareGridItemData
import de.chrisbecker386.maintainer.ui.theme.DIM_NO
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXL
import de.chrisbecker386.maintainer.ui.theme.DIM_XXS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXXS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun CareGrid(
    modifier: Modifier = Modifier,
    items: List<CareGridItemData> = emptyList()
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Card(
            Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(DIM_S),
            border = BorderStroke(
                width = DIM_XXXXS,
                color = MaterialTheme.colors.onBackground
            ),
            elevation = DIM_NO
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = DIM_XXS, end = DIM_XXS, top = DIM_XXS, bottom = DIM_XXS)
            ) {
                NonLazyGrid(
                    columns = 2,
                    itemCount = items.size
                ) {
                    CareGridItem(
                        modifier = Modifier.padding(DIM_XXS),
                        title = items[it].title,
                        icon = items[it].icon
                    )
                }
            }
        }
    }
}

@Composable
fun CareGridItem(
    modifier: Modifier = Modifier,
    title: String = "missing title",
    icon: ImageVector = Icons.Default.QuestionMark,
    onClick: (String) -> Unit = {}
) {
    Box(modifier = modifier) {
        Card(
            Modifier.fillMaxWidth().clickable { onClick(title) },
            shape = RoundedCornerShape(DIM_XS),
            border = BorderStroke(
                width = DIM_XXXXS,
                color = MaterialTheme.colors.onBackground
            ),
            elevation = DIM_NO
        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(DIM_XXS))
                Image(
                    modifier = Modifier
                        .width(DIM_XXL)
                        .height(DIM_XXL),
                    imageVector = icon,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                    contentDescription = title
                )
                Spacer(modifier = Modifier.height(DIM_XXS))
                Text(
                    text = title,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.height(DIM_XS))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCare() {
    MaintainerTheme {
        CareGrid(
            modifier = Modifier.fillMaxWidth(),
            items = listOf(
                CareGridItemData(
                    "Kitchen",
                    Icons.Default.QuestionMark
                ),
                CareGridItemData(
                    "Car",
                    Icons.Default.QuestionMark
                ),
                CareGridItemData(
                    "Bike",
                    Icons.Default.QuestionMark
                ),
                CareGridItemData(
                    "Computer",
                    Icons.Default.QuestionMark
                ),
                CareGridItemData(
                    "Bathroom",
                    Icons.Default.QuestionMark
                )
            )
        )
    }
}
