package com.example.redditmemes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.redditmemes.databinding.FragmentMemeBinding
import com.google.android.material.snackbar.Snackbar


class MemeFragment : Fragment() {

    var urlImage: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMemeBinding>(
            inflater,
            R.layout.fragment_meme, container, false
        )


        loadMeme(binding)

        binding.btnNext.setOnClickListener { loadMeme(binding) }
        binding.btnShare.setOnClickListener { shareMeme(urlImage) }


        return binding.root
    }


    private fun loadMeme(binding: FragmentMemeBinding) {

        binding.loading.visibility = View.VISIBLE

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(activity)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                urlImage = response.getString("url")
                val title = response.getString("title")
                val upvotes = response.getString("ups")

                binding.titleview.text = title
                binding.upvotecount.text = upvotes

                Glide.with(this).load(urlImage).into(binding.memeImageView)

                binding.loading.visibility = View.INVISIBLE
            },
            {
                showSnackBar("Connection Error", activity)
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

    }


    private fun shareMeme(urlImage: String?) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Sent via RedditMeme-App: $urlImage")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun showSnackBar(message: String?, activity: Activity?) {
        if (null != activity && null != message) {
            Snackbar.make(
                activity.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT
            ).show()
        }
    }


}