package com.example.artwork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.artwork.data.ArtworkRepository
import com.example.artwork.model.Art
import com.example.artwork.ui.theme.ArtworkTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Rowby Villanueva
//Android Project 1
//Artwork app
//03/24/25


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            ArtworkTheme {
                NavHost(
                    navController = navController, startDestination = Screen.Home.route + "/{id}"
                ){
                    composable(
                        route = Screen.Home.route + "/{id}",
                        arguments = listOf(navArgument("id"){
                            type = NavType.IntType
                            defaultValue = 0
                        })
                    ){
                        HomePage(navController)
                    }


                    composable(
                        route = Screen.Artist.route + "/{id}",
                        arguments = listOf(navArgument("id"){
                            type = NavType.IntType

                        })
                    ){
                        ArtistPage(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun HomePage(navController: NavHostController) {
    var current by remember {
        mutableIntStateOf(
            navController.currentBackStackEntry?.arguments?.getInt("id") ?: 0
        )
    }
    val art = ArtworkRepository.artworks[current]
    Box(     //Box for the layout
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),  //make a page scrollable
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ArtWall(
                navController = navController,
                art = art,
                current = current,
                modifier = Modifier.padding(16.dp)
            )


            ArtDescriptor(
                art = art,
                modifier = Modifier.padding(16.dp) // Add spacing around descriptor
            )
        }


        ArtNavigation(
            current = current,
            move = { index ->
                current = if (index !in 0 until ArtworkRepository.artworks.size) 0 else index
            },
            modifier = Modifier
                .align(Alignment.BottomCenter) // Align the button to bottom center
                .padding(16.dp)
        )
    }
}



@Composable
fun ArtWall(navController: NavHostController, art: Art, current: Int, modifier: Modifier = Modifier){
Column (
    modifier = Modifier
        .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
){
    Image(
        painter = painterResource(art.artworkImageId),
        contentDescription = stringResource(art.titleId),
        modifier = Modifier
            .size(300.dp)
            .clickable{ //makes the image clickable
                navController.navigate(Screen.Artist.route + "/$current")
        })
    

    }
}

@Composable
fun ArtDescriptor(art: Art, modifier: Modifier = Modifier) {
Column(
    horizontalAlignment = Alignment.CenterHorizontally
) {

    Text(text = stringResource(art.artistId),
        fontWeight = FontWeight.Bold)
    Row (
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
    Text(text = stringResource(art.titleId))
    Text(text = stringResource(art.yearId))
            }
        }
    }



@Composable
fun ArtNavigation(current: Int, modifier: Modifier = Modifier, move: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { move(current - 1) },
                enabled = current > 0
            ) {
                Text(text = "Back")
            }
            Button(
                onClick = { move(current + 1) },
                enabled = current < ArtworkRepository.artworks.size - 1
            ) {
                Text(text = "Next")
            }
        }
    }
}


@Composable
fun ArtistPage(navController: NavHostController) {
    val id = navController.currentBackStackEntry?.arguments?.getInt("id") ?: 0
    val art = ArtworkRepository.artworks[id]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp, bottom = 25.dp)
        ) {
            Image(
                painter = painterResource(art.artistImageId),
                contentDescription = stringResource(art.artistId),
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(
                    text = stringResource(art.artistId),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = stringResource(art.artistInfoId),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }

        Text(
            text = stringResource(art.artistBioId),
            modifier = Modifier
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = { navController.navigate(Screen.Home.route + "/$id") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Back")
        }
    }
}



@Composable
fun ArtistProfile(art: Art){

        Text(text = stringResource(art.artistId))
}

@Composable
fun ArtistBio(bioId: Int){
    Text(text = stringResource(id = bioId))
}


@Composable
fun ArtistBackNavigation(navController: NavHostController, id: Int){

    Button(
        onClick = {
        navController.navigate(Screen.Home.route + "/$id")
        }
    ){
        Text(text = "Back")
    }
}
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ArtworkTheme {
        HomePage(rememberNavController())
    }
}