package com.example.crudexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class LVMessageAdapter(val messageList : MutableList<ModelMessage>) : BaseAdapter(){
    override fun getCount(): Int {
        return messageList.size
    }

    override fun getItem(position: Int): Any {
        return messageList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView

        if(view == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.lv_message_item, parent, false)
        }

        val tvMessage : TextView = view!!.findViewById(R.id.tvMessage)
        tvMessage.text = messageList[position].content

        return view!!

    }

}