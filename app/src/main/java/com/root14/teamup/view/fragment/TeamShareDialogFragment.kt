package com.root14.teamup.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.root14.teamup.databinding.FragmentTeamShareDialogListDialogBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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

        lifecycleScope.launch(Dispatchers.Main) {
            generateQrCode(binding.imageViewBarcode, "sample-data")
        }

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

        binding.imageButtonShare.setOnClickListener{
            Toast.makeText(view.context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Generates a QR code image and displays it in the given ImageView.
     *
     * @param imageViewTo The ImageView to display the QR code in.
     * @param content The content to encode in the QR code.
     */
    private fun generateQrCode(imageViewTo: ImageView, content: String) {
        try {
            // Create a BarcodeEncoder instance
            val barcodeEncoder = BarcodeEncoder()

            // Encode the content into a QR code bitmap
            val bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 250, 250)

            // Set the QR code bitmap to the ImageView
            imageViewTo.setImageBitmap(bitmap)
        } catch (exception: Exception) {
            // Print an error message if the QR code generation fails
            println("fail to generate QR")
            exception.printStackTrace()
        }
    }

}