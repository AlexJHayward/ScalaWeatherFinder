package location

import com.koddi.geocoder.Result
import domain.GeoResult

object GeoTranslator {

  def translate(v: Result): GeoResult = GeoResult(
    name = v.formattedAddress,
    latitude = v.geometry.location.latitude.floatValue,
    longitude = v.geometry.location.longitude.floatValue
  )
}
