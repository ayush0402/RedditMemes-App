package com.example.redditmemes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.redditmemes.databinding.FragmentMemeBinding


class MemeFragment : Fragment() {


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
        binding.btnShare.setOnClickListener { shareMeme() }


        return binding.root
    }


    private fun loadMeme(binding: FragmentMemeBinding) {

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(activity)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val urlImage = response.getString("url")
                val title = response.getString("title")
                val upvotes = response.getString("ups")

                binding.titleview.text = title
                binding.upvotecount.text =upvotes

                Glide.with(this).load(urlImage).into(binding.memeImageView)
            },
            {
                binding.memeImageView.setImageResource(R.drawable.ic_launcher_background)
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

    }


    fun shareMeme() {

    }


}