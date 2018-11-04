package location

import com.koddi.geocoder.{Geocoder, Result}

import scala.concurrent.{ExecutionContext, Future}

object GeoClients {

  trait Client {
    def lookup(location: String): Future[Seq[Result]]
  }

  class GeoClient(implicit ec: ExecutionContext) extends Client {

    //pls don't steal my API key...
    private val coder = Geocoder.create("API_KEY")

    override def lookup(location: String): Future[Seq[Result]] = {
      Future(coder.lookup(location))
    }
  }
}
