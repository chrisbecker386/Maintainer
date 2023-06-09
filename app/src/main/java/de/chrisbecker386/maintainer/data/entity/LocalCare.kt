/*
 * Created by Christopher Becker on 13/05/2023, 07:48
 * Copyright (c) 2023. All rights reserved.
 * Last modified 13/05/2023, 07:48
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

package de.chrisbecker386.maintainer.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cares")
data class LocalCare(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "care_id")
    val id: Int = 0,
    @ColumnInfo(name = "care_title")
    val title: String
)
