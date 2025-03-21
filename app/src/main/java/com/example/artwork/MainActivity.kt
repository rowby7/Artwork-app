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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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
fun HomePage(navController: NavHostController){

    var current by remember {
        mutableIntStateOf(
            navController.currentBackStackEntry?.arguments?.getInt(
                "id"
            ) ?: 0
        )
    }
    val art = ArtworkRepository.artworks[current]
    Text(text = stringResource(art.artistBioId))
Column(modifier = Modifier
    .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
){
    ArtWall(navController, art, current)

    ArtDescriptor(art)
    ArtNavigation(current){
        current = if (it !in 0..<ArtworkRepository.artworks.size) 0 else it
        }

    }
}

@Composable
fun ArtWall(navController: NavHostController, art: Art, current: Int){
Column (
    modifier = Modifier
        .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
){
    Image(
        painter = painterResource(art.artworkImageId),
        contentDescription = stringResource(art.titleId),
        modifier = Modifier.clickable{
                navController.navigate(Screen.Artist.route + "/$current")
        })
    
    Text(text = stringResource(art.artistId))
    }
}

@Composable
fun ArtDescriptor(art: Art) {

}



@Composable
fun ArtNavigation(current: Int, move: (Int) -> Unit){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Button(
                onClick = {
                    move(current - 1)
                }
            ) {
                Text(text = "Back")
            }
            Button(
                onClick = {
                    move(current + 1)
                }
            ) {
                Text(text = "Go")
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
            .fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 100.dp, bottom = 25.dp)
        ) {
        // Artist Image
        Image(
            painter = painterResource(art.artistImageId),
            contentDescription = stringResource(art.artistId),
            modifier = Modifier
                .size(120.dp) // Set the size of the image
                .clip(CircleShape)
                .padding(bottom = 16.dp) // Add spacing below the image
        )
        Column() {
            //Artist Name
            Text(
                text = stringResource(art.artistId),
                modifier = Modifier.padding(bottom = 8.dp) // Space below the name
            )

            // Artist Info (Lifespan and Nationality)
            Text(
                text = stringResource(art.artistInfoId),
                modifier = Modifier.padding(bottom = 16.dp) // Space below the info
            )
        }
    }

        // Artist Bio (Single Paragraph)
        Text(
            text = stringResource(art.artistBioId),
            modifier = Modifier
                .weight(1f) // Push the Back button to the bottom by taking up remaining space
                .padding(bottom = 16.dp) // Space below the bio
        )

        // Back Button
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

    // TODO: Artist Page Section A

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