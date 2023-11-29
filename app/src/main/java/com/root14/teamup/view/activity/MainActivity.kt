package com.root14.teamup.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.root14.teamup.data.PrefDataStoreManager
import com.root14.teamup.databinding.ActivityMainBinding
import com.root14.teamup.model.TeamModel
import com.root14.teamup.util.Util
import com.root14.teamup.view.adapter.TeamsAdapter
import com.root14.teamup.view.fragment.TeamCreateDialogFragment
import com.root14.teamup.viewmodel.CreateTeamViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Main activity of the TeamUp application.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    /**
     * Binding for the activity layout.
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * View model for creating teams.
     */
    private val createTeamViewModel: CreateTeamViewModel by viewModels()

    /**
     * Adapter for the list of teams.
     */
    private lateinit var myAdapter: RecyclerView.Adapter<*>

    /**
     * Layout manager for the list of teams.
     */
    private lateinit var myManager: RecyclerView.LayoutManager

    /**
     * List of teams to be displayed.
     */
    private val listItems = mutableListOf<TeamModel>()

    @Inject
    lateinit var prefDataStoreManager: PrefDataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Util.applyWindowInsets(binding.root, this)

        // Set up the recycler view for the list of teams
        myManager = LinearLayoutManager(this@MainActivity)
        myAdapter = TeamsAdapter(listItems, prefDataStoreManager)
        binding.recyclerViewTeams.layoutManager = myManager
        binding.recyclerViewTeams.setHasFixedSize(true)

        // Update the UI with the list of teams
        updateUI(listItems)

        // Observe the UI state of the create team view model
        createTeamViewModel.teamUiState.observe(this) {
            lifecycleScope.launch {
                updateUI(listItems)
            }

        }

        // Set up click listener for the add team button
        binding.floatingActionButtonTeamAdd.setOnClickListener {
            TeamCreateDialogFragment().show(supportFragmentManager, "TeamCreateDialogFragment")
        }

        // Set up listener for the swipe-to-refresh gesture
        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch(Dispatchers.Main) {
                updateUI(listItems)
                binding.swipeRefresh.isRefreshing = false
            }
        }

        myAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                //updateUI(listItems)
            }
        })
    }

    /**
     * Updates the UI with the list of teams.
     */
    private fun updateUI(listItems: MutableList<TeamModel>) {
        lifecycleScope.launch {
            // Get all teams from the data store
            PrefDataStoreManager.getInstance(this@MainActivity).getAllData().collect { teams ->
                // Check if there are any teams
                if (teams.isEmpty()) {
                    // Show the no item message
                    binding.textViewNoItem.visibility = RecyclerView.VISIBLE
                } else {
                    // Hide the no item message
                    binding.textViewNoItem.visibility = RecyclerView.GONE

                    // Clear the list of teams
                    listItems.clear()

                    // Add each team to the list and notify the adapter
                    teams.onEachIndexed { index, team ->
                        listItems.add(TeamModel(team.key.name, team.value.toString()))
                        myAdapter.notifyItemChanged(index)
                    }

                    // Set the adapter for the recycler view
                    binding.recyclerViewTeams.adapter = myAdapter
                }
            }
        }
    }
}
