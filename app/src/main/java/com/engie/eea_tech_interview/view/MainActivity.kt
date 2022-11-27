package com.engie.eea_tech_interview.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.engie.eea_tech_interview.utils.NavJsonConvertor
import com.engie.eea_tech_interview.view.ui.*
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {

                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    NavHost(
                        navController,
                        startDestination = Screen.HomeScreen.route,
                    ) {
                        composable(Screen.HomeScreen.route) {
                            HomeScreen(navController = navController)
                        }

                        composable(Screen.DetailScreen.deepLink) {
                            val movie = it.arguments?.getString("movie")
                                ?.let { it1 -> NavJsonConvertor.movieFromJson(it1) }
                            movie?.let { it1 -> DetailScreen(navController = navController, it1) }
                        }

                    }
                }
            }
        }

    }
}