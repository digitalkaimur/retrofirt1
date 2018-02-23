package com.retrofitsimple.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.immigration.restservices.APIService
import com.immigration.restservices.ApiUtils
import com.retrofitdemo.retrofit.model.ResponseModel
import com.retrofitsimple.R
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*


class MultipartProfile : AppCompatActivity() {
   
   lateinit var apiService: APIService
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)
      apiService = ApiUtils.apiService
      initJson()
   }
   
   
   
   private fun initJson() {
      val bitmapImageLocal = BitmapFactory.decodeResource(applicationContext.resources, R.mipmap.ic_launcher)
      
      val file = onCaptureImageResult(bitmapImageLocal)
      val mFile = RequestBody.create(MediaType.parse("image/*"), file)
      val fileToUpload = MultipartBody.Part.createFormData("profilePic", file.getName(), mFile)
     // val filename = RequestBody.create(MediaType.parse("text/plain"), file.getName())
      
      
      val firstName="KArun"
      val lastName="Singh"
      val countryCodeValues="+91"
      val contactValues="8920828585"
      val accessToken="10448859925a8d6d090c67f"
   
      val fname = RequestBody.create(MediaType.parse("text/plain"), firstName)
      val lname = RequestBody.create(MediaType.parse("text/plain"), lastName)
      val cnt_code = RequestBody.create(MediaType.parse("text/plain"),countryCodeValues)
      val mobile = RequestBody.create(MediaType.parse("text/plain"), contactValues)
   
      apiService.postImage(accessToken,fileToUpload,fname,lname,mobile,cnt_code)
       .enqueue(object : retrofit2.Callback<ResponseModel> {
          
          override fun onResponse(call: retrofit2.Call<ResponseModel>?, response: retrofit2.Response<ResponseModel>?) {
             Log.d("TAGS",response!!.body().message )
          }
          
          override fun onFailure(call: retrofit2.Call<ResponseModel>?, t: Throwable?) {
             Log.d("TAGS", t.toString())
          }
       })
   }
   
   private fun onCaptureImageResult(bitmap: Bitmap): File {
      val imgFile: File
      val bytes = ByteArrayOutputStream()
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
      imgFile = File(Environment.getExternalStorageDirectory(),
       System.currentTimeMillis().toString() + ".jpg")
      val fo: FileOutputStream
      try {
         imgFile.createNewFile()
         fo = FileOutputStream(imgFile)
         fo.write(bytes.toByteArray())
         fo.close()
      } catch (e: FileNotFoundException) {
         e.printStackTrace()
      } catch (e: IOException) {
         e.printStackTrace()
      }
      return imgFile
   }
   
}

