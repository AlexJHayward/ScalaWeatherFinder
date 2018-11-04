import TestObjects._
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.{FunSuite, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global

class WeatherAppSpec extends FunSuite with Matchers with ScalaFutures with IntegrationPatience {

  test("should be able to get a GeoResponse") {

    val r = newMockGeoService.locate("London")
    whenReady(r) {v =>
      v shouldBe expectedGeoResponse
    }
  }

  test("should get the weather from a place") {

    val r = new WeatherApp(newMockGeoService, newMockWeatherService).getWeather("London")
    whenReady(r) { v ⇒
      v shouldBe expectedWeatherResponse
    }
  }

  test("should get the weather from several places") {

    val places = Seq("London", "Swansea", "Hong Kong")

    val r = new WeatherApp(newMockGeoService, newMockWeatherService).getWeatherForMany(places)

    whenReady(r.failed) { ex =>
      ex.getMessage shouldBe "I don't know where Swansea is"
    }
  }
}
