package com.li.jacky.jackydatepicker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.li.jacky.library.DateBoard

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DateBoard(this))
    }
}
