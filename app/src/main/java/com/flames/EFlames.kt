package com.flames

enum class EFlames (private val ID: Int, private val msg:String) {
    FRIENDSHIP(R.drawable.friendship, "The friendship between you two is unbreakable. 😎"),
    LOVE(R.drawable.love, "You two are madly in Love with each other. 😍"),
    AFFECTION(R.drawable.affection, "You are very affectionate to each other. 🤗"),
    MARRIAGE(R.drawable.marriage, "Congratulations, on your marriage! 💑"),
    ENEMY(R.drawable.enemy, "Yikes! You guys are sworn enemies. 🤬"),
    SIBLINGS(R.drawable.siblings, "Awww... You two make the sweetest siblings. ☺"),
    ERROR (R.drawable.enemy, "Oops.. That shouldn't happen.");

    fun getImageID() : Int = ID
    fun getMsg() : String = msg;
}