package com.fastcampuskotlin.melon.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.fastcampuskotlin.melon.MelonDetailActivity
import com.fastcampuskotlin.melon.R
import com.fastcampuskotlin.melon.service.MelonItem

class MelonListAdapter(
	val melonItems: ArrayList<MelonItem>,
	val inflater: LayoutInflater,
	val glide: RequestManager,
	val context: Context
) : RecyclerView.Adapter<MelonListAdapter.ViewHolder>() {

	inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
		val title: TextView
		val thumbnail: ImageView
		val singer: TextView

		init {
			title = itemView.findViewById(R.id.title)
			thumbnail = itemView.findViewById(R.id.thumbnail)
			singer = itemView.findViewById(R.id.singer)

			itemview.setOnClickListener {
				Log.d("ViewHolder", "$adapterPosition")

				val intent = Intent(context, MelonDetailActivity::class.java)
				intent.putParcelableArrayListExtra("melon_item_list", melonItems)
				intent.putExtra("position", adapterPosition)
				context.startActivity(intent)
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(inflater.inflate(R.layout.melon_list_item, parent, false))
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.title.text = melonItems[position].title
		holder.singer.text = melonItems[position].title

		glide.load(melonItems[position].thumbnail)
			.centerCrop()
			.into(holder.thumbnail)
	}

	override fun getItemCount(): Int {
		return melonItems.size
	}

}

