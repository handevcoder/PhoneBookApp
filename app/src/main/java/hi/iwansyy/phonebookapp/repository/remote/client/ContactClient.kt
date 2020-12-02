package hi.iwansyy.phonebookapp.repository.remote.client

import android.util.Log
import com.google.gson.GsonBuilder
import hi.iwansyy.phonebookapp.BuildConfig
import hi.iwansyy.phonebookapp.repository.remote.constan.ConstantUtil
import hi.iwansyy.phonebookapp.repository.remote.service.ContactService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ContactClient {
    companion object {
        val service: ContactService by lazy {
            val httpLoggingInterceptor = HttpLoggingInterceptor {
                message -> if (BuildConfig.DEBUG) Log.e("LOG_API", message)
            }
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

            val retrofit = Retrofit
                .Builder()
                .baseUrl(ConstantUtil.BASE_URL_API)
                .client(okHttpClient)
                .addConverterFactory(
                    GsonConverterFactory.create(GsonBuilder().setLenient().create())
                )
                .build()
            retrofit.create(ContactService::class.java)
        }
    }
}