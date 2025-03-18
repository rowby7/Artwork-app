package com.example.artwork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.artwork.data.ArtworkRepository
import com.example.artwork.ui.theme.ArtworkTheme

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

    ArtNavigation(current){
        current = if (it !in 0..<ArtworkRepository.artworks.size) 0 else it
    }
}

@Composable
fun ArtWall(){

}

@Composable
fun ArtDescriptor() {

}

@Composable
fun ArtNavigation(current: Int, move: (Int) -> Unit){

    Button(
        onClick = {
            move(current + 1)
        }
    ){
        Text(text = "Go")
    }
}

@Composable
fun ArtistPage(navController: NavHostController) {
 val id = navController.currentBackStackEntry?.arguments?.getInt("id") ?: 0
    val art = ArtworkRepository.artworks[id]
}

@Composable
fun ArtistProfile(){

}

@Composable
fun ArtistBio(){

}


@Composable
fun ArtistBackNavigation(){

}
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ArtworkTheme {
        HomePage(rememberNavController())
    }
}