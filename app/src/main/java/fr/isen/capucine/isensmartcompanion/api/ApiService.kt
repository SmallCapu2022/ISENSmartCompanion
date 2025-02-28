package fr.isen.capucine.isensmartcompanion.api
import fr.isen.capucine.isensmartcompanion.models.EventModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("events.json")
    suspend fun getEvents(): Response<List<EventModel>>
}