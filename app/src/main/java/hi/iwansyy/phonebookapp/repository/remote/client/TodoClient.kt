package hi.iwansyy.phonebookapp.repository.remote.client

import com.google.gson.GsonBuilder
import hi.iwansyy.phonebookapp.repository.remote.service.TodoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class TodoClient {
    companion object{
        private const val BASE_URL = "https://online-course-todo.herokuapp.com"
        private const val BASE_URL_API = "$BASE_URL/api/"

        val service: TodoService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .build()

            retrofit.create(TodoService::class.java)
        }
    }
}