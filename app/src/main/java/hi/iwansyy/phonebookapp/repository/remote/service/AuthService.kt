package hi.iwansyy.phonebookapp.repository.remote.service

import hi.iwansyy.phonebookapp.model.AuthModel
import hi.iwansyy.phonebookapp.model.BaseModel
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("v1/signin")
    suspend fun login(@Body body: HashMap<String, Any>): BaseModel<AuthModel>
}