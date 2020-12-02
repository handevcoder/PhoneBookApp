package hi.iwansyy.phonebookapp.repository.remote.client

import android.util.Log
import com.google.gson.GsonBuilder
import hi.iwansyy.phonebookapp.BuildConfig
import hi.iwansyy.phonebookapp.repository.remote.constan.ConstantUtil
import hi.iwansyy.phonebookapp.repository.remote.service.AuthService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthClient {
    companion object {
        val service: AuthService by lazy {
            val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
                if (BuildConfig.DEBUG) Log.e("LOG_API", message)
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
            retrofit.create(AuthService::class.java)
        }
    }

}
