package hi.iwansyy.phonebookapp.repository.remote.service

import hi.iwansyy.phonebookapp.model.BaseModel
import hi.iwansyy.phonebookapp.model.ContactModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ContactService {
        @Multipart
        @POST("v1/contacts")
        suspend fun insertContact(
            @Header("Authorization") token: String,
            @PartMap map: HashMap<String, RequestBody>,
            @Part image: MultipartBody.Part?
        ): BaseModel<ContactModel>
        @Multipart
        @GET("/api/v1/contacts")
        suspend fun getAllContact(
            @Header("Authorization") token: String
        ): BaseModel<List<ContactModel>>

}