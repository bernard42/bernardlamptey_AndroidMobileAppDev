package com.example.lab3.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ToDoViewModel: ViewModel()
{
    var toDoList = mutableStateListOf<ToDo>()

    fun addToDo(inToDoTask: ToDo){
        toDoList.add(inToDoTask)
    }

    fun deleteToDo(toDoTask: ToDo){
        toDoList.remove(toDoTask)
    }
}

