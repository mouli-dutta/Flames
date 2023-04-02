package com.flames

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var frontAnimatorSet: AnimatorSet
    private lateinit var backAnimatorSet: AnimatorSet

    private lateinit var frontLayout: CardView
    private lateinit var backLayout: CardView

    private lateinit var name1Input: TextInputLayout
    private lateinit var name2Input: TextInputLayout

    private lateinit var textResultOnBackCard: TextView
    private lateinit var imageResultOnBackCard: ImageView

    private var isFront = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initIds()
        loadAnimations()
        changeCameraDistance()

        val rulesBtn: ImageButton = findViewById(R.id.rules_btn)
        rulesBtn.setOnClickListener { showRules() }

        val calcBtn: MaterialButton = findViewById(R.id.calculate)
        calcBtn.setOnClickListener { calculateBtnOnClick() }

        val tryAgainBtn: MaterialButton = findViewById(R.id.try_again)
        tryAgainBtn.setOnClickListener { tryAgainBtnOnClick() }

    }

    private fun initIds() {
        frontLayout = findViewById(R.id.front_layout)
        backLayout = findViewById(R.id.back_layout)

        name1Input = findViewById(R.id.name1)
        name2Input = findViewById(R.id.name2)

        textResultOnBackCard = findViewById(R.id.result)
        imageResultOnBackCard = findViewById(R.id.result_image)
    }

    private fun loadAnimations() {
        frontAnimatorSet = AnimatorInflater.loadAnimator(applicationContext, R.animator.flip_in) as AnimatorSet
        backAnimatorSet = AnimatorInflater.loadAnimator(applicationContext, R.animator.flip_out) as AnimatorSet
    }

    private fun changeCameraDistance() {
        val scale = resources.displayMetrics.density
        val cameraDist = 8000 * scale
        frontLayout.cameraDistance = cameraDist
        backLayout.cameraDistance = cameraDist
    }

    private fun showRules() {
        val titleView = TextView(this)
        titleView.setText(R.string.app_name)
        titleView.setPadding(20, 30, 20, 30)
        titleView.textSize = 20F
        titleView.setTextColor(ResourcesCompat.getColor(resources, R.color.alert_dialog_title, null))

        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
        builder.setCustomTitle(titleView)
            .setMessage(R.string.rules)
            .setPositiveButton("OK") { dialog: DialogInterface, _: Int -> dialog.dismiss() }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawableResource(R.color.white)
        dialog.show()
    }

    private fun calculateBtnOnClick() {
        // extract user input from input fields
        val name1Str = getStringFromField(name1Input)
        val name2Str = getStringFromField(name2Input)

        // set error when input field is empty
        // initialize
        name1Input.isErrorEnabled = false
        name2Input.isErrorEnabled = false

        // check
        when {
            (name1Str == null) || name1Str.isEmpty() -> {
                name1Input.isErrorEnabled = true
                name1Input.error = "Please enter your name!"
            }
            (name2Str == null) || name2Str.isEmpty() -> {
                name2Input.isErrorEnabled = true
                name2Input.error = "Please enter your partner's name!"
            }
            // both inputs are present
            else -> {
                try {
                    // calculate Flames result and display
                    val status = Flames(name1Str, name2Str).calculateRelationShip()
                    textResultOnBackCard.text = status.getMsg()
                    imageResultOnBackCard.setImageResource(status.getImageID())
                    imageResultOnBackCard.scaleType = ImageView.ScaleType.FIT_CENTER

                    // flip views
                    isFront = when {
                        isFront -> {
                            flipLayouts(backLayout, frontLayout)
                            false
                        }
                        else -> {
                            flipLayouts(frontLayout, backLayout)
                            true
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "Error occurred", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun tryAgainBtnOnClick() {
        // clear input fields
        name1Input.editText?.text = null
        name2Input.editText?.text = null

        name1Input.editText?.clearFocus()
        name2Input.editText?.clearFocus()

        // flip views
        isFront = when {
            !isFront -> {
                flipLayouts(frontLayout, backLayout)
                true
            }
            else -> {
                flipLayouts(backLayout, frontLayout)
                false
            }
        }
    }

    private fun flipLayouts(visibleView: View, invisibleView: View) {
        try {
            frontAnimatorSet.setTarget(visibleView)
            backAnimatorSet.setTarget(invisibleView)
            frontAnimatorSet.start()
            backAnimatorSet.start()

        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Error occurred while flipping view", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getStringFromField(layout: TextInputLayout): String? {
        val editText = layout.editText
        return editText?.text?.toString()?.trim()?.lowercase()
    }

}