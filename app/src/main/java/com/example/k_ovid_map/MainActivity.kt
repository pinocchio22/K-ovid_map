package com.example.k_ovid_map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ListView

// MainActivity.kt
class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var listView : ListView
    var adapter : ListViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listview)
        editText = findViewById(R.id.edittext)

    }

    fun onClickButton(view: View){
        val thread = Thread({
            var apiPublicMask = JsonUrl(editText.text.toString())
            adapter = apiPublicMask.main()
            runOnUiThread {
                listView.adapter = adapter
            }
        }).start()
    }

}