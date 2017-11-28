package models

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._ // Combinator syntax

case class Product(productName: String, manufacturer: String, model: String, family: String, announcedDate: DateTime)

object Product {
  val dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ"

  implicit val jodaDateReads = Reads[DateTime](js =>
    js.validate[String].map[DateTime](dtString =>
      DateTime.parse(dtString, DateTimeFormat.forPattern(dateFormat).withOffsetParsed())
    )
  )

 implicit val jodaDateWrites: Writes[DateTime] = new Writes[DateTime] {
    def writes(d: DateTime): JsValue = JsString(d.toString())
  }


  implicit val productReads: Reads[Product] = (
    (JsPath \ "product_name").read[String] and
    (JsPath \ "manufacturer").read[String] and
    (JsPath \ "model").read[String] and
    (JsPath \ "family").read[String] and
    (JsPath \ "announced-date").read[DateTime]
  )(Product.apply _)

  implicit val productWrites: Writes[Product] = (
    (JsPath \ "product_name").write[String] and
      (JsPath \ "manufacturer").write[String] and
      (JsPath \ "model").write[String] and
      (JsPath \ "family").write[String] and
      (JsPath \ "announced-date").write[DateTime]
  )(unlift(Product.unapply))


}
