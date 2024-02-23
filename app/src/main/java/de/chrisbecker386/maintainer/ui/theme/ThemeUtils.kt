/*
 * Created by Christopher Becker on 23/02/2024, 21:56
 * Copyright (c) 2024. All rights reserved.
 * Last modified 23/02/2024, 21:56
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

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES, locale = "de", apiLevel = 33)
@Preview(name = "Light Mode", showBackground = true, uiMode = UI_MODE_NIGHT_NO, apiLevel = 33)
annotation class ThemePreviews()

@Preview(
    name = "Landscape Mode",
    showBackground = true,
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 640
)
@Preview(name = "Portrait Mode", showBackground = true, device = Devices.PIXEL_4)
annotation class OrientationPreviews

class TextPreviewProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf(
        "Short label",
        "A slightly longer label",
        "A really too long label, which might never happen but just to evaluate how UI will behave if"
    )
}
