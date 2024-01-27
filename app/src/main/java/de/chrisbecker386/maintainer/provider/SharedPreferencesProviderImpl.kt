/*
 * Created by Christopher Becker on 27/01/2024, 21:23
 * Copyright (c) 2024. All rights reserved.
 * Last modified 27/01/2024, 21:23
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

package de.chrisbecker386.maintainer.provider

import android.content.SharedPreferences
import de.chrisbecker386.maintainer.provider.interfaces.SharedPreferencesProvider
import de.chrisbecker386.maintainer.ui.theme.IS_APP_CONFIGURED_DEFAULT
import de.chrisbecker386.maintainer.ui.theme.IS_APP_CONFIGURED_TAG
import javax.inject.Inject

class SharedPreferencesProviderImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferencesProvider {

    override fun isAppConfigured(): Boolean = readIsAppConfigured()
    override fun writeIsAppConfigured(appIsConfigured: Boolean): Boolean =
        sharedPreferences.edit().putBoolean(IS_APP_CONFIGURED_TAG, appIsConfigured).commit()

    private fun readIsAppConfigured(): Boolean =
        sharedPreferences.getBoolean(IS_APP_CONFIGURED_TAG, IS_APP_CONFIGURED_DEFAULT)
}
