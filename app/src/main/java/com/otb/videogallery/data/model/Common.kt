package com.otb.videogallery.data.model

/**
 * Created by Mohit Rajput on 14/9/20.
 */

sealed class DataResult<T>

data class Success<T>(val data : T) : DataResult<T>()

data class Error<T>(val errorMessage : String) : DataResult<T>()