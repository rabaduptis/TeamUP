package com.root14.teamup.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.root14.teamup.R
import com.root14.teamup.model.TeamModel

class TeamsAdapter(val data: List<TeamModel>) :
    RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {

    inner class TeamsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teamName: TextView = itemView.findViewById(R.id.textView_team_name)
        val teamDescription: TextView = itemView.findViewById(R.id.textView_team_description)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): TeamsAdapter.TeamsViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val teamView0 = inflater.inflate(R.layout.main_teams_custom_view, parent, false)
        return TeamsViewHolder(teamView0)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TeamsAdapter.TeamsViewHolder, position: Int) {
        val team = data[position]
        holder.teamName.text = team.teamName
        holder.teamDescription.text = team.teamDescription
    }

}