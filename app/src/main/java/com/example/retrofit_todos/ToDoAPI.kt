package com.example.retrofit_todos

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

interface ToDoAPI {

    @GET("/todos")
    suspend fun getToDos(): List<ToDo>

    companion object{
        var todoApi: ToDoAPI? = null
        fun getInstance(): ToDoAPI{
            if (todoApi == null){
                todoApi = Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ToDoAPI::class.java)
            }
            return todoApi!!
        }
    }
}