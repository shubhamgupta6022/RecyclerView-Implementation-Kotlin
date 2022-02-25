package com.example.recyclerviewproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.recyclerviewproject.R

class DetailsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var detailName:TextView
    private lateinit var detailNo:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("NAME")
            param2 = it.getString("CONTACT")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v=inflater.inflate(R.layout.fragment_details, container, false)

        detailName=v.findViewById(R.id.detailName)
        detailNo=v.findViewById(R.id.detailNo)

        detailName.text=param1
        detailNo.text=param2

        return v
    }
}