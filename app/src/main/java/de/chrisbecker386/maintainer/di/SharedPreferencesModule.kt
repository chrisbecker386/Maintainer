/*
 * Created by Christopher Becker on 27/01/2024, 22:38
 * Copyright (c) 2024. All rights reserved.
 * Last modified 27/01/2024, 22:38
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

package de.chrisbecker386.maintainer.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.chrisbecker386.maintainer.provider.SharedPreferencesProviderImpl
import de.chrisbecker386.maintainer.provider.interfaces.SharedPreferencesProvider
import de.chrisbecker386.maintainer.ui.theme.MAINTAINER_APP
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(MAINTAINER_APP, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesProvider(sharedPreferences: SharedPreferences): SharedPreferencesProvider {
        return SharedPreferencesProviderImpl(sharedPreferences)
    }
}
