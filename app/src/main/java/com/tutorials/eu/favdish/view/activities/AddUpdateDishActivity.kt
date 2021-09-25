package com.tutorials.eu.favdish.view.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.read
import android.view.View
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.tutorials.eu.favdish.R
import com.tutorials.eu.favdish.databinding.ActivityAddUpdateDishBinding
import com.tutorials.eu.favdish.databinding.DialogCustomImageSelectionBinding
import android.Manifest
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.util.stream.DoubleStream.builder

class AddUpdateDishActivity : AppCompatActivity() , View.OnClickListener{

    private lateinit var mBinding: ActivityAddUpdateDishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupActionBar()

        mBinding.ivAddDishImage.setOnClickListener(this)
    }
    private fun setupActionBar(){
        setSupportActionBar(mBinding.toolbarAddDishActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mBinding.toolbarAddDishActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(v: View?){
        if (v != null) {
            when(v.id){
                    R.id.iv_add_dish_image->{
                        customImageSelectionDialog()
                        return
                    }
            }
        }
    }
    private fun customImageSelectionDialog(){
        val dialog=Dialog(this)
        val binding:DialogCustomImageSelectionBinding= DialogCustomImageSelectionBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        binding.tvCamera.setOnClickListener {
            Dexter.withActivity(this).withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ).withListener(object :MultiplePermissionsListener{
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted())
                        Toast.makeText(this@AddUpdateDishActivity,"you have camera now",Toast.LENGTH_SHORT)
                            .show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?,
                ) {
                    showRationalDialogForPermission()
                }

            }).onSameThread().check()

            dialog.dismiss()
        }
        binding.tvGallery.setOnClickListener {
            Toast.makeText(this,"you clicked gallery",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }
    private fun showRationalDialogForPermission(){
        AlertDialog.Builder(this).setMessage("looks like you have in denial mode"
        +"make sure you go in settings of the app and turn it on")
            .setPositiveButton("GO to settings"){
                _,_ ->
                try {
                    val intent=Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri=Uri.fromParts("package",packageName,null)
                    intent.data=uri
                    startActivity(intent)
                }catch (e:ActivityNotFoundException){
                    e.printStackTrace()
                }
            }.setNegativeButton("cancel"){
                dialog,_->
                dialog.dismiss()
            }.show()
    }
}