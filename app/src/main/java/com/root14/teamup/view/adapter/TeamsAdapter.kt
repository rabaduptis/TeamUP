package com.root14.teamup.view.adapter

import Navigator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.root14.teamup.R
import com.root14.teamup.databinding.ActivityMainBinding
import com.root14.teamup.databinding.FragmentTeamCreateDialogListDialogBinding
import com.root14.teamup.databinding.MainTeamsCustomViewBinding
import com.root14.teamup.model.TeamModel
import com.root14.teamup.view.activity.TeamDetailActivity

class TeamsAdapter(private val data: List<TeamModel>) :
    RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {

    private lateinit var binding: MainTeamsCustomViewBinding

    inner class TeamsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teamName: TextView = binding.textViewTeamName
        val teamDescription: TextView = binding.textViewTeamDescription
        val teamBaseLayout: ConstraintLayout = binding.constraitLayoutTeamBase
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): TeamsAdapter.TeamsViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        binding = MainTeamsCustomViewBinding.inflate(inflater)
        return TeamsViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TeamsAdapter.TeamsViewHolder, position: Int) {
        val team = data[position]
        holder.teamName.text = team.teamName
        holder.teamDescription.text = team.teamDescription

        holder.teamBaseLayout.setOnClickListener {

            val extras = Bundle()
            extras.putString("team-extras", team.teamName)

            Navigator<TeamDetailActivity>(it.context).navigateTo(
                TeamDetailActivity::class.java,
                extras
            )
        }
    }

}