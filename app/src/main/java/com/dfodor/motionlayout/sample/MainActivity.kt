package com.dfodor.motionlayout.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dfodor.motionlayout.sample.databinding.ActivityMainBinding
import com.dfodor.motionlayout.sample.sample_1.MusicBandListActivity
import com.dfodor.motionlayout.sample.sample_2.MusicPlayerActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sample1Btn.setOnClickListener {
            startSampleActivity(MusicBandListActivity::class.java)
        }

        binding.sample2Btn.setOnClickListener {
            startSampleActivity(MusicPlayerActivity::class.java)
        }
    }

    private fun startSampleActivity(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }
}