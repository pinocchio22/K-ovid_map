package com.example.k_ovid_map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-09-15
 * @desc
 */
// ListViewAdapter.kt
class ListViewAdapter : BaseAdapter() {

    var list = ArrayList<ListViewItem>()

    override fun getCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int) : Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return list.get(position)
    }

    fun getSize(): Int {
        return list.size
    }

    fun addItem(center: String, centeraddr: String) {
        var item = ListViewItem(center, centeraddr)
        list.add(item)

        println("in ListViewAdapter --> listSize : "+list.size)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var context = parent?.getContext()
        var convertV = convertView

        if (convertView == null) {
            val systemService =
                context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertV = systemService.inflate(R.layout.listview,parent,false)
        }

        var center = convertV?.findViewById(R.id.listview_text1) as TextView
        var centeraddr = convertV?.findViewById(R.id.listview_text2) as TextView

        val listViewItem = list[position]

        center.text = listViewItem.getcenter()
        centeraddr.text = listViewItem.getcenteraddr()

        return convertV

    }

}