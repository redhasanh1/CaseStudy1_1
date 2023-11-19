package com.example.casestudy1_1

import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorLogic {

    var memory: Double = 0.0

    fun evaluateExpression(expression: String): String {
        return try {
            val result = ExpressionBuilder(expression).build().evaluate()
            result.toString()
        } catch (e: Exception) {
            "Error"
        }
    }

    fun calculatePercentage(value: String): String {
        return (value.toDouble() / 100).toString()
    }

    fun memoryClear() {
        memory = 0.0
    }

    fun memoryRecall(): String {
        return memory.toString()
    }

    fun memoryAdd(value: String) {
        memory += value.toDouble()
    }

    fun memorySubtract(value: String) {
        memory -= value.toDouble()
    }
}
