package weather

import cats.effect.IO
import com.snowplowanalytics.weather.Errors.WeatherError
import com.snowplowanalytics.weather.providers.darksky.Responses.DarkSkyResponse
import com.snowplowanalytics.weather.providers.darksky._
import domain.WeatherResult
import domain.mock.MockResponses

import scala.concurrent.{ExecutionContext, Future}

object WeatherClients {

  trait Client {
    def forecast(lat: Float, long: Float): Future[Either[WeatherError, DarkSkyResponse]]
  }

  class WeatherClient(apiKey: String)(implicit ec: ExecutionContext) extends Client {

    private val client = DarkSky.basicClient[IO](apiKey)

    override def forecast(lat: Float, long: Float): Future[Either[WeatherError, DarkSkyResponse]] = {

      val f = client.forecast(lat, long)

      f.unsafeToFuture()
    }
  }
}
