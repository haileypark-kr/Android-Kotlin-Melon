package com.fastcampuskotlin.melon

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.fastcampuskotlin.melon.service.MelonItem

class MelonDetailActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_melon_detail)


		val melonItems =
			intent.getParcelableArrayListExtra<MelonItem>("melon_item_list")

		Log.d("MelonDetailActivity", "onCreate: $melonItems")
	}
}