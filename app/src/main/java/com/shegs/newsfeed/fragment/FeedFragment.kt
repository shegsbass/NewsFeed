package com.shegs.newsfeed.fragment

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.shegs.newsfeed.APIRequest
import com.shegs.newsfeed.R
import com.shegs.newsfeed.adapter.RecyclerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://newsapi.org/"
class FeedFragment : Fragment() {

    lateinit var countDownTimer: CountDownTimer

    private var titlesList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imagesList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val view = view.findViewById<View>(R.id.gradientView)

        val startColor = Color.parseColor("#3276A7") // Start color (red)
        val endColor = Color.parseColor("#125178")   // End color (blue)

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,  // Gradient orientation (left to right)
            intArrayOf(startColor, endColor)          // Color array for the gradient
        )

        view.background = gradientDrawable


        makeAPIRequest()

        val swipeRefreshLayout: SwipeRefreshLayout = requireView().findViewById(R.id.swipeRefresh)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            makeAPIRequest()
            swipeRefreshLayout.isRefreshing = false
        }

    }

    private fun addToList(title: String, description: String, image: String) {
        titlesList.add(title)
        descList.add(description)
        imagesList.add(image)
    }

    private fun setUpRecyclerView(){
        val rvRecyclerView = view?.findViewById<RecyclerView>(R.id.rvRecyclerView)
        if (rvRecyclerView != null) {
            rvRecyclerView.layoutManager = LinearLayoutManager(activity)
        }
        if (rvRecyclerView != null) {
            rvRecyclerView.adapter = RecyclerAdapter(titlesList, descList, imagesList)
        }
        val animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_animation) // Replace 'context' with your appropriate context
        rvRecyclerView?.startAnimation(animation)

    }

    private fun makeAPIRequest(){
        val progressBar = view?.findViewById<ProgressBar>(R.id.progressBar)
        if (progressBar != null) {
            progressBar.visibility = View.VISIBLE
        }

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getNews()

                for (article in response.articles){
                    Log.i("FeedFragment", "Result = $article")
                    addToList(article.title, article.description, article.urlToImage)
                }
                withContext(Dispatchers.Main){
                    setUpRecyclerView()
                    if (progressBar != null) {
                        progressBar.visibility = View.GONE
                    }
                }

            }catch (e:Exception){
                Log.e("FeedFragment", e.toString())

                withContext(Dispatchers.Main){
                    attemptRequestAgain()
                }
            }
        }
    }

    private fun attemptRequestAgain(){
        countDownTimer = object: CountDownTimer(5*1000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                Log.i("FeedFragment", "Could not retrieve data... trying again in ${millisUntilFinished/1000} seconds")
            }

            override fun onFinish() {
                makeAPIRequest()
                countDownTimer.cancel()
            }

        }
        countDownTimer.start()
    }


}