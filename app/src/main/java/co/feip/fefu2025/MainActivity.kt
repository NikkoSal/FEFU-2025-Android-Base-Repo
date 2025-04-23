package co.feip.fefu2025

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var counter = 0
    private lateinit var counterTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt("counter", 0)
        }

        counterTextView = findViewById(R.id.counterTextView)
        updateCounterText()

        counterTextView.setOnClickListener {
            counter++
            updateCounterText()
        }
    }

    private fun updateCounterText() {
        counterTextView.text = "MainActivity: $counter"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("counter", counter)
    }
}