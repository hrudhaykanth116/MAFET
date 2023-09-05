package com.hrudhaykanth116.mafet.home

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(

) {

    val navController = rememberNavController()

    HomeScreenUI(
        navController = navController,
    )

}