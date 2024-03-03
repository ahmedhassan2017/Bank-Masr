package banquemisr.challenge05.data.remote.responses

import banquemisr.challenge05.Models.User


/**
 * Login response data holder.
 */
data class LoginResponse(val user: User, val token: String)