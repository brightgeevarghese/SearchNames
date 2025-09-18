package com.example.searchdemo

import android.R.attr.text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.searchdemo.ui.theme.SearchdemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SearchdemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SearchNames(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNames(
    modifier: Modifier = Modifier
) {

    val names = remember { mutableStateListOf(
        "Tom", "Jerry", "Mickey", "Donald", "Daisy", "Huey", "Dewey", "Louie", "Goofy", "Pluto", "Minnie", "Vinnie", "Roadhog", "Piggy", "Mona", "Frozen"
    ) }
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    val filteredNames = names.filter { it.contains(text, ignoreCase = true) }
    Scaffold(
        modifier = modifier,
        topBar = {
            DockedSearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = text, //The current text shown inside the search box
                        // Callback whenever the user types something.
                        // Updates the state variable `text` with the new input.
                        onQueryChange = { text = it },
                        // Callback when the user presses the search key (IME action).
                        // Collapses the search bar by setting `expanded` to false.
                        onSearch = { expanded = false },
                        expanded = expanded,
                        // Callback whenever the expanded state changes (user taps, focuses, etc.)
                        onExpandedChange = { expanded = it },
                        placeholder = { Text("Hinted search text") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
                    )
                },
                // Controls whether the result area below the search bar is visible.
                expanded = expanded,//if it is true, it shows expanded
                onExpandedChange = { expanded = it },
            ) {
                Text(
                    text = "Results for: $text",
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    names.add("New Item")
                }
            ){
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) {
        innerPadding ->

        LazyColumn(modifier = modifier.padding(innerPadding)) {
            items(filteredNames.size) {
                Text(text = filteredNames[it], modifier = Modifier.padding(16.dp))
            }
        }
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SearchdemoTheme {
        SearchNames()
    }
}