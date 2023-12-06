package com.root14.teamup.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.root14.teamup.data.PrefDataStoreManager
import com.root14.teamup.data.repo.TeamRepo
import com.root14.teamup.databinding.ActivityMainBinding
import com.root14.teamup.model.entity.TeamModel
import com.root14.teamup.util.Util
import com.root14.teamup.view.adapter.TeamsAdapter
import com.root14.teamup.view.fragment.TeamCreateDialogFragment
import com.root14.teamup.viewmodel.CreateTeamViewModel
import com.root14.teamup.viewmodel.ManageTeamViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
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

    @Inject
    lateinit var teamRepo: TeamRepo

    private val manageTeamViewModel: ManageTeamViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Util.applyWindowInsets(binding.root, this)

        // Set up the recycler view for the list of teams
        myManager = LinearLayoutManager(this@MainActivity)
        myAdapter = TeamsAdapter(listItems, manageTeamViewModel)
        binding.recyclerViewTeams.layoutManager = myManager
        binding.recyclerViewTeams.setHasFixedSize(true)

        // Update the UI with the list of teams
        updateUI()

        // Observe the UI state of the create team view model
        createTeamViewModel.teamUiState.observe(this) {
            if (!it.isError) {
                updateUI()
            }
        }

        manageTeamViewModel.deleteTeamUiState.observe(this) {
            if (!it.isError) {
                updateUI()
            }
        }

        // Set up click listener for the add team button
        binding.floatingActionButtonTeamAdd.setOnClickListener {
            TeamCreateDialogFragment().show(supportFragmentManager, "TeamCreateDialogFragment")
        }

        // Set up listener for the swipe-to-refresh gesture
        binding.swipeRefresh.setOnRefreshListener {
            updateUI()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    /**
     * Updates the UI with the list of teams.
     */
    private fun updateUI() {
        lifecycleScope.launch(Dispatchers.Main) {

            manageTeamViewModel.getAllTeam().observe(this@MainActivity) {
                // Check if there are any teams
                if (it.isNullOrEmpty()) {
                    // Clear the list of teams
                    listItems.clear()
                    // Show the no item message
                    binding.textViewNoItem.visibility = RecyclerView.VISIBLE
                } else {
                    // Hide the no item message
                    binding.textViewNoItem.visibility = RecyclerView.INVISIBLE

                    // Clear the list of teams
                    listItems.clear()

                    // Add each team to the list and notify the adapter
                    it?.onEachIndexed { index, team ->
                        listItems.add(team)
                        myAdapter.notifyItemChanged(index)
                    }

                    // Set the adapter for the recycler view
                    binding.recyclerViewTeams.adapter = myAdapter
                }
            }
        }
    }
}
