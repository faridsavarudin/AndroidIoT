package id.co.dycode.nb_iot.model

import com.google.gson.annotations.SerializedName

data class BodyLogin(

	@field:SerializedName("password")
	var password: String? = null,

	@field:SerializedName("username")
	var username: String? = null
)