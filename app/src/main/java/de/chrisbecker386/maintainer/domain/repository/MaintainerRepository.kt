/*
 * Created by Christopher Becker on 09/05/2023, 12:23
 * Copyright (c) 2023. All rights reserved.
 * Last modified 09/05/2023, 12:23
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

package de.chrisbecker386.maintainer.domain.repository

interface MaintainerRepository {
    suspend fun getAllLocalCares()
}
