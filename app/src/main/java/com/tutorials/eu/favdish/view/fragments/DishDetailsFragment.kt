package com.tutorials.eu.favdish.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.tutorials.eu.favdish.R
import com.tutorials.eu.favdish.databinding.FragmentDishDetailsBinding
import java.io.IOException


/**
 * A simple [Fragment] subclass.
 * Use the [DishDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DishDetailsFragment : Fragment() {


    private  var mBinding : FragmentDishDetailsBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBinding= FragmentDishDetailsBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args : DishDetailsFragmentArgs by navArgs()
        args.let {
            try {
                Glide.with(requireContext())
                    .load(it.dishDetails.image)
                    .centerCrop()
                    .into(mBinding!!.ivDishImage)
            }catch (e:IOException){
                e.printStackTrace()
            }
            mBinding!!.tvTitle.text=it.dishDetails.title
            mBinding!!.tvType.text=it.dishDetails.type.coerceAtLeast("1")
            mBinding!!.tvCategory.text=it.dishDetails.category
            mBinding!!.tvIngredients.text=it.dishDetails.ingredients
            mBinding!!.tvCookingDirection.text=it.dishDetails.directionToCook
            mBinding!!.tvCookingTime.text=resources.getString(R.string.lbl_estimate_cooking_time,it.dishDetails.cookingTime)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding=null
    }


}