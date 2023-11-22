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
import de.chrisbecker386.maintainer.data.local.MachineDao
import de.chrisbecker386.maintainer.data.local.MaintainerDb
import de.chrisbecker386.maintainer.data.local.PreconditionDao
import de.chrisbecker386.maintainer.data.local.SectionDao
import de.chrisbecker386.maintainer.data.local.StepDao
import de.chrisbecker386.maintainer.data.local.TaskCompletedDao
import de.chrisbecker386.maintainer.data.local.TaskDao
import de.chrisbecker386.maintainer.data.local.repository.MaintainerRepositoryImpl
import de.chrisbecker386.maintainer.domain.repository.MaintainerRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MaintainerModule {

    @Provides
    fun provideSectionDao(database: MaintainerDb): SectionDao {
        return database.sectionDao
    }

    @Provides
    fun provideMachineDao(database: MaintainerDb): MachineDao {
        return database.machineDao
    }

    @Provides
    fun provideTasksDao(database: MaintainerDb): TaskDao {
        return database.taskDao
    }

    @Provides
    fun provideStepDao(database: MaintainerDb): StepDao {
        return database.stepDao
    }

    @Provides
    fun providePreconditionDao(database: MaintainerDb): PreconditionDao {
        return database.preconditionDao
    }

    @Provides
    fun provideCompletedTaskDao(database: MaintainerDb): TaskCompletedDao {
        return database.taskCompletedDao
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext appContext: Context): MaintainerDb {
        return Room.databaseBuilder(
            appContext,
            MaintainerDb::class.java,
            "maintainerdb"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideMaintainerRepository(
        sectionDao: SectionDao,
        machineDao: MachineDao,
        taskDao: TaskDao,
        stepDao: StepDao,
        preconditionDao: PreconditionDao,
        taskCompletedDao: TaskCompletedDao
    ): MaintainerRepository {
        return MaintainerRepositoryImpl(
            sectionDao,
            machineDao,
            taskDao,
            stepDao,
            preconditionDao,
            taskCompletedDao
        )
    }
}
