package com.root14.teamup.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.root14.teamup.data.PrefDataStoreManager
import com.root14.teamup.databinding.FragmentTeamCreateDialogListDialogBinding
import com.root14.teamup.viewmodel.CreateTeamViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


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

    private val createTeamViewModel: CreateTeamViewModel by viewModels()

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


            lifecycleScope.launch {
                PrefDataStoreManager.getInstance(binding.root.context).getAllData()
                    .collect { teams ->
                        if (teams.size < 4) {
                            if (binding.textViewTeamName.text.isNotEmpty()) {
                                createTeamViewModel.createTeam(
                                    binding.textViewTeamName.text.toString(),
                                    binding.textViewTeamDescription.text.toString()
                                )
                            }
                        } else {
                            Toast.makeText(
                                binding.root.context,
                                "cannot create team more than 4",
                                Toast.LENGTH_SHORT
                            ).show()
                            dismiss()
                        }

                    }
            }


        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}