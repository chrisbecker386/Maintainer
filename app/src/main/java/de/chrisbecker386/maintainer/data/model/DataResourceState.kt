/*
 * Created by Christopher Becker on 01/02/2024, 14:16
 * Copyright (c) 2024. All rights reserved.
 * Last modified 01/02/2024, 14:16
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

package de.chrisbecker386.maintainer.data.model

sealed class DataResourceState<out T> {
    data class Loading<out T>(val data: T, val percentage: Int? = null) : DataResourceState<T>()
    data class Success<out T>(val data: T) : DataResourceState<T>()
    data class Error<out T>(val data: T, val message: String, val throwable: Throwable? = null) :
        DataResourceState<T>()
    data class Finished<out T>(val data: T, val title: String?, val text: String?) :
        DataResourceState<T>()
}
