package com.root14.teamup.view.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.root14.teamup.R
import com.root14.teamup.databinding.FragmentTeamShareDialogListDialogItemBinding
import com.root14.teamup.databinding.FragmentTeamShareDialogListDialogBinding

class TeamShareDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentTeamShareDialogListDialogBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTeamShareDialogListDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        /*
        TODO:implement copy 2 clipboard functionality
        val clipboard = view.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        // Creates a Uri using a base Uri and a record ID based on the contact's last
        // name. Declares the base URI string.
        val CONTACTS = "content://com.example.contacts"

        // Declares a path string for URIs, used to copy data.
        val COPY_PATH = "/copy"

        // Declares the Uri to paste to the clipboard.
        val copyUri: Uri = Uri.parse("$CONTACTS$COPY_PATH/$lastName")

        // Creates a new URI clip object. The system uses the anonymous
        // getContentResolver() object to get MIME types from provider. The clip object's
        // label is "URI", and its data is the Uri previously created.
        val clip: ClipData = ClipData.newUri(contentResolver, "URI", copyUri)
        */

        Toast.makeText(view.context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}