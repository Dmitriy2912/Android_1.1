package ru.netology.nmedia.repository

fun formatNumber(number: Int): String {
    return when {
        number < 0 -> "0"
        number < 1_000 -> number.toString()
        number < 10_000 -> {
            val hundreds = number / 100
            String.format("%.1f", hundreds / 10.0) + "K"
        }
        number < 1_000_000 -> "${number / 1_000}K"
        else -> {
            val millionsHundreds = number / 100_000
            String.format("%.1f", millionsHundreds / 10.0) + "M"
        }
    }
}