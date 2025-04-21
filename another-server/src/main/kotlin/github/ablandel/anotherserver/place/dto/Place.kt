package github.ablandel.anotherserver.place.dto

abstract class Place(
    val type: String,
)

class OpenPlace : Place(type = PlaceTypeEnum.OPEN.type)

class RestrictedPlace : Place(type = PlaceTypeEnum.RESTRICTED.type)
