package com.example.andrifitness

import PreDesignedWorkoutLayout
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val measurementViewModel = viewModel<MeasurementViewModel>()
    val userProfileViewModel = viewModel<UserProfileViewModel>()
    val workoutProgressViewModel = viewModel<WorkoutProgressViewModel>()

    NavHost(navController = navController, startDestination = ApplicationScreens.WorkoutApplicationScreen.route) {
        composable(route = ApplicationScreens.WorkoutApplicationScreen.route) {
            WorkoutLayout(navController = navController)
        }
        composable(route = ApplicationScreens.WorkoutCreationApplicationScreen.route) {
            WorkoutCreationLayout(navController = navController)
        }
        composable(route = ApplicationScreens.ExercisesApplicationScreen.route) {
            ExercisesLayout(navController = navController)
        }
        composable(route = ApplicationScreens.LogsApplicationScreen.route) {
            LogsPage(navController = navController)
        }
        composable(route = ApplicationScreens.LogsCreationApplicationScreen.route) {
            LogsCreationPage(navController = navController)
        }
        composable(route = ApplicationScreens.MeasurementsApplicationScreen.route) {
            MeasurementsLayout(navController = navController, measurementViewModel = measurementViewModel)
        }
        composable(route = ApplicationScreens.NewWorkoutScreen.route) {
            NewWorkoutLayout(navController = navController)
        }
        composable(route = ApplicationScreens.NewLogsScreen.route) {
            NewLogsPage(navController = navController)
        }
        composable(route = ApplicationScreens.PreDesignedWorkoutScreen.route) {
            PreDesignedWorkoutLayout(navController = navController)
        }
        composable(route =ApplicationScreens.UserProfileScreen.route){

            UserProfileLayout(navController = navController, userProfileViewModel = userProfileViewModel)

        }
        composable(route = ApplicationScreens.UserProfileForm.route){
            UserProfileForm(navController = navController, userProfileViewModel = userProfileViewModel)
        }
        composable(route = ApplicationScreens.WorkoutProgress.route){
            WorkoutProgress(navController = navController, workoutProgressViewModel = workoutProgressViewModel)
        }
        composable(route = ApplicationScreens.MeasurementHistoryScreen.route){
            MeasurementHistory(navController = navController, measurementViewModel = MeasurementViewModel() )
        }
    }
}






