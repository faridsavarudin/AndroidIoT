package id.dycode.edu.androidforiot.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import id.co.dycode.nb_iot.model.AuthenticateResponse
import id.co.dycode.nb_iot.model.BodyLogin
import id.co.dycode.nb_iot.model.LoginResponse
import id.dycode.edu.androidforiot.MainActivity
import id.dycode.edu.androidforiot.R
import id.dycode.edu.androidforiot.service.ApiClient
import id.dycode.edu.androidforiot.service.PreferenceHelper
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        preferenceHelper = PreferenceHelper(this)

        if (preferenceHelper.getAuthenticate().equals("")){
            getAuthenticate()
        }

        if (preferenceHelper.getLoginResponse() != null){
            startActivity((Intent(this, MainActivity::class.java)))
            finish()
        }

        btn_login.setOnClickListener {
            postLogin()
        }
    }

    private fun postLogin() {
        if (edt_username.text.toString().isNotEmpty() and  edt_password.text.toString().isNotEmpty()){
            var bodyLogin = BodyLogin()

            bodyLogin.username = edt_username.text.toString()
            bodyLogin.password = edt_password.text.toString()

            ApiClient.getClient().postLogin(bodyLogin).enqueue(object :retrofit2.Callback<LoginResponse>{
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    response.let {
                        if (it.isSuccessful){
                            preferenceHelper.setLoginResponse(response.body())
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                }

            })

        }
    }

    private fun getAuthenticate() {
        ApiClient.getClient().getAuthenticate().enqueue(object : retrofit2.Callback<AuthenticateResponse>{

            override fun onFailure(call: Call<AuthenticateResponse>, t: Throwable) {
                Log.d("failed", t.message)
            }

            override fun onResponse(call: Call<AuthenticateResponse>, response: Response<AuthenticateResponse>) {
                response.let {
                    if (it.isSuccessful){
                        preferenceHelper.setAutenticate(response.body()?.accessToken.toString())
                    }
                }
            }

        })
    }
}
