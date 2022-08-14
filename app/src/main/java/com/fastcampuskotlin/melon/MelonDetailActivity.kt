package com.fastcampuskotlin.melon

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fastcampuskotlin.melon.service.MelonItem

class MelonDetailActivity : AppCompatActivity() {

	lateinit var playPauseButton: ImageView
	lateinit var mediaPlayer: MediaPlayer
	lateinit var melonItems: ArrayList<MelonItem>
	var melonItemPosition: Int = 0
		set(value) {
			if (value < 0) {
				field = melonItems.size - 1
				Toast.makeText(this, "마지막 곡을 재생합니다.", Toast.LENGTH_LONG).show()
			} else if (value >= melonItems.size) {
				field = 0
				Toast.makeText(this, "첫 곡을 재생합니다.", Toast.LENGTH_LONG).show()
			} else {
				field = value
			}
		}

	var isPlaying: Boolean = true
		set(value) {
			if (value) {
				// 정지 버튼
				playPauseButton.setImageDrawable(
					this.resources.getDrawable(R.drawable.pause, this.theme)
				)

			} else {
				// 재생 버튼
				playPauseButton.setImageDrawable(
					this.resources.getDrawable(R.drawable.play, this.theme)
				)
			}

			field = value
		}

	var pausePosition: Int = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_melon_detail)

		// 넘겨받은 extras 받아서, 음악 재생
		val melonItems = intent.getParcelableArrayListExtra<MelonItem>("melon_item_list")
		if (melonItems != null) {
			this.melonItems = melonItems
		}

		melonItemPosition = intent.getIntExtra("position", 0)

		playMediaPlayer()
		changeThumbnail()

		// 음악 재생 버튼 setOnClickListener
		playPauseButton = findViewById<ImageView>(R.id.imageview_pause)
		playPauseButton.setOnClickListener {
			isPlaying = !isPlaying

			if (!isPlaying) {
				// 현재 위치 파악 후 일시정지
				pausePosition = mediaPlayer.currentPosition
				mediaPlayer.pause()
			} else {
				// 이전 재생중이던 위치로 이동 후 다시 재생
				mediaPlayer.seekTo(pausePosition)
				mediaPlayer.start()
			}
		}

		// 다음 버튼 setOnClickListener
		(findViewById<ImageView>(R.id.imageview_next)).setOnClickListener {
			mediaPlayer.stop()
			melonItemPosition++
			playMediaPlayer()
			changeThumbnail()
		}

		// 이전 버튼 setOnClickListener
		(findViewById<ImageView>(R.id.imageview_prev)).setOnClickListener {
			mediaPlayer.stop()
			melonItemPosition--
			playMediaPlayer()
			changeThumbnail()
		}

	}


	fun playMediaPlayer() {
		mediaPlayer = MediaPlayer.create(
			this,
			Uri.parse(melonItems[melonItemPosition].song)
		)
		mediaPlayer.start()
	}

	fun changeThumbnail() {
		findViewById<ImageView>(R.id.thumbnail).apply {
			val glide = Glide.with(this@MelonDetailActivity)
			glide.load(melonItems[melonItemPosition].thumbnail).into(this)
		}
	}


}