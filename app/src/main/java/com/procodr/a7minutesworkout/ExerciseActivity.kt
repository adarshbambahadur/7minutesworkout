package com.procodr.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.procodr.a7minutesworkout.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity() {
    private var binding: ActivityExerciseBinding? = null


    private var exerciseList: ArrayList<Exercise> = ArrayList()
    private var timer: CountDownTimer? = null
    private var timerProgress: Long = 0

    private var progressIdx = 0

    class Exercise(var name: String, var duration: Long, var afterPrompt: String) {
        var maxProgress = duration / 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        setExercisesList()
        setupExerciseView()
    }

    private fun setupExerciseView(progressIdx: Int = 0) {
        if (timer != null) {
            timer?.cancel()
            timerProgress = 0
        }

        setProgressBar(progressIdx)
    }

    private fun setProgressBar(progressIdx: Int) {
        binding?.progressBar?.max = exerciseList[progressIdx].maxProgress.toInt()
        binding?.tvTitle?.text = exerciseList[progressIdx].name
        binding?.progressBar?.progress = exerciseList[progressIdx].maxProgress.toInt()

        timer = object:CountDownTimer(exerciseList[progressIdx].duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerProgress++

                binding?.tvTimer?.text = (exerciseList[progressIdx].maxProgress - timerProgress).toString()
                binding?.progressBar?.progress = (exerciseList[progressIdx].maxProgress - timerProgress).toInt()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, exerciseList[progressIdx].afterPrompt, Toast.LENGTH_SHORT).show()
                var idx = progressIdx
                setupExerciseView(++idx)
            }
        }.start()
    }

    private fun setExercisesList() {
        exerciseList.add(Exercise("Get Ready In", 20000, "Start Round 1"))
        exerciseList.add(Exercise("Work For Another", 50000, "Take a breath"))
        exerciseList.add(Exercise("Get Ready In", 20000, "Start Round 2"))
        exerciseList.add(Exercise("Work For Another", 50000, "Take a breath"))
        exerciseList.add(Exercise("Get Ready In", 20000, "Start Round 3"))
        exerciseList.add(Exercise("Work For Another", 50000, "Take a breath"))
        exerciseList.add(Exercise("Get Ready In", 20000, "Start Round 4"))
        exerciseList.add(Exercise("Work Another", 50000, "Take a breath"))
        exerciseList.add(Exercise("Get Ready In", 20000, "Start Round 5"))
        exerciseList.add(Exercise("Work Another", 50000, "Take a breath"))
        exerciseList.add(Exercise("Get Ready In", 20000, "Start Round 6"))
        exerciseList.add(Exercise("Workout 6", 50000, "Workout finished"))
    }

    override fun onDestroy() {
        super.onDestroy()

        if (timer != null) {
            timer?.cancel()
            timerProgress = 0
        }

        binding = null
    }
}