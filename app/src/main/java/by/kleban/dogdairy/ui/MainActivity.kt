package by.kleban.dogdairy.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kleban.dogdairy.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}