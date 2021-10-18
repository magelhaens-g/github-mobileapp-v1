package com.dvlpr.githubapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dvlpr.githubapp.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnBack.setOnClickListener {
                this@AboutActivity.finish()
            }
        }
    }
}