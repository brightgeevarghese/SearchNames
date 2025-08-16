package com.example.searchdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
    var query by remember { mutableStateOf("") }
    val filteredNames = names.filter { it.contains(query, ignoreCase = true) }
    Scaffold(
        modifier = modifier,
        topBar = {
            DockedSearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = query, //The text currently typed in the search box
                        onQueryChange = { input -> query = input },//{query = it} // A function that runs whenever the user types something — updates query state.
                        onSearch = { expanded = false },//After pressing the search key, the search bar collapses (results area closes).
                        expanded = expanded,
                        onExpandedChange = { status -> expanded = !status },
                        placeholder = { Text(text = "Search") }
                    )
                },
                expanded = expanded,//if it is true, it shows expanded
                onExpandedChange = { expanded = it },
            ) {
                Text(
                    text = "Results for: $query",
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