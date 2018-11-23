package id.co.dycode.nb_iot.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("X-IoT-JWT")
	val xIoTJWT: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("auth")
	val auth: Boolean? = null
)