package com.example.notes

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.databinding.ActivityMainBinding
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), CustomAddDialogFragment.OnCustomAddDialogClickListener, CustomUpdateDeleteDialogFragment.OnCustomUpdateDeleteDialogClickListener {
    lateinit var binding: ActivityMainBinding
    lateinit var customAddDialogFragment: CustomAddDialogFragment
    lateinit var customUpdateDeleteDialogFragment: CustomUpdateDeleteDialogFragment
    lateinit var database: FirebaseDatabase
    lateinit var mRef: DatabaseReference
    lateinit var mNotes: ArrayList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        mNotes = ArrayList()

        database = FirebaseDatabase.getInstance()
        mRef = database.getReference("Notes")

        binding.activityMainFab.setOnClickListener {
            /*val alertBuilder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.add_note_dialog, null)
            alertBuilder.setView(view)

            val alertDialog = alertBuilder.create()
            alertDialog.show()*/

            // بطريقة احسن
            showAddDialog()

        }


    }

    override fun onStart() {
        super.onStart()
        mRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                mNotes.clear()
                for (n in snapshot.children) {
                    val note = n.getValue(Note::class.java)
                    if (note != null) {
                        mNotes.add(0, note)
                    }


                }

                val adapter =
                        NotesAdapter(mNotes, object : NotesAdapter.OnRecyclerViewItemClickListener {
                            override fun onItemClick(position: Int) {
                                val note: Note = mNotes[position]

                                val intent = Intent(baseContext, NoteActivity::class.java)
                                intent.putExtra("title", note.title)
                                intent.putExtra("note", note.note)
                                intent.putExtra("date", note.timestamp)
                                startActivity(intent)
                            }

                            override fun onItemLongClick(position: Int) {
                                val note: Note = mNotes[position]
                                showUpdateDeleteDialog(note.id!!, note.title!!, note.note!!)
                            }
                        })
                binding.activityMainRv.layoutManager = LinearLayoutManager(baseContext)
                binding.activityMainRv.adapter = adapter
                binding.activityMainRv.setHasFixedSize(true)

            }

            override fun onCancelled(error: DatabaseError) {
                val toast = Toast.makeText(
                        applicationContext,
                        "Cancelled", Toast.LENGTH_SHORT
                )
                toast.setGravity(Gravity.CENTER, 0, 300)
                toast.show()
            }
        })
    }

    override fun customAddDialogClick(title: String, note: String) {
        saveIntoFirebase(title, note)
    }

    override fun customUpdateDialogClick(id: String, title: String, note: String) {
        val afterUpdate = Note(id, title, note, getCurrentDate())
        mRef.child(id).setValue(afterUpdate)

    }

    override fun customDeleteDialogClick(id: String) {
        mRef.child(id).removeValue()
    }

    private fun showAddDialog() {
        customAddDialogFragment = CustomAddDialogFragment.newInstance()
        customAddDialogFragment.show(supportFragmentManager, null)
    }

    private fun showUpdateDeleteDialog(id: String, title: String, note: String) {
        customUpdateDeleteDialogFragment = CustomUpdateDeleteDialogFragment.newInstance(id, title, note)
        customUpdateDeleteDialogFragment.show(supportFragmentManager, null)
    }

    private fun saveIntoFirebase(title: String, note: String) {
        if (title.isNotEmpty() && note.isNotEmpty()) {
            val id = mRef.push().key

            val myNote = Note(id!!, title, note, getCurrentDate())
            mRef.child(id).setValue(myNote)
        } else {
            val toast = Toast.makeText(
                    applicationContext,
                    "Empty data", Toast.LENGTH_SHORT
            )
            toast.setGravity(Gravity.CENTER, 0, 300)
            toast.show()

        }
    }

    private fun getCurrentDate(): String {
        val calender = Calendar.getInstance()
        val mdFormat = SimpleDateFormat("EEEE hh:mm a")
        return mdFormat.format(calender.time)
    }


}