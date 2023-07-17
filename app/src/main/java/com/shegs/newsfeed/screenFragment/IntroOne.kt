package com.shegs.newsfeed.screenFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.shegs.newsfeed.R

open class IntroOne : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_intro_one, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        val nextScreen = view.findViewById<View>(R.id.arrow_next)

        nextScreen.setOnClickListener {
            viewPager?.currentItem = 1
        }

        return view
    }

}