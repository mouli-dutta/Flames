package com.flames

class Flames (private val name1 : String, private val name2 : String) {

    fun calculateRelationShip(): EFlames {
        val arr = charArrayOf('F', 'L', 'A', 'M', 'E', 'S')
        val l = getUniqueLetterCount() % arr.size
        return displayResult(arr[l])
    }

    private fun displayResult(letter: Char): EFlames {
        val tag: EFlames = when (letter) {
            'F' -> EFlames.FRIENDSHIP
            'L' -> EFlames.LOVE
            'A' -> EFlames.AFFECTION
            'M' -> EFlames.MARRIAGE
            'E' -> EFlames.ENEMY
            'S' -> EFlames.SIBLINGS
            else -> EFlames.ERROR
        }
        return tag
    }

    private fun getUniqueLetterCount(): Int {
        val uniqueInFirst = name1.replace("[$name2]".toRegex(), "")
        val uniqueInSecond = name2.replace("[$name1]".toRegex(), "")
        val totalChars = uniqueInFirst.length + uniqueInSecond.length
        return totalChars + 1
    }
}