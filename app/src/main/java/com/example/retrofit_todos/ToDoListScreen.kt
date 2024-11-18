package com.example.retrofit_todos

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ToDoListScreen(){
    val viewModel: ToDoViewModel = viewModel()
    val todos = viewModel.todos.collectAsState()

    Scaffold(
        content = {padding ->
            LazyColumn(modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
                verticalArrangement = Arrangement.Center) {
                items(todos.value){todo ->
                    ToDoItem(todo = todo, onCheckedChange = {updatedToDo ->
                        viewModel.updateToDoState(updatedToDo)
                    })
                }
            }
        }
    )
}

@Composable
fun ToDoItem(todo: ToDo, onCheckedChange: (ToDo) -> Unit){

    var isChecked by remember {
        mutableStateOf(todo.completed)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .background(if (isChecked) Color.Red else Color.LightGray)
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier
                .weight(1f)) {
                Text(todo.title)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = if (todo.completed) "Completed" else "Pending")
            }
            Checkbox(
                checked = isChecked,
                onCheckedChange = {checked ->
                    isChecked = checked
                    onCheckedChange (todo.copy(completed = checked))
                }
            )
        }
    }
}