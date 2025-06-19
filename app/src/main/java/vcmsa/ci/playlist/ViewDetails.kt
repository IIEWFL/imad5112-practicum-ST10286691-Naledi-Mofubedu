package vcmsa.ci.playlist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class ViewDetails : AppCompatActivity() {

    private lateinit var song: Array<String>
    private lateinit var artist: Array<String>
    private lateinit var comment: Array<String>
    private lateinit var rate: Array<Int>
    lateinit var tvDisplay: TextView
    lateinit var detailBtn: Button
    lateinit var averageBtn: Button
    lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //tvDisplay   = findViewById(R.id.tvDisplay)
        //detailBtn   = findViewById(R.id.detailBtn)
       // averageBtn  = findViewById(R.id.averageBtn)
        //backBtn     = findViewById(R.id.backBtn)

        // Retrieve the lists passed via Intent
        val song: Array<String> = intent.getStringArrayListExtra("song Title")?.toTypedArray() ?: arrayOf()
        //val artist: Array<String> = intent.getStringArrayListExtra("Artist")?.toTypedArray() ?: arrayOf()
        //val comment: Array<String> = intent.getStringArrayListExtra("comments")?.toTypedArray() ?: arrayOf()
        val rate: Array<Int> = intent.getIntegerArrayListExtra("rate")?.toArray(arrayOfNulls<Int>(0)) ?: arrayOf()


        if (song.isEmpty()) {
            Toast.makeText(this, "No song data received 😟", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        displayPlaylist()

        averageBtn.setOnClickListener {
            if (rate.isEmpty()) {
                Snackbar.make(tvDisplay, "No ratings available to average!", Snackbar.LENGTH_SHORT).show()
            } else {
                val avg = rate.average()
                val avgRounded = String.format("%.2f", avg)
                Snackbar.make(tvDisplay, "Average rating: $avgRounded", Snackbar.LENGTH_LONG).show()
            }
        }

        backBtn.setOnClickListener { finish() }

        detailBtn.setOnClickListener {
            // Optionally, add extra behavior here—e.g. scroll to top or show a specific song's info
            Snackbar.make(tvDisplay, "Detail feature coming soon!", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun displayPlaylist() {
        val builder = StringBuilder()
        for (i in song.indices) {
            builder.append("${i + 1}. ${song[i]}\n")
            builder.append("By ${artist.getOrNull(i) ?: "Unknown"}\n")
            builder.append("Rating: ${rate.getOrNull(i) ?: "N/A"}\n")
            builder.append("Comment: ${comment.getOrNull(i) ?: ""}\n\n")
        }
        tvDisplay.text = builder.toString()
    }
}