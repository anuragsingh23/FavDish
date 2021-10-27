package com.tutorials.eu.favdish.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tutorials.eu.favdish.R
import com.tutorials.eu.favdish.application.FavDishApplication
import com.tutorials.eu.favdish.databinding.FragmentFavouriteDishesBinding
import com.tutorials.eu.favdish.model.database.FavDishRepository
import com.tutorials.eu.favdish.model.entities.FavDish
import com.tutorials.eu.favdish.view.activities.MainActivity
import com.tutorials.eu.favdish.view.adapters.FavDishAdapter
import com.tutorials.eu.favdish.view.dashboard.DashboardViewModel
import com.tutorials.eu.favdish.viewmodel.FavDishViewModel
import com.tutorials.eu.favdish.viewmodel.FavDishViewModelFactory

class FavouriteDishesFragment : Fragment() {

   private var mBinding : FragmentFavouriteDishesBinding?=null

    private val mFavDishViewModel:FavDishViewModel by viewModels {
        FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
    }
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        mBinding= FragmentFavouriteDishesBinding.inflate(inflater,container,false)


        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFavDishViewModel.favoriteDishes.observe(viewLifecycleOwner){
            dishes ->
            dishes.let {

                mBinding!!.rvFavoriteDishesList.layoutManager=GridLayoutManager(requireActivity(),2)

                val adapter=FavDishAdapter(this)
                mBinding!!.rvFavoriteDishesList.adapter=adapter

                if (it.isNotEmpty()){
                    mBinding!!.rvFavoriteDishesList.visibility=View.VISIBLE
                    mBinding!!.tvNoFavoriteDishAvailable.visibility=View.GONE
                    adapter.dishesList(it)
                    }
                else{
                    mBinding!!.rvFavoriteDishesList.visibility=View.GONE
                    mBinding!!.tvNoFavoriteDishAvailable.visibility=View.VISIBLE

                }
            }
        }
    }

    fun dishDetails(favDish: FavDish){
        findNavController().navigate(FavouriteDishesFragmentDirections.
        actionFavoriteDishesToDishDetails(favDish))

        if(requireActivity() is MainActivity){
            (activity as MainActivity?)!!.hideBottomNavigationView()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

    override fun onResume() {
        super.onResume()
        if (requireActivity() is MainActivity){
            (activity as MainActivity?)!!.showBottomNavigationView()
        }
    }
}