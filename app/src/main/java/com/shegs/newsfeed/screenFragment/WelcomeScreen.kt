package com.shegs.newsfeed.screenFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.shegs.newsfeed.R
import com.shegs.newsfeed.UploadActivity


class WelcomeScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_welcome_screen, container, false)

        val openFeedBtn = view.findViewById<Button>(R.id.btn_feeds)
        openFeedBtn.setOnClickListener {
            val fragment = FeedFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
            fragmentTransaction.commit()

        }

        val uploadImageBtn = view.findViewById<Button>(R.id.btn_upload_img)
        uploadImageBtn.setOnClickListener {
            val intent = Intent(activity, UploadActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}