package com.engie.eea_tech_interview.view.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.engie.eea_tech_interview.model.Movie
import com.engie.eea_tech_interview.utils.ImageURLBuilder
import com.engie.eea_tech_interview.utils.NavJsonConvertor
import com.engie.eea_tech_interview.view.Screen
import java.util.*


@OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun MovieCard(
    navController: NavController,
    movie: Movie,
) {

    val visible by remember { mutableStateOf(true) }

    Box(modifier = Modifier.padding(horizontal = 10.dp)) {

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(
                initialAlpha = 0.4f
            ),
            exit = fadeOut(
                animationSpec = tween(durationMillis = 250)
            )
        ) {
            Card(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp),
                backgroundColor = MaterialTheme.colors.background,
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    navController.navigate(
                        Screen.DetailScreen.deepLink.replace(
                            "{movie}",
                            NavJsonConvertor.movieToJson(movie)
                        )
                    )
                }
            ) {

                Column(
                    modifier = Modifier
                        .width(306.dp)
                        .background(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colors.background
                        )
                        .padding(10.dp)
                ) {

                    GlideImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        model = movie.posterPath?.let { ImageURLBuilder.buildURL(it) },
                        contentDescription = "Image poster",
                        contentScale = ContentScale.Fit
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )

                    movie.title?.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    }?.let {
                        Text(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Normal,
                            maxLines = 1,
                            color = Color.Magenta,
                            text = it
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )

                    movie.overview?.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    }?.let {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                            fontStyle = FontStyle.Normal,
                            text = it
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                    )

                    movie.releaseDate?.let {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                            fontStyle = FontStyle.Normal,
                            text = "Release date $it"
                        )
                    }
                }
            }
        }
    }
}
