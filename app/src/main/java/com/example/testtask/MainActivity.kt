package com.example.testtask

import android.os.Bundle
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {

    private val rootLayout: TableLayout by lazy { findViewById(R.id.root) }
    private var tableRowList = listOf<TableRow>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRows()
        initEditListeners()
    }

    private fun initRows() {
        tableRowList = rootLayout.getChildren()
    }

    private fun initEditListeners() {
        tableRowList.forEach { row ->
            row.getChildren<EditText>()
                .forEach { edit ->
                    edit.addTextChangedListener {
                        calculateScore()
                    }
                }
        }
    }

    private fun calculateScore() {
        tableRowList.forEach { row ->
            var scoreValue = 0
            row.getChildren<EditText>()
                .forEach { edit ->
                    val editNumber = edit.text.toString().toIntOrNull() ?: 0
                    scoreValue += editNumber
                }
            val scoreView = row.getChildren<TextView>().firstOrNull { it.tag == "score" }
            scoreView?.text = scoreValue.toString()
        }
    }

    private fun calculateRowScore(row: TableRow) {

    }

    private fun calculatePlace() {

    }

    inline fun <reified T> ViewGroup.getChildren(): List<T> {
        return children
            .filter { it is T }
            .map { it as T }
            .toList()
    }
}