package com.example.retrofit_todos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ToDoViewModel: ViewModel() {
    private val _todos = MutableStateFlow<List<ToDo>>(emptyList())
    val todos: StateFlow<List<ToDo>> = _todos

    init {
        fetchToDos()
    }

    private fun fetchToDos(){
        viewModelScope.launch {
            val todoApi = ToDoAPI.getInstance()
            try {
                val todoList = todoApi.getToDos()
                _todos.value = todoList
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun updateToDoState(updatedToDo: ToDo){
        _todos.value = _todos.value.map {todo ->
            if (todo.id == updatedToDo.id){
                updatedToDo
            }
            else{
                todo
            }
        }
    }
}