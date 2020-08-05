package com.github.playground4devs.kmpmovies.preview

import android.annotation.SuppressLint
import com.github.playground4devs.movies.Image
import com.github.playground4devs.movies.Movie


@SuppressLint("NewApi")
fun toConstruct(obj: Any?): String = when (obj) {
    null -> "null"
    is String -> "\"${obj}\""
    is Int -> obj.toString()
    is List<*> -> obj.joinToString(", \n", "listOf(\n", "\n)") { toConstruct(it) }
    is Movie -> """
            Movie(
                title = "${obj.title}",
                plot =  "${obj.plot}",
                image = ${toConstruct(obj.image)},
                rating = ${obj.rating ?: "null"},
                genres =  ${toConstruct(obj.genres)}
            )
        """.trimIndent()
    is Image -> """
            Image(
                url =  "${obj.url}",
                width =  ${obj.width},
                height =  ${obj.height},
                caption =  "${obj.caption}"
            )
        """.trimIndent()
    else -> error("Unknown $")
}