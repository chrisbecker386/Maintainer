/*
 * Created by Christopher Becker on 09/05/2023, 12:48
 * Copyright (c) 2023. All rights reserved.
 * Last modified 09/05/2023, 12:48
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

package de.chrisbecker386.maintainer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.chrisbecker386.maintainer.data.entity.Section

@Dao
interface MaintainerDao {
    @Query("SELECT * FROM sections")
    suspend fun getAllSections(): List<Section>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllSections(sections: List<Section>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewSection(section: Section)
}
