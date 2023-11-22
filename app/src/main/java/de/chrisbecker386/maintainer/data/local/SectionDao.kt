/*
 * Created by Christopher Becker on 20/11/2023, 10:49
 * Copyright (c) 2023. All rights reserved.
 * Last modified 20/11/2023, 10:49
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
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import de.chrisbecker386.maintainer.data.entity.Machine
import de.chrisbecker386.maintainer.data.entity.Section
import kotlinx.coroutines.flow.Flow

@Dao
interface SectionDao {

    @Upsert
    suspend fun addSection(section: Section)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSections(sections: List<Section>)

    @Transaction
    @Query("DELETE FROM sections WHERE section_id = :sectionId")
    suspend fun removeSection(sectionId: Int)

    @Delete
    suspend fun removeSections(sections: List<Section>)

    @Transaction
    @Query("DELETE FROM sections")
    suspend fun removeAllSections()

    @Transaction
    @Query("SELECT * FROM sections WHERE section_id = :sectionId")
    fun getSection(sectionId: Int): Flow<Section>

    @Query("SELECT * FROM sections")
    fun getAllSections(): Flow<List<Section>>

    @Transaction
    @Query("SELECT * FROM machines WHERE machine_fk_section_id = :sectionId")
    fun getMachinesForSection(sectionId: Int): Flow<List<Machine>>
}
