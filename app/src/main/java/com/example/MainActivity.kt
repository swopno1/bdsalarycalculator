package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ui.SalaryCalculatorScreen
import com.example.ui.SalaryViewModel
import com.example.ui.theme.BDSalaryCalculatorTheme

class MainActivity : ComponentActivity() {

    private val viewModel: SalaryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BDSalaryCalculatorTheme {
                SalaryCalculatorScreen(viewModel = viewModel)
            }
        }
    }
}
