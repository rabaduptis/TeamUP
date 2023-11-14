package com.root14.teamup.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.root14.teamup.databinding.ActivityTeamDetailBinding

class TeamDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeamDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTeamDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}