import com.koddi.geocoder.Location.APPROXIMATE
import com.koddi.geocoder._
import com.snowplowanalytics.weather.Errors
import com.snowplowanalytics.weather.providers.darksky.Responses.{DarkSkyResponse, DataPoint}
import domain.{GeoResult, Precipitation, Temperature, WeatherResult}
import location.GeoClients.{Client ⇒ GeoClient}
import location.GeoService
import weather.WeatherClients.{Client ⇒ WeatherClient}
import weather.WeatherService

import scala.concurrent.{ExecutionContext, Future}

object TestObjects {

  class MockGeoClient extends GeoClient {
    override def lookup(location: String): Future[Seq[Result]] = location match {
      case "London"    => Future.successful(Seq(geoCoderResult))
      case "Hong Kong" => Future.successful(Seq(geoCoderResult.copy(formattedAddress = "Hong Kong")))
      case _           => Future.failed(new RuntimeException(s"I don't know where $location is"))
    }
  }

  class MockWeatherClient extends WeatherClient {
    override def forecast(lat: Float, long: Float): Future[Either[Errors.WeatherError, DarkSkyResponse]] =
      Future.successful(Right(darkSkyResponse.copy(latitude = lat, longitude = long)))
  }

  def newMockWeatherService(implicit ec: ExecutionContext) = new WeatherService(new MockWeatherClient)

  def newMockGeoService(implicit ec: ExecutionContext) = new GeoService(new MockGeoClient)

  val someGeometry = Geometry(
    location = Location(1f, 2f),
    locationType = APPROXIMATE,
    viewport = GeometryBounds(Location(5d, 6d), Location(7d, 8d)),
    bounds = None
  )

  val geoCoderResult = Result(
    placeId = "ChIJc2LNREMbdkgRZ6Xh54AoUco",
    formattedAddress = "Clerkenwell, London, EC1V 4AB, UK",
    geometry = someGeometry,
    addressComponents = Seq.empty,
    postcodeLocalities = None,
    partialMatch = false,
    types = Seq.empty
  )

  val darkSkyResponse = DarkSkyResponse(
    latitude = someGeometry.location.latitude.toFloat,
    longitude = someGeometry.location.longitude.toFloat,
    timezone = "Europe/London",
    currently = Some(
      DataPoint(
        apparentTemperature = None,
        apparentTemperatureHigh = None,
        apparentTemperatureHighTime = None,
        apparentTemperatureLow = None,
        apparentTemperatureLowTime = None,
        cloudCover = None,
        dewPoint = None,
        humidity = None,
        icon = None,
        moonPhase = None,
        nearestStormBearing = None,
        nearestStormDistance = None,
        ozone = None,
        precipAccumulation = Some(10f),
        precipIntensity = None,
        precipIntensityMax = None,
        precipIntensityMaxTime = None,
        precipProbability = Some(0.5f),
        precipType = None,
        pressure = None,
        summary = None,
        sunriseTime = Some(7L),
        sunsetTime = Some(16L),
        temperature = Some(10.0f),
        temperatureHigh = Some(14.0f),
        temperatureHighTime = None,
        temperatureLow = Some(5.0f),
        temperatureLowTime = None,
        temperatureMax = None,
        temperatureMaxTime = None,
        temperatureMin = None,
        temperatureMinTime = None,
        time = 10L,
        uvIndex = None,
        uvIndexTime = None,
        visibility = None,
        windBearing = None,
        windGust = None,
        windSpeed = None
      )
    ),
    minutely = None,
    hourly = None,
    daily = None,
    alerts = None,
    flags = None
  )

  val expectedWeatherResponse = WeatherResult(
    latitude = geoCoderResult.geometry.location.latitude.toFloat,
    longitude = geoCoderResult.geometry.location.longitude.toFloat,
    timezone = "Europe/London",
    sunriseTime = Some(7),
    sunsetTime = Some(16),
    temperature = Temperature(Some(10.0f), Some(14.0f), Some(5.0f)),
    precipitation = Precipitation(Some(10.0f), Some(0.5f))
  )

  val expectedGeoResponse = GeoResult(
    name = geoCoderResult.formattedAddress,
    latitude = geoCoderResult.geometry.location.latitude.floatValue,
    longitude = geoCoderResult.geometry.location.longitude.floatValue
  )
}
