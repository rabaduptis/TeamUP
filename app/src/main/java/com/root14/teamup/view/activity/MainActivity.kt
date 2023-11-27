package com.root14.teamup.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.root14.teamup.data.PrefDataStoreManager
import com.root14.teamup.databinding.ActivityMainBinding
import com.root14.teamup.model.TeamModel
import com.root14.teamup.util.Util
import com.root14.teamup.view.adapter.TeamsAdapter
import com.root14.teamup.view.fragment.TeamCreateDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
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

        println("bla")

        binding.swipeRefresh.setOnRefreshListener {

            println("look douglas! it is working!")
            GlobalScope.launch {
                delay(5000)
                binding.swipeRefresh.isRefreshing = false
            }
        }

    }
}