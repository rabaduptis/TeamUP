package com.root14.teamup.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import com.root14.teamup.viewmodel.CreateTeamViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val createTeamViewModel: CreateTeamViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Util.applyWindowInsets(binding.root, this)

        val listItems = mutableListOf<TeamModel>()

        val myManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        val myAdapter: RecyclerView.Adapter<*> = TeamsAdapter(listItems)

        updateUI(listItems)

        binding.recyclerViewTeams.layoutManager = myManager
        binding.recyclerViewTeams.adapter = myAdapter
        binding.recyclerViewTeams.setHasFixedSize(true)

        binding.floatingActionButtonTeamAdd.setOnClickListener {
            val modelBottomSheet = TeamCreateDialogFragment()
            modelBottomSheet.show(supportFragmentManager, "TeamCreateDialogFragment")
        }

        //TODO:not working like swipe refresh
        createTeamViewModel.createTeamUiState.observe(this) { it ->
            if (!it.isError) {
                updateUI(listItems)
                myAdapter.notifyDataSetChanged()
                //binding.textViewNoItem.visibility = RecyclerView.GONE
            }

        }

        binding.swipeRefresh.setOnRefreshListener {
            println("look douglas! it is working!")
            lifecycleScope.launch(Dispatchers.Main) {
                myAdapter.notifyDataSetChanged()
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun updateUI(listItems: MutableList<TeamModel>) {
        lifecycleScope.launch {
            PrefDataStoreManager.getInstance(this@MainActivity).getAllData().collect { teams ->
                if (teams.isEmpty()) {
                    binding.textViewNoItem.visibility = RecyclerView.VISIBLE
                } else {
                    binding.textViewNoItem.visibility = RecyclerView.GONE
                    teams.forEach { team ->
                        listItems.add(TeamModel(team.key.name, team.value.toString()))
                    }
                }
            }
        }
    }
}