package com.root14.teamup.view.activity

import Navigator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.root14.teamup.util.Util
import com.root14.teamup.databinding.ActivityMainBinding
import com.root14.teamup.model.TeamModel
import com.root14.teamup.view.adapter.TeamsAdapter
import com.root14.teamup.view.fragment.TeamCreateDialogFragment
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Util.applyWindowInsets(binding.root, this)

        val listItems = mutableListOf<TeamModel>()

        for (i in 0..3) {
            listItems.add(
                TeamModel(
                    "team default $i", "team default description ${Random.nextInt().toUInt()}"
                )
            )
        }

        val myManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        val myAdapter: RecyclerView.Adapter<*> = TeamsAdapter(listItems)

        binding.recyclerViewTeams.layoutManager = myManager
        binding.recyclerViewTeams.adapter = myAdapter
        binding.recyclerViewTeams.setHasFixedSize(true)


        binding.floatingActionButtonTeamAdd.setOnClickListener {
            val modelBottomSheet = TeamCreateDialogFragment()
            modelBottomSheet.show(supportFragmentManager, "TeamCreateDialogFragment")

        }
    }
}