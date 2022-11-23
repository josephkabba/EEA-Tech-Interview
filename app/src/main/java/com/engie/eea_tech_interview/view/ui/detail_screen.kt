package com.engie.eea_tech_interview.view.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.engie.eea_tech_interview.model.Movie
import com.engie.eea_tech_interview.utils.Resource
import com.engie.eea_tech_interview.viewmodel.AppViewModel


@Composable
fun DetailScreen(
    navController: NavController,
    movie: Movie,
    viewModel: AppViewModel = hiltViewModel(),
) {

    val state = viewModel.genres.collectAsState(initial = Resource.loading())
    val genres = state.value.data ?: emptyList()

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                title = {
                    movie.title?.let { Text(text = it) }
                },

                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = ""
                        )
                    }

                }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 10.dp)
                .fillMaxSize()
        ) {

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth()
            ) {
                itemsIndexed(genres) { _, genre ->
                    if(movie.genreIds?.contains(genre.id) == true) {
                        Text(
                            modifier = Modifier.border(
                                2.dp,
                                color = Color.Magenta,
                                shape = RoundedCornerShape(10.dp)
                            ).padding(5.dp),
                            text = genre.name,
                            textAlign = TextAlign.Center,
                            color = Color.Magenta
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
            )

            Card(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp),
                backgroundColor = MaterialTheme.colors.background,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    movie.originalTitle?.let { it1 ->
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                            fontStyle = FontStyle.Normal,
                            text = it1
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        fontStyle = FontStyle.Normal,
                        text = "Release date ${movie.releaseDate}"
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        fontStyle = FontStyle.Normal,
                        text = "Rating ${movie.voteCount}"
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )

                    movie.overview?.let { it1 ->
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                            fontStyle = FontStyle.Normal,
                            text = it1
                        )
                    }


                }
            }



        }
    }
}