package com.engie.eea_tech_interview.view.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.engie.eea_tech_interview.utils.Resource
import com.engie.eea_tech_interview.view.ui.composables.MovieCard
import com.engie.eea_tech_interview.viewmodel.AppViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: AppViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {

    val state = viewModel.movies.collectAsState(initial = Resource.loading())
    val movies = state.value.data ?: emptyList()


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(elevation = 3.dp, contentPadding = PaddingValues(5.dp)) {
                Text(text = "Movie App")
            }
        }
    ) { it ->
        when(state.value.status){
            Resource.Status.SUCCESS -> {
                LazyColumn(
                    contentPadding = PaddingValues(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                    modifier = Modifier
                        .padding(it)
                        .fillMaxWidth()
                ) {

                    itemsIndexed(movies) { _, movie ->
                        MovieCard(
                            navController = navController,
                            movie = movie
                        )
                    }
                }
            }
            Resource.Status.LOADING -> {
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize() ){
                    CircularProgressIndicator()
                }
            }
            else -> {

               LaunchedEffect(scaffoldState){
                   state.value.message?.let { it1 ->
                       scaffoldState.snackbarHostState.showSnackbar(
                           message = it1,
                           actionLabel = "",
                           duration = SnackbarDuration.Long
                       )
                   }
               }

            }
        }

    }
}