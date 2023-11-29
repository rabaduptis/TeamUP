package com.root14.teamup.view.adapter

import Navigator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.root14.teamup.R
import com.root14.teamup.data.PrefDataStoreManager
import com.root14.teamup.databinding.ActivityMainBinding
import com.root14.teamup.databinding.FragmentTeamCreateDialogListDialogBinding
import com.root14.teamup.databinding.MainTeamsCustomViewBinding
import com.root14.teamup.model.TeamModel
import com.root14.teamup.view.activity.TeamDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import javax.inject.Inject

class TeamsAdapter(
    private var data: MutableList<TeamModel>, private var prefDataStoreManager: PrefDataStoreManager
) : RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {

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
                TeamDetailActivity::class.java, extras
            )
        }
    }

    override fun onViewAttachedToWindow(holder: TeamsViewHolder) {
        super.onViewAttachedToWindow(holder)
        binding.imgButtonDelete.setOnClickListener {
            data.removeAt(holder.bindingAdapterPosition)
            notifyItemRemoved(holder.bindingAdapterPosition)
            notifyItemRangeRemoved(0, holder.bindingAdapterPosition)

            holder.itemView.findViewTreeLifecycleOwner()?.lifecycleScope?.launch(Dispatchers.IO) {
                //fixme: When this line runs, the recyclerview deletion animation does not work.
                prefDataStoreManager.deleteData(holder.teamName.text.toString())
                println("hey douglas look successfully deleted!")
            }
        }

    }
}