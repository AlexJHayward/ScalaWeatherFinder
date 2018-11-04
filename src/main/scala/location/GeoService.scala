package location
import domain.GeoResult
import location.GeoClients.Client

import scala.concurrent.{ExecutionContext, Future}

class GeoService(client: Client)(implicit ec: ExecutionContext) {

  def locate(place: String): Future[GeoResult] = client.lookup(place).map { sequence ⇒
    sequence.map { result ⇒
      GeoTranslator.translate(result)
    }
  }
}