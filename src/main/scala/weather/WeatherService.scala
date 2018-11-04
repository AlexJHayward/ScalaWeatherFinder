package weather

import domain.WeatherResult
import weather.WeatherClients.Client
import weather.WeatherTranslator._

import scala.concurrent.{ExecutionContext, Future}

class WeatherService(client: Client)(implicit ec: ExecutionContext) {

  def getForecast(lat: Float, long: Float): Future[WeatherResult] = {
    val forecast = client.forecast(lat, long)

    forecast.map {
      case Right(value) ⇒ Future.successful(value.translate)
      case Left(ex)     ⇒ Future.failed(ex)
    }
  }
}
