package fr.isen.capucine.isensmartcompanion.models

import android.content.Context
import fr.isen.capucine.isensmartcompanion.R
import java.io.Serializable

class EventModel (
    val id: String,
    val title: String,
    val description: String,
    val date : String,
    val location : String,
    val category : String) : Serializable
{
    companion object {
        fun fakeEvents(context: Context): List<EventModel> {
            return listOf(
                EventModel(
                    "1e2d345a",
                    context.getString(R.string.event_gala_title),
                    context.getString(R.string.event_gala_description),
                    context.getString(R.string.event_gala_date),
                    context.getString(R.string.event_gala_location),
                    context.getString(R.string.event_gala_category)
                ),
                EventModel(
                    "3oeirg9",
                    context.getString(R.string.event_charity_title),
                    context.getString(R.string.event_charity_description),
                    context.getString(R.string.event_charity_date),
                    context.getString(R.string.event_charity_location),
                    context.getString(R.string.event_charity_category)
                )
            )
        }
    }
}