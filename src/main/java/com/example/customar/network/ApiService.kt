package com.example.customar.network

import com.example.customar.models.CartItems
import com.example.customar.models.CartResponse
import com.example.customar.models.Item
import com.example.customar.models.Wishlist
import com.example.customar.models.WishlistRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/items/newArrivals/recent")
    fun getRecentItems(): Call<List<Item>>

    @GET("/items/mywishlist/{userId}")
    fun getWishlistItems(
        @Path("userId") userId: String
    ): Call<List<Item>>

    @GET("/items/mycart/{userId}")
    fun getCartItems(
        @Path("userId") userId: String
    ): Call<List<CartItems>>


    @POST("/items/addwishlist")
    fun addToWishlist(
        @Body request: WishlistRequest
    ): Call<Wishlist>

    @POST("/items/addtocart")
    fun addToCart(
        @Body request: WishlistRequest
    ): Call<CartResponse>

    @POST("/items/deleteCart")
    fun deleteCart(
        @Body request: WishlistRequest
    ): Call<CartResponse>

    @GET("/items/newArrivals/all")
    fun getAllItems(): Call<List<Item>>

    @GET("/items/autocomplete")
    fun getAutocompleteResults(@Query("query") query: String): Call<List<String>>

    @GET("/items/recommended")
    fun getRecommendedItems():Call<List<Item>>
}