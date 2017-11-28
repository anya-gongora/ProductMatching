package models

import org.scalatest.FlatSpec
import play.api.libs.json.Json

class ListingSpec extends FlatSpec {

  "Listing json" should "convert into Listing cass" in {
    val json = Json.parse(
      """
        |{
        |  "title":"Sony Cyber-shot DSC-W310 - Digital camera - compact - 12.1 Mpix - optical zoom: 4 x - supported memory: MS Duo, SD, MS PRO Duo, MS PRO Duo Mark2, SDHC, MS PRO-HG Duo - silver",
        |  "manufacturer":"Sony",
        |  "currency":"USD",
        |  "price":"108.75"
        |}
      """.stripMargin
    )

    val listing = json.as[Listing]
    assert(listing.title === "Sony Cyber-shot DSC-W310 - Digital camera - compact - 12.1 Mpix - optical zoom: 4 x - supported memory: MS Duo, SD, MS PRO Duo, MS PRO Duo Mark2, SDHC, MS PRO-HG Duo - silver")
    assert(listing.manufacturer === "Sony")
    assert(listing.currency === "USD")
    assert(listing.price === "108.75")
  }

}
