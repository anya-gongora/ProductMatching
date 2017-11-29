import scala.io.Source
import java.io.{BufferedWriter, FileNotFoundException, FileWriter, IOException}

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
      // Construct the name of the product we are looking for
      // If the product doesn't have a family key, only use manufacturer and model
      val name = if (product.family.isDefined) s"${product.manufacturer} ${product.family.get} ${product.model}" else s"${product.manufacturer} ${product.model}"
      val matchedListing = listings.filter(listing => listing.title.contains(name))
      // Create a Result
      Result(product.productName, matchedListing)
    })

    val writer = new BufferedWriter(new FileWriter(resultsFile))
    // Write the results to the results.txt file
    results.foreach(result => writer.write(Json.stringify(Json.toJson(result)) + "\n"))
    writer.close()
    println("Fin! The results.txt has been created.")
  }
}