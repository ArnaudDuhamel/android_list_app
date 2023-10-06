package com.example.list_tutorial

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun funData() {
    val DarkBlue = Color(0xFF495d92)

    // To remove focus on the textfield when pressing on the add button and on
    // the dome ime action
    val focusManager = LocalFocusManager.current

    // The variable used to give a unique id to each list item
    var id = 0

    // The map of text inputs
    val notesList = remember {
        mutableStateListOf<Pair<Int,String>>()
    }

    // The variable to see if the box of a list item is checked or not
    val checkBoxStates = remember {
        mutableStateMapOf<Int, Boolean>().withDefault { false }
    }

    // The input in the text field is stored
    val inputValue = remember { mutableStateOf(TextFieldValue()) }

    Scaffold(
        topBar =  {
            CenterAlignedTopAppBar(
                title = { Text(
                    text = "Grocery list",
                    color = DarkBlue,
                    fontWeight = FontWeight.Bold
                ) },
                colors = topAppBarColors(
                    Color.White
                ),

            )
        },
        modifier = Modifier
            .fillMaxWidth()
    ) { paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

                // input text field
                TextField(
                    value = inputValue.value,
                    onValueChange = {
                        inputValue.value = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(
                        text = "Add an item...",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        autoCorrect = true,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = TextUnit.Unspecified,
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 1,
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    })
                )
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxWidth(0.85f)
            ){
                // The row with the 3 action buttons
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (!Regex("^$| +$").matches(inputValue.value.text)) {
                            notesList.add(Pair(id, inputValue.value.text))
                            id++
                            focusManager.clearFocus()
                        }
                        inputValue.value = TextFieldValue()
                    },
                    shape = RectangleShape
                ) {
                    Text(text = "add")
                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {

                              notesList.removeIf { pair ->
                                  checkBoxStates.getValue(pair.first) == true
                              }

                        checkBoxStates.values.removeIf {value ->
                                  value == true
                        }

                              }
                    ,
                    shape = RectangleShape
                ) {
                    Text(text = "delete")
                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        notesList.clear()
                        checkBoxStates.clear()
                        id = 0
                    },
                    shape = RectangleShape
                ) {
                    Text(text = "clear")
                }
            }

            Spacer(modifier = Modifier.height(2.5.dp))

            Text(
                text = "My list",
                color = DarkBlue,
                fontWeight = FontWeight.Normal
            )


            Spacer(modifier = Modifier.height(5.dp))


            Surface() {
                LazyColumn (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // here the notesList is changed into a list because the itemsIndexed
                    // function takes a list
                    itemsIndexed(notesList) {_, pair ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(50.dp)
                                .toggleable(
                                    value = checkBoxStates.getValue(pair.first),
                                    onValueChange = { checkBoxStates[pair.first] = it }
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // This text object contains the list item itself
                            Text(
                                text = pair.second,
                                modifier = Modifier
                                    .weight(0.92f)
                            )
                                // The checkbox. Great care was take to look as much as possible
                                // as the presented sketch
                                Checkbox(
                                    checked = checkBoxStates.getValue(pair.first),
                                    onCheckedChange = {
                                        checkBoxStates[pair.first] = it
                                    },
                                    modifier = Modifier
                                        .weight(0.08f)
                                        .padding(start = 5.dp)
                                )
                            }

                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(0.85f),
                            thickness = 1.dp
                        )
                    }
                }
            }


        }


    }

}