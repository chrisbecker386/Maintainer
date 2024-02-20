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

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.R
import de.chrisbecker386.maintainer.data.model.GridItemData
import de.chrisbecker386.maintainer.data.utility.isEven
import de.chrisbecker386.maintainer.ui.theme.DIM_NO
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXL
import de.chrisbecker386.maintainer.ui.theme.DIM_XXS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun OverviewGrid(
    items: List<GridItemData> = emptyList(),
    onItemClick: (Int) -> Unit = {}
) {
    val numberOfColumns = 2
    EvenCard {
        NonLazyGrid(
            columns = numberOfColumns,
            itemCount = items.size
        ) { index ->
            val modifier = Modifier.padding(start = if (index.isEven()) DIM_XS else DIM_NO)
                .padding(top = if (index >= numberOfColumns) DIM_XS else DIM_NO)
            OverviewGridItem(
                modifier = modifier,
                title = items[index].title,
                icon = items[index].imageRes?.let { icon -> ImageVector.vectorResource(id = icon) }
                    ?: Icons.Default.QuestionMark,
                onClick = { onItemClick(items[index].id) }
            )
        }
    }
}

@Composable
fun OverviewGridItem(
    modifier: Modifier = Modifier,
    title: String = "missing title",
    icon: ImageVector = Icons.Default.QuestionMark,
    onClick: () -> Unit = {}
) {
    Box(modifier = modifier) {
        EvenCard(Modifier.clickable { onClick() }) {
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
                GridItemText(title)
                Spacer(modifier = Modifier.height(DIM_XS))
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = false)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = false)
@Composable
fun PreviewSection() {
    MaintainerTheme {
        Column {
            OverviewGrid(
                items = listOf(
                    GridItemData(
                        1,
                        "Kitchen",
                        R.drawable.kitchen_48px
                    ),
                    GridItemData(
                        2,
                        "Car",
                        R.drawable.question_mark_48px
                    ),
                    GridItemData(
                        3,
                        "Bike"
                    ),
                    GridItemData(
                        4,
                        "Computer"
                    ),
                    GridItemData(
                        5,
                        "Bathroom"
                    )
                )
            )
        }
    }
}
