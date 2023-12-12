package com.example.realcalculater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnInit()
        allClear()
        enter()
    }

    lateinit var btn : Button
    var numbersFirst : Double? = null
    var numbersSecond : Double? = null
    var operations : String? = null
    var dotFlag = false
    var operationsFlag = false
    var operationFlagOnce = false
    var operationStop = false
    var enterFlag = false
    var enterNo : Int? = null

    private fun allClear() {
        findViewById<Button>(R.id.allClearBtn).setOnClickListener {
            initAll()
        }
    }

    private fun initAll() {
        setContentView(R.layout.activity_main)
        btnInit()
        allClear()
        enter()
        numbersFirst = null
        numbersSecond = null
        operations = null
        dotFlag = false
        operationsFlag = false
        operationFlagOnce = false
        var operationStop = false
        enterFlag = false
        if(enterNo != null) {
            var calculateText = findViewById<TextView>(R.id.calculateText)
            var operateText = findViewById<TextView>(R.id.operateText)
            calculateText.text = enterNo.toString()
            operateText.text = enterNo.toString()
            enterNo = null
        }
    }

    private fun enter() {
        var calculateText = findViewById<TextView>(R.id.calculateText)
        var operateText = findViewById<TextView>(R.id.operateText)
        findViewById<Button>(R.id.enterBtn).setOnClickListener {
            if(!operationFlagOnce && !calculateText.text.isBlank()) {
                if(operations == null) {
                    numbersFirst = calculateText.text.toString().toDouble()
                    numbersFirst = numbersFirst!!*2
                    operateText.append("*2")
                    numberToInt(numbersFirst!!)
                } else {
                    operation()
                    operations = null
                    numberToInt(numbersFirst!!)
                }
                enterFlag = true
            }
        }
    }

    private fun numberToInt(numbersFirst : Double) {
        var calculateText = findViewById<TextView>(R.id.calculateText)
        if(numbersFirst!!%1.0 == 0.0) {
            calculateText.text = numbersFirst.toString().split(".")[0]
        } else {
            calculateText.text = numbersFirst.toString()
        }
    }

    private fun operation() {
        var calculateText = findViewById<TextView>(R.id.calculateText)
        numbersSecond = calculateText.text.toString().toDouble()
        when(operations) {
            "+" -> {
                numbersFirst = numbersFirst!! + numbersSecond!!
                numberToInt(numbersFirst!!)
            }

            "-" -> {
                numbersFirst = numbersFirst!! - numbersSecond!!
                numberToInt(numbersFirst!!)
            }

            "*" -> {
                numbersFirst = numbersFirst!! * numbersSecond!!
                numberToInt(numbersFirst!!)
            }

            "/" -> {
                numbersFirst = numbersFirst!! / numbersSecond!!
                numberToInt(numbersFirst!!)
            }
        }
    }

    private fun btnInit() {

        var nameArray = arrayOf("1", "2", "3", "4"
            , "5", "6", "7", "8", "9", "0"
            , "Dot", "Plus", "Minus", "Multi", "Div")
        var calculateText = findViewById<TextView>(R.id.calculateText)
        var operateText = findViewById<TextView>(R.id.operateText)

        for (i in nameArray) {
            btn = findViewById<Button>(resources.getIdentifier("btn$i", "id", "com.example.realcalculater"))
            btn.setOnClickListener {
                if(i.equals("Plus") || i.equals("Minus") || i.equals("Multi") || i.equals("Div")) {
                    if(!operationStop) {
                        if(enterFlag) {
                            enterFlag = false
                        }
                        if(operationsFlag == false) {
                            numbersFirst = calculateText.text.toString().toDouble()
                            calculateText.text = null
                            operationsFlag = true
                            dotFlag = false
                        } else {
                            operation()
                        }
                        when(i) {
                            "Plus" -> {
                                operations = "+"
                                operateText.append("+")
                            }
                            "Minus" -> {
                                operations = "-"
                                operateText.append("-")
                            }
                            "Multi" -> {
                                operations = "*"
                                operateText.append("*")
                            }
                            "Div" -> {
                                operations = "/"
                                operateText.append("/")
                            }
                        }
                        operationFlagOnce = true
                        operationStop = true
                    }
                } else if (i.equals("Dot")){
                    if(!enterFlag && !dotFlag && !calculateText.text.isBlank()) {
                        calculateText.append(".")
                        operateText.append(".")
                        dotFlag = true
                    }
                } else {
                    if(enterFlag) {
                        enterNo = i.toInt()
                        initAll()
                    }
                    if(operationFlagOnce) {
                        calculateText.text = null
                        calculateText.append(i)
                        operateText.append(i)
                        operationFlagOnce = false
                    } else {
                        calculateText.append(i)
                        operateText.append(i)
                    }
                    if(operationStop){
                        operationStop = false
                    }
                }
            }
        }
    }
}