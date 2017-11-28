package models

import play.api.libs.json.Reads
import play.api.libs.functional.syntax._
import play.api.libs.json._
case class Listing(title: String, manufacturer: String, currency: String, price: String)

object Listing {
  implicit val listingReads: Reads[Listing] = (
    (JsPath \ "title").read[String] and 
      (JsPath \ "manufacturer").read[String] and
      (JsPath \ "currency").read[String] and 
      (JsPath \ "price").read[String]
  )(Listing.apply _)
  
  implicit val listingWrites: Writes[Listing] = (
    (JsPath \ "title").write[String] and
      (JsPath \ "manufacturer").write[String] and
      (JsPath \ "currency").write[String] and
      (JsPath \ "price").write[String]
  )(unlift(Listing.unapply _))
  
}
