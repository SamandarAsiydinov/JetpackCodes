package uz.context.jetpackcodes.compose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String) {

    sealed class DrawerScreen(route: String, title: String, val icon: ImageVector
    ): Screen(route, title) {
        object HomeScreen: DrawerScreen("HomeScreen", "Home", Icons.Filled.Home)
        object SettingsScreen: DrawerScreen("HomeScreen", "Setting", Icons.Filled.Settings)
        object FavoriteScreen: DrawerScreen("HomeScreen", "Favorite", Icons.Filled.Favorite)
        object ProfileScreen: DrawerScreen("HomeScreen", "Profile", Icons.Filled.Person)
    }
}

val screenFromDrawer = listOf(
    Screen.DrawerScreen.HomeScreen,
    Screen.DrawerScreen.SettingsScreen,
    Screen.DrawerScreen.FavoriteScreen,
    Screen.DrawerScreen.ProfileScreen
)
