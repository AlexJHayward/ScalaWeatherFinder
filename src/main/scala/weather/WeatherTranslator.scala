package weather

import com.snowplowanalytics.weather.providers.darksky.Responses
import domain.{Precipitation, Temperature, WeatherResult}

object WeatherTranslator {

  implicit class Translator(in: Responses.DarkSkyResponse) {

    def translate: WeatherResult = {

      val current = in.currently.get

      val temperature = Temperature(
        current = current.temperature,
        currentHigh = current.temperatureHigh,
        currentLow = current.temperatureLow
      )

      val precipitation = Precipitation(
        accumulation = current.precipAccumulation,
        probability = current.precipProbability
      )

      WeatherResult(
        latitude = in.latitude,
        longitude = in.longitude,
        timezone = in.timezone,
        sunriseTime = current.sunriseTime,
        sunsetTime = current.sunsetTime,
        temperature = temperature,
        precipitation = precipitation
      )
    }
  }
}
