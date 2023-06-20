package com.richarddewan.kotlincoroutines

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }
    private  val viewMode by viewModels<MainViewMode>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        downloadImageGlobalScope()
        downloadImageCoroutinesScope()
    }

    /**
     * Image downloading in Coroutine Scope Background Thread and view updating in Main thread
     */
    private fun downloadImageCoroutinesScope() {
        CoroutineScope(Dispatchers.IO).launch {
            val url = URL("https://webneel.com/wallpaper/sites/default/files/images/08-2018/3-nature-wallpaper-mountain.1366.jpg")
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            val inputStream = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(inputStream)

            launch(Dispatchers.Main) {
                imageView.setImageBitmap(bitmap)
            }
        }
    }

    /**
     * Image downloading in Global Scope Background thread and view updating in Main thread
     */
    private fun downloadImageGlobalScope() {
        GlobalScope.launch(Dispatchers.IO) {
            val url = URL("https://webneel.com/wallpaper/sites/default/files/images/08-2018/3-nature-wallpaper-mountain.1366.jpg")
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            val inputStream = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(inputStream)

            launch(Dispatchers.Main) {
                imageView.setImageBitmap(bitmap)
            }
        }
    }
}
