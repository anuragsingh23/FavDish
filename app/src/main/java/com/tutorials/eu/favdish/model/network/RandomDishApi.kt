package com.tutorials.eu.favdish.model.network

import androidx.room.PrimaryKey
import com.tutorials.eu.favdish.model.entities.RandomDish
import com.tutorials.eu.favdish.utils.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomDishApi {

    @GET(Constants.API_END_POINT)
    fun getRandomDish(

    @Query(Constants.API_KEY) apiKey : String,
    @Query(Constants.LIMIT_LICENSE) limitLicense: Boolean,
    @Query(Constants.TAGS) tags: String,
    @Query(Constants.NUMBER) number: Int

    ): Single<RandomDish.Recipes>
}