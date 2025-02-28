package fr.isen.capucine.isensmartcompanion.api;

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private const val baseUrl = "https://isen-smart-companion-default-rtdb.europe-west1.firebasedatabase.app"

    //Lazy est instancié des quil est appelé et non pas a la compilation
    val api : ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
