package com.example.notes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.notes.databinding.AddNoteDialogBinding
import java.lang.RuntimeException

class CustomAddDialogFragment : androidx.fragment.app.DialogFragment() {

    private var CustomDialogClickListener: OnCustomAddDialogClickListener? = null
    lateinit var binding: AddNoteDialogBinding

    companion object {
        fun newInstance() =
                CustomAddDialogFragment().apply     {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return LayoutInflater.from(activity).inflate(R.layout.add_note_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = AddNoteDialogBinding.bind(view)
        val ed_title = binding.addNoteDialogEdTitle
        val ed_note = binding.addNoteDialogEdNote
        val btn_dialog = binding.addNoteDialogBtnAdd

        btn_dialog.setOnClickListener {
            CustomDialogClickListener?.customAddDialogClick(
                ed_title.text.toString(),
                ed_note.text.toString()
            )

            dismiss()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCustomAddDialogClickListener) {
            CustomDialogClickListener = context
        } else {
            throw RuntimeException("Please implement OnCustomAddDialogClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        CustomDialogClickListener = null

    }


    interface OnCustomAddDialogClickListener {
        fun customAddDialogClick(title: String, note: String)
    }

}