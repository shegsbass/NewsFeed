package com.shegs.newsfeed.screenFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.shegs.newsfeed.R

class IntroThree : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_intro_three, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        val nextScreen = view.findViewById<TextView>(R.id.tv_ready)

        nextScreen.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment2_to_welcomeScreen)
        }

        return view

    }

}