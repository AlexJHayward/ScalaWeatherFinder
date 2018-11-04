package domain

case class WeatherResult(latitude: Float,
                         longitude: Float,
                         timezone: String,
                         sunriseTime: Option[Long],
                         sunsetTime: Option[Long],
                         temperature: Temperature,
                         precipitation: Precipitation)

case class Temperature(current: Option[Float], currentHigh: Option[Float], currentLow: Option[Float])

case class Precipitation(accumulation: Option[Float], probability: Option[Float])
