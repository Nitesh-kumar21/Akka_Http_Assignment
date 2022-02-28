

import spray.json.{DefaultJsonProtocol, JsArray, JsValue, JsonFormat, RootJsonFormat, enrichAny}

import java.util.UUID
import scala.collection.mutable.ListBuffer

case class User(name: String, age: Int, uid: String = UUID.randomUUID().toString)

object UserProtocol extends DefaultJsonProtocol {
  implicit def listBufferFormat[T: JsonFormat]: RootJsonFormat[ListBuffer[T]] = new RootJsonFormat[ListBuffer[T]] {
    override def write(obj: ListBuffer[T]): JsValue = JsArray(obj.map(_.toJson).toVector)

    override def read(json: JsValue): ListBuffer[T] = json match {
      case JsArray(elements) => elements.map(_.convertTo[T]).to(ListBuffer)
      case _ => throw new Exception("Cannot Read")
    }
  }
  implicit val userFormat: RootJsonFormat[User] = jsonFormat3(User)
}