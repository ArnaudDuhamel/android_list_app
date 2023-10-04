package com.example.list_tutorial

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.example.list_tutorial.ui.theme.Purple40
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.graphics.RectangleShape
import com.example.list_tutorial.ui.theme.DarkBlue
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalFocusManager

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun funData() {
    val focusManager = LocalFocusManager.current

// this variable use to handle list state
    val notesList = remember {
        mutableStateListOf<String>()
    }

    var checkBoxStates = remember {
        mutableStateMapOf<Int, Boolean>().withDefault { false }
    }
// this variable use to handle edit text input value
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
                    singleLine = true
                )
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxWidth(0.85f)
            ){
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (!Regex("^$| +$").matches(inputValue.value.text)) {
                            notesList.add(inputValue.value.text)
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
                            notesList.forEachIndexed { index, element ->
                               if (checkBoxStates.getValue(index) == true) {
                                   notesList.remove(element)
                               }
                            }

                            checkBoxStates.forEach {
                                if (it.value){
                                    checkBoxStates.remove(it.key)
                                }
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

                    itemsIndexed(notesList) {index , item ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(50.dp)
                                .toggleable(
                                    value = checkBoxStates.getValue(index),
                                    onValueChange = { checkBoxStates[index] = it }
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = item,
                                modifier = Modifier
                                    .weight(0.92f)
                            )
                            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                                Checkbox(
                                    checked = checkBoxStates.getValue(index),
                                    onCheckedChange = {
                                        checkBoxStates[index] = it
                                    },
                                    modifier = Modifier
                                        .weight(0.08f)
                                        .padding(start = 5.dp)
                                )
                            }
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