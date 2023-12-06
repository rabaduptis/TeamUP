package com.root14.teamup.view.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.root14.teamup.data.PrefDataStoreManager
import com.root14.teamup.databinding.FragmentTeamCreateDialogListDialogBinding
import com.root14.teamup.viewmodel.CreateTeamViewModel
import com.root14.teamup.viewmodel.ManageTeamViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    TeamCreateDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
@AndroidEntryPoint
class TeamCreateDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentTeamCreateDialogListDialogBinding? = null

    private val createTeamViewModel: CreateTeamViewModel by activityViewModels()
    private val manageTeamViewModel: ManageTeamViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTeamCreateDialogListDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonCreateTeam.setOnClickListener {
            createTeam()
        }
    }

    private fun createTeam() {
        manageTeamViewModel.getTeamCount().observe(this@TeamCreateDialogFragment) { teamSize ->
            if (teamSize < 4) {
                if (binding.textViewTeamName.text.isNotEmpty()) {
                    createTeamViewModel.createTeam(
                        binding.textViewTeamName.text.toString(),
                        binding.textViewTeamDescription.text.toString()
                    )
                } else {
                    Toast.makeText(
                        binding.root.context, "cannot create team more than 4", Toast.LENGTH_SHORT
                    ).show()
                }
                // Removes the observer ensures that `getTeamCount()` is only called once,,
                // even if the BottomSheetDialogFragment is recreated.
                manageTeamViewModel.getTeamCount().removeObservers(this@TeamCreateDialogFragment)
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


