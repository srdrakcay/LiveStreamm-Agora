package com.serdar.livestreamm_agora

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.get
import com.serdar.livestreamm_agora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        selectedChipRole()
    }

    private fun selectedChipRole() {
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.audience -> {
                    binding.btnStart.setOnClickListener {
                        val intent = Intent(this, LiveStream::class.java)
                        intent.putExtra("role", "Audience")
                        startActivity(intent)
                    }

                }
                R.id.broadcaster -> {
                    binding.btnStart.setOnClickListener {
                        val intent = Intent(this, LiveStream::class.java)
                        intent.putExtra("role", "Broadcaster")
                        startActivity(intent)
                    }

                }
            }
        }
    }
}
