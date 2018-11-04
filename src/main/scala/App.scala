import domain.WeatherResult
import location.GeoClients.GeoClient
import location.GeoService
import weather.WeatherClients.WeatherClient
import weather.WeatherService

import scala.concurrent.duration.Duration.Inf
import scala.concurrent.{Await, Future}

object Main extends App {

  val geo     = new GeoService(new GeoClient)
  val weather = new WeatherService(new WeatherClient("API_KEY"))

  val app = new WeatherApp(geo, weather)

  val f = app.getWeather("EC1V 4AB")

  f.onComplete(println)

  Await.result(f, Inf)
}

class WeatherApp(geo: GeoService, weather: WeatherService) {

  /**
    * Gets the weather for one place only
    */
  def getWeather(place: String): Future[WeatherResult] = ???

  /**
    * Gets the weather for a sequence of places. Should one call fail, the whole future should fail.
    * HINT: You may want to dig around in the methods available on the Future object...
    */
  def getWeatherForMany(places: Seq[String]): Future[Seq[WeatherResult]] = ???
}
