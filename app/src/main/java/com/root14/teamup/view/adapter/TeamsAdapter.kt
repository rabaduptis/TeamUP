package com.root14.teamup.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.root14.teamup.R
import com.root14.teamup.model.TeamModel

class TeamsAdapter(private val data: List<TeamModel>) :
    RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {

    inner class TeamsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teamName: TextView = itemView.findViewById(R.id.textView_team_name)
        val teamDescription: TextView = itemView.findViewById(R.id.textView_team_description)
        val teamBaseLayout: ConstraintLayout = itemView.findViewById(R.id.constraitLayout_team_base)
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

        holder.teamBaseLayout.setOnClickListener {
            Toast.makeText(it.context, "clicked $position", Toast.LENGTH_LONG).show()
        }
    }

}