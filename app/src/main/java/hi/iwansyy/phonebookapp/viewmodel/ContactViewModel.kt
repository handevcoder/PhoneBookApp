package hi.iwansyy.phonebookapp.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hi.iwansyy.phonebookapp.model.ContactModel
import hi.iwansyy.phonebookapp.repository.remote.client.ContactClient
import hi.iwansyy.phonebookapp.repository.remote.constan.SessionUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

sealed class ContactState{
    data class Loading(val message: String = "Loading...") : ContactState()
    data class Error(val exception: Exception) : ContactState()
    data class Create(val data: List<ContactModel>) : ContactState()
}

class ContactViewModel(private val app: Application): AndroidViewModel(app) {
    private val contactService by lazy { ContactClient.service }
    private val mutableState by lazy { MutableLiveData<ContactState>() }
    private val token by lazy { SessionUtil(app.applicationContext).token }
    val state: LiveData<ContactState> get() = mutableState

    fun insertContact(phone: String, name: String, email: String, company: String, job: String, image: String){
        mutableState.value = ContactState.Loading()

        viewModelScope.launch(Dispatchers.IO){
            try {
                val body = hashMapOf<String, RequestBody>()

                body["name"] = name.toRequestBody(MultipartBody.FORM)
                body["phone"] = phone.toRequestBody(MultipartBody.FORM)
                body["email"] = email.toRequestBody(MultipartBody.FORM)
                body["company"] = company.toRequestBody(MultipartBody.FORM)
                body["job"] = job.toRequestBody(MultipartBody.FORM)

                val file = File(image)

                var photo: MultipartBody.Part? = null

                if (image.isNotEmpty() && file.exists()){
                    val bitmap = BitmapFactory.decodeFile(image)
                    val bos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos)

                    FileOutputStream(image, true).apply {
                        write(bos.toByteArray())
                        flush()
                        close()
                    }

                    val fileBody = file.asRequestBody(MultipartBody.FORM)
                    photo = MultipartBody.Part.createFormData(
                        "image",
                        file.name,
                        fileBody
                    )
                }

                val contactModel = contactService.insertContact(token, body, photo).data
                mutableState.postValue(ContactState.Create(listOf(contactModel)))
            } catch (exc: Exception){
                exc.printStackTrace()
                mutableState.postValue(ContactState.Error(exc))
            }
        }
    }

    fun getAllTodo() {
        mutableState.value = ContactState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val contactModel = contactService.getAllContact(token).data
                mutableState.postValue(ContactState.Create(contactModel))
            } catch (exc: Exception) {
                exc.printStackTrace()
            }
        }
    }

}