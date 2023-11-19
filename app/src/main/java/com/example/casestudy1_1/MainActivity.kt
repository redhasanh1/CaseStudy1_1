package com.example.casestudy1_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private lateinit var inputTextView: TextView
    private var lastNumeric: Boolean = false
    private var stateError: Boolean = false
    private var lastDot: Boolean = false
    private var memory: Double = 0.0 // Memory variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.tvResult)
        inputTextView = findViewById(R.id.input)

        setNumericOnClickListener()
        setOperatorOnClickListener()
        setSpecialButtonsOnClickListener()
        setMemoryButtonsOnClickListener()
    }

    private fun setNumericOnClickListener() {
        val numericButtons = arrayOf(R.id.button12, R.id.button9, R.id.button8, R.id.button6, R.id.button11, R.id.button10, R.id.button3, R.id.button5, R.id.button16)
        numericButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { v ->
                onNumericButtonClick(v as Button)
            }
        }
    }

    private fun setOperatorOnClickListener() {
        val operatorButtons = arrayOf(R.id.buttonMC10, R.id.buttonMC9, R.id.buttonMC8, R.id.buttonMC11, R.id.button17) // Include the exponent button ID
        operatorButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { v ->
                onOperatorButtonClick(v as Button)
            }
        }
    }

    private fun setSpecialButtonsOnClickListener() {
        findViewById<Button>(R.id.buttonMC7).setOnClickListener { onEqualButtonClick() }
        findViewById<Button>(R.id.button13).setOnClickListener { onDecimalPointButtonClick() }
        findViewById<Button>(R.id.buttonClear).setOnClickListener { onClearButtonClick() }
        findViewById<Button>(R.id.button15).setOnClickListener { onPercentButtonClick() }
    }

    private fun setMemoryButtonsOnClickListener() {
        findViewById<Button>(R.id.buttonMC2).setOnClickListener { onMCButtonClick() }
        findViewById<Button>(R.id.buttonMC).setOnClickListener { onMRButtonClick() }
        findViewById<Button>(R.id.buttonMC3).setOnClickListener { onMPlusButtonClick() }
        findViewById<Button>(R.id.buttonMC6).setOnClickListener { onMMinusButtonClick() }
    }

    private fun onNumericButtonClick(button: Button) {
        if (stateError) {
            inputTextView.text = button.text
            stateError = false
        } else {
            inputTextView.append(button.text)
        }
        lastNumeric = true
    }

    private fun onOperatorButtonClick(button: Button) {
        if (lastNumeric && !stateError) {
            inputTextView.append(when (button.id) {
                R.id.buttonMC10 -> "+"
                R.id.buttonMC9 -> "-"
                R.id.buttonMC8 -> "*"
                R.id.buttonMC11 -> "/"
                R.id.button17 -> "^" // Handle exponent
                else -> ""
            })
            lastNumeric = false
            lastDot = false
        }
    }

    private fun onEqualButtonClick() {
        if (lastNumeric && !stateError) {
            val text = inputTextView.text.toString()
            val expression = ExpressionBuilder(text).build()
            try {
                val result = expression.evaluate()
                resultTextView.text = result.toString()
                lastDot = result.toString().contains(".")
            } catch (ex: Exception) {
                resultTextView.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }

    private fun onDecimalPointButtonClick() {
        if (lastNumeric && !stateError && !lastDot) {
            inputTextView.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    private fun onClearButtonClick() {
        this.inputTextView.text = ""
        this.resultTextView.text = ""
        lastNumeric = false
        stateError = false
        lastDot = false
    }

    private fun onPercentButtonClick() {
        if (lastNumeric && !stateError) {
            val value = inputTextView.text.toString().toDouble() / 100
            inputTextView.text = value.toString()
            lastNumeric = true
        }
    }

    private fun onMCButtonClick() {
        memory = 0.0
    }

    private fun onMRButtonClick() {
        inputTextView.text = memory.toString()
        lastNumeric = true
    }

    private fun onMPlusButtonClick() {
        val currentNumber = inputTextView.text.toString()
        if (currentNumber.isNotEmpty() && lastNumeric) {
            memory += currentNumber.toDouble()
        }
    }

    private fun onMMinusButtonClick() {
        val currentNumber = inputTextView.text.toString()
        if (currentNumber.isNotEmpty() && lastNumeric) {
            memory -= currentNumber.toDouble()
        }
    }
}
