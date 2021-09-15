package com.example.k_ovid_map

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ListView

// MainActivity.kt
class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var listView : ListView
    lateinit var adapter: ListViewAdapter
    lateinit var lats: ArrayList<String>
    lateinit var lngs: ArrayList<String>
    lateinit var name: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listview)
        editText = findViewById(R.id.edittext)

    }

    fun onClickButton(view: View){
        val thread = Thread {
            var jsonUrl = JsonUrl(editText.text.toString())
            adapter = jsonUrl.main()
            name = jsonUrl.name
            lats = jsonUrl.lats
            lngs = jsonUrl.lngs
            runOnUiThread {
                listView.adapter = adapter
            }
        }.start()
    }

    fun onClickButton2(view: View){
        var intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("lats",lats)
        intent.putExtra("lngs",lngs)
        intent.putExtra("name",name)
        startActivity(intent)
    }

}