package com.root14.teamup.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.root14.teamup.databinding.ActivityTeamDetailBinding
import com.root14.teamup.util.Util

class TeamDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeamDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTeamDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Util.applyWindowInsets(binding.root, this)

        intent.extras?.getString("team-extras").let { extras ->
            Toast.makeText(this, "comin extras: $extras", Toast.LENGTH_SHORT).show()
        }

    }

}