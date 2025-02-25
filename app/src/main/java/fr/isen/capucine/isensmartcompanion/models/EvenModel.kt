package fr.isen.capucine.isensmartcompanion.models

class EventModel (
    val id: String,
    val title: String,
    val description: String,
    val date : String,
    val location : String,
    val category : String)
{
    companion object {
        fun fakeEvents() : List<EventModel> {
            return listOf(
                EventModel(
                    "1e2d345a",
                    "Gala annuel de l'ISEN",
                    "Soirée prestigieuse organisée par le BDE",
                    "10 avril 2025",
                    "Palais Neptune, Toulon",
                    "BDE"
                ),
                EventModel(
                    "3oeirg9",
                    "Soirée Caritative Isen Partage",
                    "Soirée prestigieuse organisée par Isen Partage pour l'Unicef",
                    "10 mai 2025",
                    "Carré Sud, Toulon",
                    "Partage"
                )
            )
        }
    }
}
