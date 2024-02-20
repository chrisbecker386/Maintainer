/*
 * Created by Christopher Becker on 30/01/2024, 22:01
 * Copyright (c) 2024. All rights reserved.
 * Last modified 30/01/2024, 22:01
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

package de.chrisbecker386.maintainer.ui.component.editables

import android.content.res.Configuration
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import de.chrisbecker386.maintainer.data.entity.Step
import de.chrisbecker386.maintainer.ui.component.AddStepDialog
import de.chrisbecker386.maintainer.ui.component.BaseButton
import de.chrisbecker386.maintainer.ui.component.StepWithDetails
import de.chrisbecker386.maintainer.ui.component.UnevenCard
import de.chrisbecker386.maintainer.ui.theme.DEFAULT_STEP
import de.chrisbecker386.maintainer.ui.theme.DIM_S
import de.chrisbecker386.maintainer.ui.theme.DIM_XS
import de.chrisbecker386.maintainer.ui.theme.MaintainerTheme

/**
 * Composable for editing a list of steps associated with a task.
 *
 * @param modifier Modifier for styling and layout adjustments.
 * @param steps List of steps to be edited.
 * @param onValueChange Lambda invoked when the list of steps changes.
 * @param content Optional composable lambda for additional content.
 */
@Composable
fun StepsEditor(
    modifier: Modifier = Modifier,
    steps: List<Step> = emptyList(),
    onValueChange: (List<Step>) -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    var currentStep: Step by remember { mutableStateOf(DEFAULT_STEP) }
    val localSteps by rememberSaveable { mutableStateOf(steps.toMutableList()) }

    var showTaskDialog by remember { mutableStateOf(false) }
    UnevenCard {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = DIM_XS, bottom = DIM_S, start = DIM_XS, end = DIM_XS),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "steps",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "add some steps for your task",
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                BaseButton(
                    text = "add",
                    onClick = { showTaskDialog = !showTaskDialog }
                )
            }

            if (localSteps.size > 0) {
                localSteps.forEachIndexed { index, step ->
                    StepWithDetails(
                        modifier = Modifier.pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    currentStep = localSteps[index]
                                    showTaskDialog = !showTaskDialog
                                }
                            )
                        },
                        step = step
                    )
                }
            }

            if (showTaskDialog) {
                AddStepDialog(
                    step = currentStep,
                    onConfirm = { step ->
                        if (step.order == 0) {
                            localSteps.add(
                                Step(
                                    order = localSteps.size + 1,
                                    id = 0,
                                    title = step.title,
                                    description = step.description,
                                    completedDate = step.completedDate,
                                    taskId = step.taskId
                                )
                            )
                        } else {
                            localSteps[step.order - 1] = step
                        }
                        currentStep = DEFAULT_STEP
                        showTaskDialog = false
                        onValueChange(localSteps)
                    },
                    onDismissRequest = { showTaskDialog = false }
                )
            }
            content()
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewStepsEditor() {
    MaintainerTheme {
        StepsEditor()
    }
}
