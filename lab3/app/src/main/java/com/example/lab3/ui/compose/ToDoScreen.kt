@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)

package com.example.lab3.ui.compose

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import com.example.lab3.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import com.example.lab3.model.ToDo
import com.example.lab3.model.ToDoViewModel
import java.util.*

@Composable
fun ToDoScreen() {
    val toDoViewModel: ToDoViewModel = viewModel()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        backgroundColor = MaterialTheme.colors.surface,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {showDialog = true}
            )
            {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
        },
        topBar = { TopAppBar(title = { Text(text= stringResource(id = R.string.title), color = Color.White) },
            backgroundColor = Color(0xff0f9d58)) },
        content = {
            if (showDialog){
                addTaskDialog(context, dismissDialog = {showDialog = false}, {toDoViewModel.addToDo(it)})
            }

            LazyColumn(
                contentPadding = PaddingValues(
                    vertical = 8.dp,
                    horizontal = 8.dp
                )
            )
            {
                items(toDoViewModel.toDoList, key={toDoTask -> toDoTask.id}) { toDoTask ->
                    var checkedState = remember { mutableStateOf(false) }
                    Row {
                        Checkbox(
                            checked = checkedState.value,
                            onCheckedChange = { checkedState.value = it },
                        )

                        ToDoItem(
                            toDoObject = toDoTask,
                            context,
                            {
                                toDoViewModel.deleteToDo(it)
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun addTaskDialog(context: Context, dismissDialog:() -> Unit, addToDoTask: (ToDo) -> Unit){
    var taskTextField by remember { mutableStateOf("") }
    var mDate by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { dismissDialog},
        title={ Text(text = stringResource(id = R.string.addTask), style = MaterialTheme.typography.h6) },
        text = {
            Column(modifier = Modifier.padding(top=20.dp)) {
                TextField(label = { Text(text= stringResource(id = R.string.taskName)) }, value = taskTextField, onValueChange = {taskTextField=it})
                Spacer(modifier = Modifier.height(10.dp))
                var year: Int
                var month: Int
                var day: Int

                var calendar = Calendar.getInstance()
                year = calendar.get(Calendar.YEAR)
                month = calendar.get(Calendar.MONTH)
                day = calendar.get(Calendar.DAY_OF_MONTH)
                calendar.time = Date()

                val mDatePickerDialog = DatePickerDialog(context,
                    { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                        mDate = "$mDayOfMonth/${mMonth+1}/$mYear"}, year, month, day)

                Button(onClick = { mDatePickerDialog.show() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray))
                {
                    Text(text = stringResource(id = R.string.chooseDate), color = Color.White)
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if(taskTextField.isNotEmpty()) {
                    val newID = UUID.randomUUID().toString();
                    addToDoTask(ToDo(newID, taskTextField, mDate))
                    Toast.makeText(
                        context,
                        context.resources.getString(R.string.toDoAdded),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                dismissDialog()
            })
            {
                Text(text = stringResource(id = R.string.add))
            }
        },dismissButton = {
            Button(onClick = {
                dismissDialog()
            }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
}

@Composable
fun deleteTaskDialog(context: Context, dismissDialog:() -> Unit, toDoTask: ToDo, deleteTask: (ToDo) -> Unit){
    AlertDialog(
        onDismissRequest = { dismissDialog},
        title={ Text(text = stringResource(id = R.string.delete), style = MaterialTheme.typography.h6) },
        confirmButton = {
            Button(onClick = {
                deleteTask(toDoTask)
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.deleteTask),
                    Toast.LENGTH_SHORT
                ).show()
                dismissDialog()
            })
            {
                Text(text = stringResource(id = R.string.yes))
            }
        },dismissButton = {
            Button(onClick = {
                dismissDialog()
            }) {
                Text(text = stringResource(id = R.string.no))
            }
        }
    )
}

@Composable
fun ToDoItem(toDoObject: ToDo, context: Context, deleteTask: (ToDo) -> Unit) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        border = BorderStroke(2.dp, color = MaterialTheme.colors.primaryVariant),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    Toast
                        .makeText(
                            context,
                            context.resources.getString(com.example.lab3.R.string.readmsg) + " " + toDoObject.taskName,
                            Toast.LENGTH_SHORT
                        )
                        .show()
                },
                onLongClick = { showDeleteDialog = true }
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = toDoObject.taskName, style = MaterialTheme.typography.h6)
            Text(text = toDoObject.dueDate.toString(), style = MaterialTheme.typography.body1)
        }

    }
    if (showDeleteDialog){
        deleteTaskDialog(context, dismissDialog = {showDeleteDialog = false}, toDoObject, deleteTask)
    }
}
