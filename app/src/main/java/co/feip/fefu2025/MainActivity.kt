package co.feip.fefu2025

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        val button = findViewById<Button>(R.id.add_button)
        val myLayout = findViewById<FlexBoxLayout>(R.id.flexbox_layout)

        button.setOnClickListener {
            myLayout.addView(
                AnimeGenreView(this).apply {
                    val (name, color) = Genres.LIST.random()
                    setGenreName(name)
                    setBackColor(color)
                }
            )
        }
    }
}