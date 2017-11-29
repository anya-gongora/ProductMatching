import scala.io.Source
import java.io._
import java.nio.charset.StandardCharsets

import models.{Listing, Product, Result}
import play.api.libs.json.{JsValue, Json}


object Matching {
  val listingsFile = "src/main/listings.txt"
  val productsFile = "src/main/products.txt"
  val resultsFile = "results.txt"

  //Get the data from the file and transform it into a Sequence of JsValues
  def getFileInformation(fileName: String): Seq[JsValue] = {
    try {
      val seq: List[String] = Source.fromFile(fileName, "UTF-8").getLines().toList

      //transform all the string lines into jsValues
      seq.map(line => {
        Json.parse(line)
      })
    } catch {
      case e: FileNotFoundException => throw new Throwable(s"Error: File $fileName not found")
      case ioe: IOException => {
        println(fileName)
        throw new Throwable(ioe)
      }
    }
  }

  def main(args: Array[String]) {
    val products = getFileInformation(productsFile).map(_.as[Product])
    val listings = getFileInformation(listingsFile).map(_.as[Listing])

    val results: Seq[Result] = products.map(product => {

      // List of keywords
      val productNameList = List(product.manufacturer.toLowerCase, product.model.toLowerCase, product.family.getOrElse("").toLowerCase)

      // If the product doesn't have a family key, only use manufacturer and model
      val matchedListing = if(productNameList(2).nonEmpty){
        listings.filter(listing =>
          listing.title.toLowerCase.contains(productNameList.head) &&
            listing.title.toLowerCase.contains(productNameList(1)) &&
            listing.title.toLowerCase.contains(productNameList(2)))
      } else {
        listings.filter(listing =>
          listing.title.toLowerCase.contains(productNameList.head) &&
            listing.title.toLowerCase.contains(productNameList(1)))
      }
      
      // Create a Result
      Result(product.productName, matchedListing)
    })
    val matchedProducts = results.count(product => product.listings.nonEmpty)
    val writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(resultsFile), StandardCharsets.UTF_8))
    // Write the results to the results.txt file
    results.foreach(result => writer.write(Json.stringify(Json.toJson(result)) + "\n"))
    writer.close()
    println("Fin! The results.txt has been created.")
    println(s"Successfully matched $matchedProducts products.")
  }
}