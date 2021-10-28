package com.tutorials.eu.favdish.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tutorials.eu.favdish.R
import com.tutorials.eu.favdish.databinding.FragmentRandomDishBinding
import com.tutorials.eu.favdish.viewmodel.NotificationsViewModel

class RandomDishFragment : Fragment() {

    private var mBinding:FragmentRandomDishBinding? = null
    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      mBinding= FragmentRandomDishBinding.inflate(inflater,container,false)



        return mBinding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}