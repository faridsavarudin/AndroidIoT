package id.co.dycode.nb_iot.model

import com.google.gson.annotations.SerializedName

data class Data(

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)