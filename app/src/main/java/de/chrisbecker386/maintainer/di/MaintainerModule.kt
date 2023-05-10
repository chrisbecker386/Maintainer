/*
 * Created by Christopher Becker on 09/05/2023, 13:36
 * Copyright (c) 2023. All rights reserved.
 * Last modified 09/05/2023, 13:36
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
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.chrisbecker386.maintainer.data.local.MaintainerDao
import de.chrisbecker386.maintainer.data.local.MaintainerDb
import de.chrisbecker386.maintainer.data.local.repository.MaintainerRepositoryImpl
import de.chrisbecker386.maintainer.domain.repository.MaintainerRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MaintainerModule {
    @Provides
    fun provideRoomDao(database: MaintainerDb): MaintainerDao {
        return database.dao
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext appContext: Context): MaintainerDb {
        return Room.databaseBuilder(
            appContext,
            MaintainerDb::class.java,
            "maintainer_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideMaintainerRepository(database: MaintainerDb): MaintainerRepository {
        return MaintainerRepositoryImpl(database)
    }
}
