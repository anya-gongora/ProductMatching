package models

import play.api.libs.json.{JsPath, Reads}
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Result(productName: String, listings: Seq[Listing])

object Result {
  implicit val resultReads: Reads[Result] = (
    (JsPath \ "product_name").read[String] and
      (JsPath \ "listings").read[Seq[Listing]]
    ) (Result.apply _)

  implicit val resultWrites: Writes[Result] = (
    (JsPath \ "product_name").write[String] and
      (JsPath \ "listings").write[Seq[Listing]]
    ) (unlift(Result.unapply _))
}
