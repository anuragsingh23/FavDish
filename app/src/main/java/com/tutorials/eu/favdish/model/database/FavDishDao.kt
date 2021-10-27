package com.tutorials.eu.favdish.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tutorials.eu.favdish.model.entities.FavDish
import kotlinx.coroutines.flow.Flow
import androidx.room.Delete

import androidx.room.Update

import androidx.room.OnConflictStrategy




@Dao
interface FavDishDao {


    @Insert
    suspend fun insertFavDishDetails(favDish: FavDish)

    @Query("SELECT * FROM FAV_DISHES_TABLE ORDER BY ID")
    fun getAllDishesList() : Flow<List<FavDish>>

    @Update
    suspend fun updateFavDishDetails(favDish: FavDish)

    @Query("SELECT * FROM FAV_DISHES_TABLE WHERE favorite_dish = 1")
    fun getFavouriteDishesList():Flow<List<FavDish>>
}
