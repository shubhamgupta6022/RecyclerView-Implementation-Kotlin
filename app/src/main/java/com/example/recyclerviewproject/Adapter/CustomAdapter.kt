package com.example.recyclerviewproject.Adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewproject.fragment.DetailsFragment
import com.example.recyclerviewproject.Model.ItemsViewModel
import com.example.recyclerviewproject.R
import android.os.Bundle

class CustomAdapter(val c: Context, val mList: ArrayList<ItemsViewModel>):
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    inner class ViewHolder(v:View) : RecyclerView.ViewHolder(v) {
        val fullName=v.findViewById<TextView>(R.id.fullName)
        val userContact: TextView = v.findViewById(R.id.phoneNo)
        val mMenu=v.findViewById<ImageView>(R.id.mMenus)

        init {
            mMenu.setOnClickListener { popupMenus(it) }
        }

        private fun popupMenus(v:View) {
            val position=mList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText->{
                        val editView=LayoutInflater.from(c).inflate(R.layout.edit_user,null)
                        val v=LayoutInflater.from(c).inflate(R.layout.add_user,null)

                        val userName=v.findViewById<EditText>(R.id.userName)
                        val userNo=v.findViewById<EditText>(R.id.userNo)
                        val newUser=editView.findViewById<EditText>(R.id.newUser)
                        val newNo=editView.findViewById<EditText>(R.id.newNo)

                        newUser.text=userName.text
                        newNo.text=userNo.text

                        AlertDialog.Builder(c)
                            .setView(editView)
                            .setPositiveButton("Ok"){
                                dialog, _->
                                position.userName="Name: "+newUser.text.toString()
                                position.userMb="Contact: "+newNo.text.toString()
                                notifyDataSetChanged()
                                dialog.dismiss()
                            }
                            .setNegativeButton("Cancel"){
                                dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    R.id.delete->{
                        AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_delete)
                            .setMessage("Do you want to delete?")
                            .setPositiveButton("Yes"){
                                dialog,_->
                                mList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    else->true
                }
            }
            popupMenus.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.new_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        holder.fullName.text=ItemsViewModel.userName
        holder.userContact.text = ItemsViewModel.userMb

        val detailsFragment = DetailsFragment()
        val bundle = Bundle()
        bundle.putString("NAME", ItemsViewModel.userName)
        bundle.putString("CONTACT", ItemsViewModel.userMb)
        detailsFragment.setArguments(bundle)

        holder.itemView.setOnClickListener {
            val activity= it.context as AppCompatActivity
            val fragmentLayout=LayoutInflater.from(c).inflate(R.layout.fragment_details,null)
            val detailName=fragmentLayout.findViewById<TextView>(R.id.detailName)
            detailName.text=holder.fullName.text.toString()
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainLayout, detailsFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}

