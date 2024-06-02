package com.xc.brainstore.data.remote.retrofit

import com.xc.brainstore.data.remote.response.LoginResponse
import com.xc.brainstore.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("user_name") name: String,
        @Field("user_email") email: String,
        @Field("user_password") password: String,
        @Field("user_role") role: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

//    @FormUrlEncoded
//    @POST("user/detail")
//    fun postUserDetail(
//        @Field("id") id: Int,
//        @Field("image") image: string,
//        @Field("name") name: String,
//        @Field("email") email: String,
//        @Field("password") password: String,
//        @Field("phone_number") phone_number: String,
//        @Field("address") address: String
//    ): Call<UserDetailResponse>

//    @GET("user/detail")
//    fun getUserDetail(
//        @Field("id") id: Int,
//        @Field("image") image: string,
//        @Field("name") name: String,
//        @Field("email") email: String,
//        @Field("password") password: String
//        @Field("phone_number") phone_number: String,
//        @Field("address") address: String
//    ): Call<UserDetailResponse>

//    @GET("product")
//    fun getProduct(): Call<List<ProductResponse>>

//    @GET("search/product")
//    fun getProductSearch(
//        @Query("q") name: String
//    ): Call<ProductResponse>
//
//
//    @GET("product/detail")
//    fun getProductDetail(
//        @Field("id") id: Int,
//        @Field("image") image: string,
//        @Field("name") name: String,
//        @Field("price") price: String,
//        @Field("rating") rating: Int
//        @Field("stock") stock: Int
//        @Field("description") description: String,
//        @Field("specification") specification: String
//    ): Call<ProductDetailResponse>

//    @GET("seller")
//    fun getSeller(
//        @Field("id") id: Int,
//        @Field("image") image: string,
//        @Field("name") name: String,
//        @Field("location") location: String
//    ): Call<SellerResponse>

//
//    @GET("product/{product_name}")
//    fun getDetailProduct(@Path("product_name") product_name: String): Call<DetailProductResponse>
}