package com.root14.teamup.view.adapter

import Navigator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.root14.teamup.databinding.MainTeamsCustomViewBinding
import com.root14.teamup.model.entity.TeamModel
import com.root14.teamup.view.activity.TeamDetailActivity
import com.root14.teamup.view.fragment.TeamShareDialogFragment
import com.root14.teamup.viewmodel.ManageTeamViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamsAdapter(
    private var data: MutableList<TeamModel>, private val manageTeamViewModel: ManageTeamViewModel
) : RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {

    private lateinit var binding: MainTeamsCustomViewBinding

    inner class TeamsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teamName: TextView = binding.teamName
        val teamDescription: TextView = binding.teamDescription
        val teamBaseLayout: LinearLayout = binding.linearLayoutBase
        val teamShareButton: ImageButton = binding.shareButton
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

        holder.teamShareButton.setOnClickListener {
            val fragmentActivity = it.context as FragmentActivity
            val fragmentManager = fragmentActivity.supportFragmentManager

            TeamShareDialogFragment().show(fragmentManager, "TeamShareDialogFragment")
        }

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
        binding.deleteButton.setOnClickListener {
            data.removeAt(holder.bindingAdapterPosition)
            manageTeamViewModel.deleteTeam(holder.teamName.text.toString())
            notifyItemRemoved(holder.bindingAdapterPosition)
            notifyItemRangeRemoved(0, holder.bindingAdapterPosition)
        }

    }
}