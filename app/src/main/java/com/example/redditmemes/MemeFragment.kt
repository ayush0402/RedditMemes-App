package com.example.redditmemes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.redditmemes.databinding.FragmentMemeBinding


class MemeFragment : Fragment() {

    //connecting the MemeViewModel (Creating the viewModel variable)
    private lateinit var viewModel: MemeViewModel





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMemeBinding>(
            inflater,
            R.layout.fragment_meme, container, false
        )

        //Calling the MemeViewModel via ViewModelProvider
        Log.i("MemeFragment","Called ViewModelProviders")
        viewModel = ViewModelProvider(this).get(MemeViewModel::class.java)

        loadMeme(binding)

        binding.btnNext.setOnClickListener { loadMeme(binding) }
        binding.btnShare.setOnClickListener { shareMeme(viewModel.urlImage) }


        return binding.root
    }


    private fun loadMeme(binding: FragmentMemeBinding) {

        Log.i("MemeFragment","loadMeme function called")

        binding.loading.visibility = View.VISIBLE

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(activity)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                viewModel.urlImage = response.getString("url")
                viewModel.title = response.getString("title")
                viewModel.upvotes = response.getString("ups")

                binding.titleview.text = viewModel.title
                binding.upvotecount.text = viewModel.upvotes

                Glide.with(this).load(viewModel.urlImage).into(binding.memeImageView)

                binding.loading.visibility = View.INVISIBLE
            },
            {
                viewModel.showSnackBar("Connection Error", activity)
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

    }

    private fun shareMeme(urlImage: String?) {

        Log.i("MemeFragment","shareMeme function called")


        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Sent via RedditMeme-App: $urlImage")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }







}