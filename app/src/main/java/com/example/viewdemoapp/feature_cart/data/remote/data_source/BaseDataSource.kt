package com.example.viewdemoapp.feature_cart.data.remote.data_source

import com.example.viewdemoapp.core.util.Resource
import org.json.JSONObject
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                return if (body != null) {
                    Resource.success(body)
                } else {
                    Resource.error("No body")
                }
            } else {
                val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                val msg = jsonObj.getString("message")
                return Resource.error(msg ?: "error", null)
            }
        } catch (e: Exception) {
            val msg = e.message ?: e.toString()
            return Resource.error("Network call has failed for a following reason: $msg")
        }
    }
}
