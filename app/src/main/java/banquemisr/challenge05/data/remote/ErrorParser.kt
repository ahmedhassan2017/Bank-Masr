package banquemisr.challenge05.data.remote

import banquemisr.challenge05.data.remote.responses.ErrorResponse
import retrofit2.Response
import java.lang.Exception

/**
 * API errors helper class
 */
class ErrorParser {
    companion object {
        /**
         * Parse error responses from the API.
         */
        fun parseError(response: Response<*>): ErrorResponse
        {
            val converter = ApiClient().retrofit()
                .responseBodyConverter<ErrorResponse>(ErrorResponse::class.java, emptyArray())

            val errorResponse =  try {
                converter.convert(response.errorBody()!!)!!
            } catch (exception: Exception) {
                ErrorResponse("Unknown error")
            }

            return if (errorResponse.message?.isBlank() == true && errorResponse.errors.isEmpty()) {
                ErrorResponse("Unknown error", errorResponse.errors)
            } else {
                errorResponse
            }
        }
    }
}