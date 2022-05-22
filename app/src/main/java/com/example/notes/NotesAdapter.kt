package com.example.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.CustomNoteLayoutBinding

class NotesAdapter(var notes: ArrayList<Note>, var listener: OnRecyclerViewItemClickListener) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding =
            CustomNoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = notes[position]

        holder.binding.customNoteLayoutTvTitle.text = note.title

        var newText: String? = ""
        if (note.note!!.length >= 30) {

            for (ch: Char in note.note!!) {
                newText += ch.toString()
                if (newText!!.length == 30) {
                    holder.binding.customNoteLayoutTvNote.text = "$newText ......."
                    break
                }
            }

        } else {
            holder.binding.customNoteLayoutTvNote.text = note.note
        }

        holder.binding.customNoteLayoutTvDate.text = note.timestamp

        holder.binding.root.setOnClickListener {
            listener.onItemClick(position)
        }

        holder.binding.root.setOnLongClickListener {
            listener.onItemLongClick(position)
            true
        }

    }

    override fun getItemCount(): Int {
        return notes.size
    }

    inner class NotesViewHolder(var binding: CustomNoteLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    interface OnRecyclerViewItemClickListener {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }
}