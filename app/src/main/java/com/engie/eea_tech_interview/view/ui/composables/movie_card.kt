package com.engie.eea_tech_interview.view.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.engie.eea_tech_interview.model.Movie
import com.engie.eea_tech_interview.utils.NavJsonConvertor
import com.engie.eea_tech_interview.view.Screen
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieCard(
    navController: NavController,
    movie: Movie,
) {


    Box(modifier = Modifier.padding(horizontal = 10.dp)) {

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
