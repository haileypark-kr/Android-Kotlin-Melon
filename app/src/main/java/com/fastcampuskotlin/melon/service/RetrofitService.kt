package com.fastcampuskotlin.melon.service

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Call
import retrofit2.http.GET


@Parcelize
class MelonItem(
	val id: Int,
	val title: String,
	val song: String,
	val thumbnail: String
) : Parcelable


interface RetrofitService {

	@GET("melon/list/")
	fun getMelonItemList(): Call<ArrayList<MelonItem>>

}