package com.example.memey

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.android.volley.Request
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currentUrl: String? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadmeme()
    }

    fun loadmeme()
    {
        val textView = findViewById<TextView>(R.id.text)
        // ...
        val progressbar = findViewById<ProgressBar>(R.id.progressbar);
        progressbar.visibility = View.VISIBLE;
        // Instantiate the RequestQueue.
        currentUrl = "https://meme-api.herokuapp.com/gimme"
        val imageView = findViewById<ImageView>(R.id.imageView)
        // Request a string response from the provided URL.
        val jasonObjectRequest = JsonObjectRequest(
            Request.Method.GET, currentUrl,null,
            { response ->
                val url = response.getString("url")
                progressbar.visibility = View.GONE;
                Glide.with(this).load(url).listener(object : RequestListener<Drawable>
                {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE;
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE;
                        return false
                    }

                }).into(imageView)
            },
            {
                Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            })

        // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jasonObjectRequest)
    }
    fun nextmeme(view: android.view.View) {
        loadmeme()
    }
    fun sharememe(view: android.view.View) {
        val intent = Intent(Intent.ACTION_SEND);
        intent.type = "text/plain";
        intent.putExtra(Intent.EXTRA_TEXT, "Hey, I got a very cool meme on Reddit, click on $currentUrl");
        val chooser = Intent.createChooser(intent, "Share the meme via ...");
        startActivity(chooser);
    }
}