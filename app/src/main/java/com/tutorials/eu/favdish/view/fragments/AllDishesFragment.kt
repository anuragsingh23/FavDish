package com.tutorials.eu.favdish.view.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tutorials.eu.favdish.R
import com.tutorials.eu.favdish.application.FavDishApplication
import com.tutorials.eu.favdish.databinding.DialogCustomListBinding
import com.tutorials.eu.favdish.databinding.FragmentAllDishesBinding
import com.tutorials.eu.favdish.model.entities.FavDish
import com.tutorials.eu.favdish.utils.Constants
import com.tutorials.eu.favdish.view.activities.AddUpdateDishActivity
import com.tutorials.eu.favdish.view.activities.MainActivity
import com.tutorials.eu.favdish.view.adapters.CustomListItemAdapter
import com.tutorials.eu.favdish.view.adapters.FavDishAdapter
import com.tutorials.eu.favdish.viewmodel.FavDishViewModel
import com.tutorials.eu.favdish.viewmodel.FavDishViewModelFactory
import com.tutorials.eu.favdish.viewmodel.HomeViewModel

class AllDishesFragment : Fragment() {

    private lateinit var mBinding: FragmentAllDishesBinding

    private  lateinit var mFavDishAdapter: FavDishAdapter

    private lateinit var mCustomListDialog: Dialog

    private val mFavDishViewModel : FavDishViewModel by viewModels {
        FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mBinding= FragmentAllDishesBinding.inflate(inflater,container,false)


        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)





        mBinding.rvDishesList.layoutManager=GridLayoutManager(requireActivity(),2)
        mFavDishAdapter=FavDishAdapter(this@AllDishesFragment)



        mBinding.rvDishesList.adapter= mFavDishAdapter


        mFavDishViewModel.allDishesList.observe(viewLifecycleOwner){
            dishes ->
            dishes.let {
               if (it.isNotEmpty()){
                   mBinding.rvDishesList.visibility=View.VISIBLE
                   mBinding.tvNoDishesAddedYet.visibility=View.GONE

                   mFavDishAdapter.dishesList(it)
               }else{
                   mBinding.rvDishesList.visibility=View.GONE
                   mBinding.tvNoDishesAddedYet.visibility=View.VISIBLE


               }
            }
        }
    }
    fun deleteDish(dish: FavDish){
        val builder =AlertDialog.Builder(requireActivity())
        builder.setTitle(resources.getString(R.string.title_delete_dish))
        builder.setMessage(resources.getString(R.string.msg_delete_dish_dialog,dish.title))
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton(resources.getString(R.string.lbl_yes)){ dialogInterface,_ ->
            mFavDishViewModel.delete(dish)
            dialogInterface.dismiss()
        }
        builder.setNegativeButton(resources.getString(R.string.lbl_no)){dailogInterface, _ ->
            dailogInterface.dismiss()
        }
        val alertDialog:AlertDialog=builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }


    fun dishDetails
                (favDish: FavDish){
        findNavController().navigate(AllDishesFragmentDirections.actionAllDishesToDishDetails(
            favDish
        ))

        if (activity is MainActivity){
            (activity as MainActivity?)?.hideBottomNavigationView()
        }
    }

    private fun filterDishesListDialog(){
        mCustomListDialog =Dialog(requireActivity())
        val binding :DialogCustomListBinding= DialogCustomListBinding.inflate(layoutInflater)

        mCustomListDialog.setContentView(binding.root)
        binding.tvTitle.text=resources.getString(R.string.title_select_item_to_filter)
        val dishTypes=Constants.dishTypes()
        dishTypes.add(0,Constants.ALL_ITEMS)
        binding.rvList.layoutManager=LinearLayoutManager(requireActivity())

        val adapter=CustomListItemAdapter(requireActivity(),this@AllDishesFragment,dishTypes,Constants.FILTER_SELECTION)
        binding.rvList.adapter=adapter
        mCustomListDialog.show()


    }

    override fun onResume() {
        super.onResume()
        if (activity is MainActivity){
            (activity as MainActivity?)?.showBottomNavigationView()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_dishes, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_add_dish -> {
                startActivity(Intent(requireActivity(), AddUpdateDishActivity::class.java))
                return true
            }
            R.id.actiom_filter_dishes ->{
                filterDishesListDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun filterSelection(filterItemSelection:String){
        mCustomListDialog.dismiss()



        if (filterItemSelection==Constants.ALL_ITEMS){
            mFavDishViewModel.allDishesList.observe(viewLifecycleOwner){
                    dishes ->
                dishes.let {
                    if (it.isNotEmpty()){
                        mBinding.rvDishesList.visibility=View.VISIBLE
                        mBinding.tvNoDishesAddedYet.visibility=View.GONE

                        mFavDishAdapter.dishesList(it)
                    }else{
                        mBinding.rvDishesList.visibility=View.GONE
                        mBinding.tvNoDishesAddedYet.visibility=View.VISIBLE



                    }
                }
            }
        }else{
            mFavDishViewModel.getFilteredList(filterItemSelection).observe(viewLifecycleOwner){
                dishes ->
                    dishes.let {
                        if (it.isEmpty()){
                            mBinding.rvDishesList.visibility=View.VISIBLE
                            mBinding.tvNoDishesAddedYet.visibility=View.GONE


                            mFavDishAdapter.dishesList(it)
                        }else{
                            mBinding.rvDishesList.visibility=View.GONE
                            mBinding.tvNoDishesAddedYet.visibility=View.VISIBLE
                        }
            }
            }
        }

    }
}
