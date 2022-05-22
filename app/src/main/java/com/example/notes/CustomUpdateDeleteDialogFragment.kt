package com.example.notes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notes.databinding.UpdateDeleteDialogBinding
import java.lang.RuntimeException

private const val ARG_ID = "id"
private const val ARG_TITLE = "title"
private const val ARG_NOTE = "note"


class CustomUpdateDeleteDialogFragment : androidx.fragment.app.DialogFragment() {
    private var id: String? = null
    private var title: String? = null
    private var note: String? = null

    lateinit var binding: UpdateDeleteDialogBinding

    private var CustomUpdateDeleteDialogClickListener: OnCustomUpdateDeleteDialogClickListener? = null


    companion object {
        fun newInstance(id: String, title: String, note: String) =
                CustomUpdateDeleteDialogFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_ID, id)
                        putString(ARG_TITLE, title)
                        putString(ARG_NOTE, note)

                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ARG_ID)
            title = it.getString(ARG_TITLE)
            note = it.getString(ARG_NOTE)
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCustomUpdateDeleteDialogClickListener) {
            CustomUpdateDeleteDialogClickListener = context
        } else {
            throw RuntimeException("Please implement OnCustomUpdateDeleteDialogClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        CustomUpdateDeleteDialogClickListener = null
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return LayoutInflater.from(activity).inflate(R.layout.update_delete_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = UpdateDeleteDialogBinding.bind(view)
        val ed_title = binding.updateDeleteDialogEdTitle
        val ed_note = binding.updateDeleteDialogEdNote
        val btn_dialog_update = binding.updateDeleteDialogEdUpdate
        val btn_dialog_delete = binding.updateDeleteDialogEdDelete

        ed_title.setText(title)
        ed_note.setText(note)
        btn_dialog_update.setOnClickListener {
            CustomUpdateDeleteDialogClickListener?.customUpdateDialogClick(
                    id!!,
                    ed_title.text.toString(),
                    ed_note.text.toString()
            )

            dismiss()
        }

        btn_dialog_delete.setOnClickListener {
            CustomUpdateDeleteDialogClickListener?.customDeleteDialogClick(
                    id!!,

            )
            dismiss()
        }

    }

    interface OnCustomUpdateDeleteDialogClickListener {
        fun customUpdateDialogClick(id: String, title: String, note: String)
        fun customDeleteDialogClick(id: String)

    }
}