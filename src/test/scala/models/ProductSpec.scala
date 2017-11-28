package models

import models.Product.dateFormat
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.scalatest.FlatSpec
import play.api.libs.json.Json

class ProductSpec extends FlatSpec {
  val formatter = DateTimeFormat.forPattern(dateFormat).withOffsetParsed()

  "product json " should "transform into Product class" in {
   val json = Json.parse("""
      |{
      |  "product_name":"Sony_Cyber-shot_DSC-W310",
      |  "manufacturer":"Sony",
      |  "model":"DSC-W310",
      |  "family":"Cyber-shot",
      |  "announced-date":"2010-01-06T19:00:00.000-05:00"
      |}
    """.stripMargin)

    val product: Product = json.as[Product]
    assert(product.productName === "Sony_Cyber-shot_DSC-W310")
    assert(product.manufacturer === "Sony")
    assert(product.model === "DSC-W310")
    assert(product.family === "Cyber-shot")
    assert(product.announcedDate === formatter.parseDateTime("2010-01-06T19:00:00.000-05:00"))
  }

}
