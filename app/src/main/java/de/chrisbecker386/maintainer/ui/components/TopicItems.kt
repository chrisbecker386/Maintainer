/*
 * Created by Christopher Becker on 17/04/2023, 06:48
 * Copyright (c) 2023. All rights reserved.
 * Last modified 17/04/2023, 06:48
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

package de.chrisbecker386.maintainer.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.chrisbecker386.maintainer.data.model.dummy.dummyTasks
import de.chrisbecker386.maintainer.data.model.interfaces.ItemObject
import de.chrisbecker386.maintainer.ui.theme.DIM_M
import de.chrisbecker386.maintainer.ui.theme.DIM_M_PLUS
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXXS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun TopicWithItems(modifier: Modifier = Modifier, data: ItemObject) {
    Box(modifier = modifier) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = DIM_XXXS,
            shape = RoundedCornerShape(DIM_S),
            backgroundColor = MaterialTheme.colors.primaryVariant
        ) {
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primaryVariant)
                        .padding(DIM_XS),

                    text = data.title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background)
                        .padding(DIM_XS)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier.fillMaxWidth()) {
                            data.list?.forEachIndexed { index, item ->
                                ItemNoDetails(
                                    modifier = Modifier.padding(bottom = DIM_XS),
                                    title = item.title,
                                    number = index + 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemNoDetails(modifier: Modifier = Modifier, title: String, number: Int? = null) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(DIM_M_PLUS)
                .background(
                    color = MaterialTheme.colors.primaryVariant,
                    shape = RoundedCornerShape(DIM_M)
                ),
            Alignment.Center
        ) {
            Text(
                text = number?.toString() ?: "",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onError,
                textAlign = TextAlign.Center
            )
        }
        Text(
            modifier = Modifier.padding(start = DIM_XS),
            text = title,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onError
        )
    }
}

@Composable
fun RowWithIconText(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    text: String = ""
) {
    Row(modifier = modifier.fillMaxWidth()) {
        icon?.let { icon ->
            Image(
                modifier = Modifier.size(DIM_M_PLUS),
                imageVector = icon,
                contentDescription = text
            )
        } ?: Spacer(modifier = Modifier.width(DIM_M_PLUS))
        Spacer(Modifier.width(DIM_XS))
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewTask() {
    MaintainerTheme {
        Column {
            TopicWithItems(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                data = dummyTasks[0]
            )
            TopicWithItems(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                data = dummyTasks[1]
            )
            RowWithIconText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                icon = Icons.Default.Accessibility,
                text = "some text"

            )
        }
    }
}
