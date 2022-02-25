package com.example.recyclerviewproject

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewproject.Adapter.CustomAdapter
import com.example.recyclerviewproject.Model.ItemsViewModel
import com.example.recyclerviewproject.fragment.DetailsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerview:RecyclerView
    private lateinit var adapter: CustomAdapter
    private var dataContact=ArrayList<ItemsViewModel>()
    private lateinit var addBtn:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataContact=ArrayList()

        addBtn=findViewById(R.id.addBtn)
        recyclerview = findViewById(R.id.recyclerView)

        dataContact.add(ItemsViewModel("Shubham","8195064422"))

        recyclerview.layoutManager = LinearLayoutManager(this)
        adapter = CustomAdapter(this,dataContact)

        recyclerview.adapter = adapter

        addBtn.setOnClickListener{addInfo()}
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addInfo() {
        val inflater=LayoutInflater.from(this)
        val v=inflater.inflate(R.layout.add_user,null)

        val userName=v.findViewById<EditText>(R.id.userName)
        val userNo=v.findViewById<EditText>(R.id.userNo)

        AlertDialog.Builder(this)
            .setView(v)
            .setPositiveButton("Yes"){
                dialog,_->
                val name=userName.text.toString()
                val no=userNo.text.toString()
                dataContact.add(ItemsViewModel("Name: $name", "Contact: $no"))
                adapter.notifyDataSetChanged()

                val editView=inflater.inflate(R.layout.edit_user,null)
                val newUser=editView.findViewById<EditText>(R.id.newUser)
                val newNo=editView.findViewById<EditText>(R.id.newNo)
                newUser.text=userName.text
                newNo.text=userNo.text

                dialog.dismiss()
            }
            .setNegativeButton("No"){
                dialog,_->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}

