package com.example.testtask

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {

    private val root: TableLayout by lazy { findViewById(R.id.root) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initEditListeners()
    }

    private fun initEditListeners() {
        root.getChildren<TableRow>()
            .forEach { currentRow ->
                currentRow.getChildren<EditText>()
                    .forEach { edit ->
                        edit.addTextChangedListener {
                            root.getChildren<TableRow>()
                                .forEach { row ->
                                    val score = row.getScore()
                                    val place = root.getPlace(row)
                                    row.showNumberByTag(score, "score")
                                    row.showNumberByTag(place, "place")
                                }
                        }
                    }
            }
    }

    private fun ViewGroup.getPlace(row: TableRow): Int {
        return getChildren<TableRow>()
            .filter { it.tag != "skip" }
            .sortedByDescending { it.getScore() }
            .mapIndexed { index, it ->
                Pair(index + 1, it)
            }
            .firstOrNull { it.second == row }?.first ?: 0
    }

    private fun ViewGroup.getScore(): Int {
        return getChildren<EditText>()
            .sumOf {
                it.text.toString().toIntOrNull() ?: 0
            }
    }

    private fun ViewGroup.showNumberByTag(num: Int, tag: String) {
        getTextViewByTag(tag)?.text = num.toString()
    }

    private fun ViewGroup.getTextViewByTag(tag: String): TextView? {
        return getChildren<TextView>().firstOrNull { it.tag == tag }
    }

    private inline fun <reified T : View> ViewGroup.getChildren(): List<T> {
        return children
            .filter { it is T }
            .map { it as T }
            .toList()
    }
}