package uz.context.jetpackcodes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import kotlinx.coroutines.launch
import uz.context.jetpackcodes.compose.grid.SampleDetailsGrid
import uz.context.jetpackcodes.compose.grid.SampleGrid
import uz.context.jetpackcodes.compose.home.Drawer
import uz.context.jetpackcodes.compose.home.HomeScreen
import uz.context.jetpackcodes.compose.login.LoginPage
import uz.context.jetpackcodes.compose.login.RegisterPage
import uz.context.jetpackcodes.compose.navigation.Screen
import uz.context.jetpackcodes.compose.simplelist.SampleDataDetails
import uz.context.jetpackcodes.compose.simplelist.SampleList
import uz.context.jetpackcodes.response.SampleData
import uz.context.jetpackcodes.ui.theme.JetpackCodesTheme
import uz.context.jetpackcodes.ui.theme.Purple500
import uz.context.jetpackcodes.util.Constants

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackCodesTheme {
                NavigationDrawer()
            }
        }
    }
}

@Composable
fun NavigationDrawer() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState(
        rememberDrawerState(initialValue = DrawerValue.Closed)
    )
    val topBar: @Composable () -> Unit = {
        TopAppBar(
            title = {
                Text(text = "Android Dev")
            },
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu")
                }
            },
            actions = {
                IconButton(onClick = {
                    Toast.makeText(context, "Menu clicked", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "More")
                }
            },
            backgroundColor = Purple500,
            elevation = AppBarDefaults.TopAppBarElevation
        )
    }
    val drawer: @Composable () -> Unit = {
        Drawer { title, route ->
            scope.launch {
                scaffoldState.drawerState.close()
            }
            Constants.title = title

            navController.navigate(route = route)
        }
    }
    Scaffold(
        topBar = {
            topBar()
        },
        scaffoldState = scaffoldState,
        drawerContent = {
            drawer()
        },
        drawerGesturesEnabled = true
    ) {
        NavigationHost(navController = navController)
    }
}

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.DrawerScreen.HomeScreen.route
    ) {
        composable(route = Screen.DrawerScreen.HomeScreen.route) {
            HomeScreen(value = Constants.title)
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun NavigationGridPage() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "sample_data"
    ) {
        composable(route = "sample_data") {
            SampleGrid(navController)
        }
        composable("sample_grid_detail/{item}",
            arguments = listOf(
                navArgument("item") {
                    type = NavType.StringType
                }
            )) { backStackEntry ->
            backStackEntry.arguments?.getString("item")?.let { json ->
                val item = Gson().fromJson(json, SampleData::class.java)
                SampleDataDetails(data = item)
            }
        }
    }
}

@Composable
fun NavigationPageSample() {
    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = "sample_data"
    ) {
       composable("sample_data") {
           SampleList(navController = navHostController)
       }
        composable("sample_detail/{item}",
        arguments = listOf(
            navArgument("item") {
                type = NavType.StringType
            }
        )) { backStackEntry ->
            backStackEntry.arguments?.getString("item")?.let { json ->
                val item = Gson().fromJson(json, SampleData::class.java)
                SampleDataDetails(data = item)
            }
        }
    }
}

@Composable
fun NavigationPage() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login_page") {
        composable(route = "login_page", content = { LoginPage(navController = navController) })
        composable(
            route = "register_page",
            content = { RegisterPage(navController = navController) }
        )
        composable(route = "sample_grid_detail/{item}", arguments = listOf(
            navArgument("item") {
                type = NavType.StringType
            }
        )) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("item")?.let { json ->
                val item = Gson().fromJson(json, SampleData::class.java)
                SampleDetailsGrid(data = item)
            }
        }
    }
}
