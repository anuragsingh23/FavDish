package com.tutorials.eu.favdish.model.database

import androidx.annotation.WorkerThread
import com.tutorials.eu.favdish.model.entities.FavDish

class FavDishRepository(private val favDishDao: FavDishDao) {

    @WorkerThread
    suspend fun insertFavDishData(favDish: FavDish){
        favDishDao.insertFavDishDetail(favDish)
    }
}