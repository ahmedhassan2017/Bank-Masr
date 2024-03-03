package banquemisr.challenge05.data.remote

import banquemisr.challenge05.Models.State
import banquemisr.challenge05.data.remote.responses.ErrorResponse
import retrofit2.Call
import java.net.UnknownHostException
import retrofit2.Response as RetrofitResponse

/**
 * A wrapper for API responses to handle common situations.
 */
class ApiResponse<T>(var data: T? = null, var error: ErrorResponse? = null, var code: Int? = null, var state: State) {
    constructor(): this(state = State.Loading)
    constructor(data: T?): this(data, state = State.Done)
    constructor(throwable: Throwable): this() {
        val errorMessage = if (throwable.message.isNullOrBlank()) {
            "Something went wrong"
        } else if (throwable.message.toString().contains("failed to connect")||
            throwable.message.toString().contains("timeout")) {
            "Check your internet connection"
        }
        else{
            throwable.message.toString()
        }

        error = ErrorResponse(errorMessage)
        state = State.NetworkError
    }
    constructor(retrofitResponse: RetrofitResponse<T>): this() {
        if (retrofitResponse.isSuccessful) {
            data = retrofitResponse.body()
            state = State.Done
            code = retrofitResponse.code()
        } else {
            error = ErrorParser.parseError(retrofitResponse)
            state = State.ApiError
            code = retrofitResponse.code()
        }
    }
    // TODO: resolve the server issue with file uploads then get rid of this
    constructor(retrofitResponse: RetrofitResponse<T>, ignore500: Boolean): this() {
        if (retrofitResponse.isSuccessful || ignore500 && retrofitResponse.code() == 500) {
            data = retrofitResponse.body()
            state = State.Done
            code = retrofitResponse.code()
        } else {
            error = ErrorParser.parseError(retrofitResponse)
            state = State.ApiError
            code = retrofitResponse.code()
        }
    }

    companion object {
        fun <T>forCall(call: Call<T>?): ApiResponse<T>
        {
            return try {
                ApiResponse(call?.execute()!!)
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException ->
                        ApiResponse(java.lang.Exception("Internet connection not available"))
                    else -> {
                        ApiResponse(e)
                    }

                }
            }
        }

        // TODO: resolve the server issue with file uploads then get rid of this
        fun <T>forCallIgnore500(call: Call<T>?): ApiResponse<T>
        {
            return try {
                ApiResponse(call?.execute()!!, true)
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException ->
                        ApiResponse(java.lang.Exception("Internet connection not available"))
                    else -> {
                        ApiResponse(e)
                    }

                }
            }
        }
    }

    fun isSuccess(): Boolean {
        return error == null
    }
}