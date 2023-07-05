package com.shegs.newsfeed.fragment

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shegs.newsfeed.R


class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val view = view.findViewById<View>(R.id.gradientView)

        val startColor = Color.parseColor("#3276A7") // Start color (red)
        val endColor = Color.parseColor("#125178")   // End color (blue)

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,  // Gradient orientation (left to right)
            intArrayOf(startColor, endColor)          // Color array for the gradient
        )

        view.background = gradientDrawable
    }
}