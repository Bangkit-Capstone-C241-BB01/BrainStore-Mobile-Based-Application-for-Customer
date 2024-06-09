package com.xc.brainstore.data.remote.retrofit

import com.xc.brainstore.data.remote.response.LoginResponse
import com.xc.brainstore.data.remote.response.ProductResponseItem
import com.xc.brainstore.data.remote.response.RegisterResponse
import com.xc.brainstore.data.remote.response.SearchResponse
import com.xc.brainstore.data.remote.response.SellerResponse
import com.xc.brainstore.data.remote.response.UpdateUserDetailResponse
import com.xc.brainstore.data.remote.response.UserDetailResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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
        @Field("user_email") email: String,
        @Field("user_password") password: String
    ): Call<LoginResponse>

    @GET("profiles")
    fun getUserDetail(): Call<UserDetailResponse>

    @Multipart
    @PUT("profiles")
    fun putUserDetailWithoutImg(
        @Part("user_name") userName: RequestBody,
        @Part("user_phone") userPhone: RequestBody,
        @Part("user_address") userAddress: RequestBody
    ): Call<UpdateUserDetailResponse>

    @Multipart
    @PUT("profiles")
    fun putUserDetail(
        @Part userImg: MultipartBody.Part,
        @Part("user_name") userName: RequestBody,
        @Part("user_phone") userPhone: RequestBody,
        @Part("user_address") userAddress: RequestBody
    ): Call<UpdateUserDetailResponse>

    @GET("products")
    fun getProduct(): Call<List<ProductResponseItem>>

    @GET("customers/products/news")
    fun getNewestProduct(): Call<List<ProductResponseItem>>

    @GET("customers/products/rates")
    fun getPopularProduct(): Call<List<ProductResponseItem>>

    @GET("customers/products/locations")
    fun getLocationProduct(): Call<List<ProductResponseItem>>

    @POST("customers/products?")
    fun getProductSearch(
        @Query("product_name") productName: String
    ): Call<SearchResponse>

    @GET("products/{id}")
    fun getProductDetail(
        @Path("id") id: Int?
    ): Call<ProductResponseItem>

    @GET("stores/{id}")
    fun getStoreDetail(
       @Path("id") id: Int?
    ): Call<SellerResponse>

}