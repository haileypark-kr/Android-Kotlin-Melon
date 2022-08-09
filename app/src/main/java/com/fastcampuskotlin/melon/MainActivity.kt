package com.fastcampuskotlin.melon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fastcampuskotlin.melon.adapter.MelonListAdapter
import com.fastcampuskotlin.melon.service.MelonItem
import com.fastcampuskotlin.melon.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)


		// retrofit으로 멜론 노래 목록 가져와서 recyclerviwe에 넣어주기


		val retrofit = Retrofit.Builder()
			.baseUrl("http://mellowcode.org/")
			.addConverterFactory(GsonConverterFactory.create())
			.build()
		val retrofitService = retrofit.create(RetrofitService::class.java)


		retrofitService.getMelonItemList()
			.enqueue(object : Callback<ArrayList<MelonItem>> {
				override fun onResponse(
					call: Call<ArrayList<MelonItem>>,
					response: Response<ArrayList<MelonItem>>
				) {
					// response 오면 recyclerview 만들기

					Log.d(TAG, "$response")

					if (response.isSuccessful) {

						val melonItems: ArrayList<MelonItem>? = response.body()

						if (melonItems != null) {

							findViewById<RecyclerView>(R.id.recyclerview_melon).apply {
								this.adapter = MelonListAdapter(
									melonItems,
									LayoutInflater.from(this@MainActivity),
									Glide.with(this@MainActivity),
									this@MainActivity
								)
							}

						}
					}

				}

				override fun onFailure(call: Call<ArrayList<MelonItem>>, t: Throwable) {
					Log.e(TAG, "onFailure: $t")
				}

			})


	}

	companion object {
		private const val TAG = "Melon MainActivity"
	}
}