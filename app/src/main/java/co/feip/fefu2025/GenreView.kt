package co.feip.fefu2025

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView

class AnimeGenreView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val genreNameTextView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.genre_item, this, true)
        genreNameTextView = findViewById(R.id.genre_name)

        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.GenreView)
        val backgroundColor = typedArray.getColor(R.styleable.GenreView_backgroundColor, Color.BLACK)
        val genreName = typedArray.getString(R.styleable.GenreView_genreName)
            ?: context.getString(R.string.default_genre_name)

        background = context.getDrawable(R.drawable.genre_background)
        setBackColor(backgroundColor)
        setGenreName(genreName)

        typedArray.recycle()
    }

    fun setGenreName(name: String) {
        genreNameTextView.text = name
    }

    fun setBackColor(color: Int) {
        (background as? GradientDrawable)?.setColor(color)
    }
}