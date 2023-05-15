package com.example.andrifitness

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlin.math.sqrt

@Composable
fun MeasurementsLayout(navController: NavHostController) {
    val height = remember { mutableStateOf(0f) }
    val weight = remember { mutableStateOf(0f) }
    val bodyFat = remember { mutableStateOf(0f) }
    val muscleMass = remember { mutableStateOf(0f) }
    val constraints = ConstraintSet {
        val measurementsArea = createRefFor("measurementsArea")
        val bottomButtons = createRefFor("bottomButtons")

        constrain(measurementsArea) {
            top.linkTo(parent.top)
        }
        constrain(bottomButtons) {
            top.linkTo(measurementsArea.bottom)
        }
    }

    ConstraintLayout(
        constraints, modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.9f)
                .layoutId("measurementsArea")
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightGray
                ),
                value = height.value.toString(),
                label = { Text("Height", color = Color.Black) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                onValueChange = { height.value = it.toFloatOrNull() ?: 0f }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightGray
                ),
                value = weight.value.toString(),
                label = { Text("Weight", color = Color.Black) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                onValueChange = { weight.value = it.toFloatOrNull() ?: 0f }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightGray
                ),
                value = bodyFat.value.toString(),
                label = { Text("Body Fat Percentage", color = Color.Black) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                onValueChange = { bodyFat.value = it.toFloatOrNull() ?: 0f }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightGray
                ),
                value = muscleMass.value.toString(),
                label = { Text("Muscle Mass", color = Color.Black) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                onValueChange = { muscleMass.value = it.toFloatOrNull() ?: 0f }
            )

            Button(
                onClick = {navController.navigate(ApplicationScreens.MeasurementHistoryScreen.route + "/${height.value}/${weight.value}/${bodyFat.value}/${muscleMass.value}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp),
                enabled = weight.value != 0f && bodyFat.value != 0f && muscleMass.value != 0f
            ) {
                Text(text = "Add Measurement")
            }
        }
        BottomButtons(navController)
    }
}

@Composable
fun MeasurementHistory(
    navController: NavHostController,
    measurementViewModel: MeasurementViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val arguments = navBackStackEntry?.arguments
    val height = arguments?.getFloat("height")
    val weight = arguments?.getFloat("weight")
    val bodyFat = arguments?.getFloat("bodyFat")
    val muscleMass = arguments?.getFloat("muscleMass")

    val measurements = measurementViewModel.measurementList.value ?: emptyList()

    measurementViewModel.addMeasurement(
        height,
        weight,
        bodyFat,
        muscleMass
    )

    val constraints = ConstraintSet {
        val topButtons = createRefFor("topButtons")
        val pageName = createRefFor("pageName")
        val measurementLog = createRefFor("measurementLog")

        constrain(topButtons) {
            top.linkTo(parent.top)
        }
        constrain(pageName) {
            top.linkTo(topButtons.bottom)
        }
        constrain(measurementLog) {
            top.linkTo(pageName.bottom)
        }
    }
    ConstraintLayout(
        constraints,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
                .layoutId("topButtons"),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    navController.navigate(ApplicationScreens.MeasurementsApplicationScreen.route)
                },
                modifier = Modifier
                    .requiredHeight(WButtonRequiredHeight)
                    .requiredWidth(WButtonRequiredWidth),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = WButtonBackgroundColor,
                    contentColor = WButtonContentColor
                )
            ) {
                Text(
                    text = "Done",
                    fontSize = WButtonFontSizes
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
                .layoutId("pageName")
        ) {
            Text(
                text = "Measurement History",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.80f)
                .layoutId("measurementLog")
        ) {

            if (measurements.isEmpty()) {
                Text("No measurements yet.")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(measurements) { index, measurement ->
                        Text(
                            text = "Index ${index + 1}- Height: ${measurement.height} in, Weight: ${measurement.weight} kg, Body Fat=${measurement.bodyFat}%, Muscle Mass=${measurement.muscleMass}%, BMI: " + BMICalculator(
                                measurement.weight,
                                measurement.height
                            ),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = WButtonBackgroundColor,
                    contentColor = WButtonContentColor
                )
            ) {
                Text("Back to Profile")
            }
        }
    }
}

fun BMICalculator(weight: Float, height: Float):Float {
    return (weight/sqrt(((height/100).toDouble()))).toFloat()
}
data class Measurement(
    val height: Float,
    val weight: Float,
    val bodyFat: Float,
    val muscleMass: Float
)

class MeasurementViewModel : ViewModel() {
    var measurementList = MutableLiveData(mutableListOf<Measurement>())

    fun addMeasurement(height: Float?, weight: Float?, bodyFat: Float?, muscleMass: Float? = 0f) {
        height?.let { h ->
            weight?.let { w ->
                bodyFat?.let { bf ->
                    muscleMass?.let { mm ->
                        val measurements = measurementList.value ?: mutableListOf()
                        val newMeasurement = Measurement(
                            height = h,
                            weight = w,
                            bodyFat = bf,
                            muscleMass = mm
                        )
                        measurements.add(newMeasurement)
                        measurementList.value = measurements
                    }
                }
            }
        }
    }

}
