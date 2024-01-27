/*
 * Created by Christopher Becker on 27/01/2024, 23:34
 * Copyright (c) 2024. All rights reserved.
 * Last modified 27/01/2024, 23:34
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

package provider


import android.content.SharedPreferences
import de.chrisbecker386.maintainer.provider.SharedPreferencesProviderImpl
import de.chrisbecker386.maintainer.ui.theme.IS_APP_CONFIGURED_DEFAULT
import de.chrisbecker386.maintainer.ui.theme.IS_APP_CONFIGURED_TAG
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class SharedPreferencesProviderImplTest {

    @InjectMocks
    lateinit var sharedPreferencesProvider: SharedPreferencesProviderImpl

    @Mock
    lateinit var sharedPreferences: SharedPreferences

    @Mock
    lateinit var editor: SharedPreferences.Editor

    private lateinit var sut: SharedPreferencesProviderImpl

    @Before
    fun setup() {
        sut = sharedPreferencesProvider
        whenever(sharedPreferences.edit()).thenReturn(editor)
        whenever(editor.commit()).thenReturn(true)
    }

    @Test
    fun `test isAppConfigured default false `() {
        whenever(
            sharedPreferences.getBoolean(
                IS_APP_CONFIGURED_TAG, IS_APP_CONFIGURED_DEFAULT
            )
        ).thenReturn(false)

        val result = sut.isAppConfigured()
        verify(sharedPreferences).getBoolean(IS_APP_CONFIGURED_TAG, IS_APP_CONFIGURED_DEFAULT)
        assertFalse(result)
    }

    @Test
    fun `test isAppConfigured true`() {
        whenever(
            sharedPreferences.getBoolean(IS_APP_CONFIGURED_TAG, IS_APP_CONFIGURED_DEFAULT)
        ).thenReturn(true)

        val result = sut.isAppConfigured()
        verify(sharedPreferences).getBoolean(IS_APP_CONFIGURED_TAG, IS_APP_CONFIGURED_DEFAULT)

        assertTrue(result)
    }

    @Test
    fun testWriteIsAppConfigured_True() {
        whenever(editor.putBoolean(IS_APP_CONFIGURED_TAG, true)).thenReturn(editor)
        val result = sut.writeIsAppConfigured(true)

        assert(result)
    }

    @Test
    fun testWriteIsAppConfigured_False() {
        whenever(editor.putBoolean(IS_APP_CONFIGURED_TAG, false)).thenReturn(editor)
        val result = sut.writeIsAppConfigured(false)

        assert(result)
    }
}