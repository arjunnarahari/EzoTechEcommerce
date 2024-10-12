package app.ezotech.ecommerce.data.utils

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): Resource<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response: T = apiCall.invoke()
            Log.d("apiresponse111",":::"+response.toString())
            Resource.Success(response)
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    Log.d("apiresponse222",throwable.response()?.errorBody().toString())
                    Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                }
                else -> {
                    Log.d("apiresponse333",throwable.toString())
                    Resource.Failure(true, null, null)
                }
            }
        }
    }
}