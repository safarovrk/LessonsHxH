package com.example.lesson_3_safarov.data

import com.example.lesson_3_safarov.data.requestmodel.RequestLogin
import com.example.lesson_3_safarov.data.responsemodel.BaseResponse
import com.example.lesson_3_safarov.data.responsemodel.ResponseLogin
import com.example.lesson_3_safarov.data.responsemodel.ResponseProduct
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiLesson {

    @PUT("user/signin")
    suspend fun login(
        @Body requestLogin: RequestLogin,
    ): BaseResponse<ResponseLogin>

    @GET("products")
    suspend fun getProducts(
        @Query("PageSize") pageSize: Int = 20
    ) : BaseResponse<List<ResponseProduct>>
}