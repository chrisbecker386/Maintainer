/*
 * Created by Christopher Becker on 20/04/2023, 09:24
 * Copyright (c) 2023. All rights reserved.
 * Last modified 20/04/2023, 09:24
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import de.chrisbecker386.maintainer.data.model.dummy.devTasks
import de.chrisbecker386.maintainer.ui.model.ShortStatusState
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.DIM_XXS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

@Composable
fun ShortStatus(
    title: String = "",
    state: ShortStatusState
) {
    EvenCard {
        Column(
            Modifier.padding(
                top = DIM_S,
                bottom = DIM_S,
                start = DIM_XS,
                end = DIM_XS
            )
        ) {
            Row {
                ConstraintLayout(Modifier.fillMaxWidth()) {
                    val (leftTextRef, rightIcon, rightText) = createRefs()
                    BodyText(
                        modifier = Modifier.constrainAs(leftTextRef) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                        text = title

                    )
                    BodyText(
                        modifier = Modifier.constrainAs(rightText) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                        text = state.toString()
                    )
                    Image(
                        modifier = Modifier
                            .constrainAs(rightIcon) {
                                end.linkTo(rightText.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                            .padding(end = DIM_XXS),
                        imageVector = Icons.Default.Handyman,
                        contentDescription = "repair",
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                    )
                }
            }
            Spacer(modifier = Modifier.height(DIM_XXS))
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(DIM_XXS),
                progress = state.getProgress(),
                color = MaterialTheme.colors.primary,
                strokeCap = StrokeCap.Round
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewMaintainObjectStatus() {
    MaintainerTheme {
        Column {
            ShortStatus(
                title = "Maintain Status",
                state = ShortStatusState(
                    numerator = 2,
                    denominator = 7
                )
            )
            Spacer(modifier = Modifier.size(DIM_XS))
            NextMaintains(
                machineTitle = "machine",
                tasks = devTasks
            )
        }
    }
}
