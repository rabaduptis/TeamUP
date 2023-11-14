package com.root14.teamup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.root14.teamup.databinding.ActivityLoginBinding
import com.root14.teamup.databinding.ActivityMainBinding
import com.root14.teamup.model.TeamModel
import com.root14.teamup.view.adapter.TeamsAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val listItems = mutableListOf<TeamModel>()

        for (i in 1..4) {
            listItems.add(TeamModel("team $i", "team description $i"))
        }


        val myManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        val myAdaapter: RecyclerView.Adapter<*> = TeamsAdapter(listItems)

        binding.recyclerViewTeams.layoutManager = myManager;
        binding.recyclerViewTeams.adapter = myAdaapter;
        binding.recyclerViewTeams.setHasFixedSize(true);
    }

}