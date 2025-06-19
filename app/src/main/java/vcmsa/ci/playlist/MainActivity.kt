package vcmsa.ci.playlist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    lateinit var songTitleEt: EditText
    lateinit var artistEt: EditText
    lateinit var commentEt: EditText
    lateinit var rateEt: EditText
    lateinit var addBtn: Button
    lateinit var nextBtn: Button
    lateinit var closeBtn: Button

    private val songTitle = mutableListOf<String>()
    private val artist = mutableListOf<String>()
    private val comment = mutableListOf<String>()
    private val rate = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        songTitleEt = findViewById(R.id.songTitleEt)
        artistEt = findViewById(R.id.artistEt)
        commentEt = findViewById(R.id.commentEt)
        rateEt = findViewById(R.id.rateEt)
        addBtn = findViewById(R.id.addBtn)
        nextBtn = findViewById(R.id.nextBtn)
        closeBtn = findViewById(R.id.closeBtn)

        addBtn.setOnClickListener {
            if (songTitle.size >= 4) {
                Snackbar.make(it, "You must have 4 songs", Snackbar.LENGTH_SHORT).show()
            } else {
                addPlaylistDialog()
            }
        }
        nextBtn.setOnClickListener {
            // add an intent
            if (songTitle.isNotEmpty()) {
                val intent = Intent(this, ViewDetails::class.java)
                intent.putStringArrayListExtra("song Title", ArrayList(songTitle))
                intent.putStringArrayListExtra("Artist", ArrayList(artist))
                intent.putStringArrayListExtra("comments", ArrayList(comment))
                intent.putIntegerArrayListExtra(
                    "rate",
                    ArrayList(rate)
                ) // if 'rate' is actually Ints, change this line
                startActivity(intent)
            } else {
                Snackbar.make(nextBtn, "please answer all the questions", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        closeBtn.setOnClickListener {
            //close the application
            finishAffinity()

        }

    }
    fun addPlaylistDialog() {
        val builder = AlertDialog.Builder (this)
        builder.setTitle("Add New Song")

        val view = layoutInflater.inflate(R.layout.addsong, null)
        val Song = view.findViewById<EditText>(R.id.songTitleEt)
        val Artist = view.findViewById<EditText>(R.id.artistEt)
        val Rate = view.findViewById<EditText>(R.id.rateEt)
        val Comment = view.findViewById<EditText>(R.id.commentEt)

        builder.setView(view)

        builder.setPositiveButton("Add") { dialog, _ ->
            var valid = true
            val title = Song.text.toString().trim().also {
                if (it.isEmpty()) { Song.error = "Required"; valid = false }
            }
            val artistName = Artist.text.toString().trim().also {
                if (it.isEmpty()) { Artist.error = "Required"; valid = false }
            }
            val rateSong = Rate.text.toString().trim()
            val rateInt = rateSong.toIntOrNull().also {
                if (rateSong.isEmpty()) { Rate.error = "Required"; valid = false }
                else if (it == null || it !in 1..10) {
                    Rate.error = "1–10 only"; valid = false
                }
            }
            val comment = Comment.text.toString().trim()
            if (!valid) return@setPositiveButton

            // Save to lists
            songTitle.add("New Song")
            artist.add(artistName)
            rate.add(rateInt!!)
            this.comment.add(comment)
            Snackbar.make(findViewById(android.R.id.content),
                "\"$title\" by $artistName added!", Snackbar.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel"){ dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

}
