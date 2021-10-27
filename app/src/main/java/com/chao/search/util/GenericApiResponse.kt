package com.chao.search.util

import retrofit2.Response

@Suppress("unused")
sealed class GenericApiResponse<T> {

    companion object {
        private val TAG: String = "AppDebug"


        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>): GenericApiResponse<T> {
            if(response.isSuccessful){
                val body = response.body()
                if (body == null || response.code() == 204) {
                    return ApiEmptyResponse()
                }
                else if(response.code() == 401){
                    return ApiErrorResponse("401 Unauthorized. Token may be invalid.")
                }
                else {
                    return ApiSuccessResponse(body = body)
                }
            }
            else{
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                return ApiErrorResponse(errorMsg ?: "unknown error")
            }
        }
    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : GenericApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : GenericApiResponse<T>() {}

data class ApiErrorResponse<T>(val errorMessage: String) : GenericApiResponse<T>()