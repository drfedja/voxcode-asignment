package com.voxcode.api

import com.voxcode.exceptions.NewsReaderException
import retrofit2.Response
import kotlin.coroutines.cancellation.CancellationException

interface ApiHandler {
    suspend fun <T : Any, R : Any> handleApi(
        execute: suspend () -> Response<T>,
        mapper: T.() -> (R)
    ): Result<R> {
        return try {
            val response = execute()

            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body.mapper())
            } else {
                Result.failure(
                    NewsReaderException(
                        "${response.code()} ${
                            response.errorBody()?.string()
                        }"
                    )
                )
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}
