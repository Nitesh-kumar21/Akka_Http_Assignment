
import UserProtocol.{listBufferFormat, userFormat}
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Directives.path
import spray.json.enrichAny

import scala.concurrent.{ExecutionContext, Future}
import scala.io.StdIn
import scala.language.postfixOps

object UserService extends App {
  implicit val userSystem: ActorSystem = ActorSystem("userSystem")
  implicit val executionContext: ExecutionContext = userSystem.dispatcher

  val userRepo = new UserRepoImpl()

  val route = {
    path("getAllUsers") {
      get {
        val users = userRepo.getAllUsers
        if (users.isEmpty)
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<p>No user Found</p>"))
        else
          complete(HttpEntity(ContentTypes.`application/json`, userRepo.getAllUsers.toJson.prettyPrint))
      }
    } 
  } ~ path("getUser" / Segment) { userId =>
    get {
      val user = userRepo.getUser(userId)
      val result = user match {
        case Some(value) => value.toJson.prettyPrint
        case None => "<p>No user by that ID</p>"
      }
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, result))
    }
  } ~ path("addUser") {
    post {
      parameter("name", "age".as[Int]) { (name, age) =>
        val newUser = User(name = name, age = age)
        userRepo.addUser(newUser)
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"Added User with name: $name"))
      }
    }
  } ~ path("deleteUser") {
    delete {
      parameter("name") { name =>
        val deletedUser = userRepo.deleteUser(name)
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"Deleted User with name: ${deletedUser.get.name}"))
      }
    }
  }
  val serverBinding: Future[Http.ServerBinding] = Http().newServerAt("localhost", 9000).bindFlow(route)
  println("Server started successfully")
  StdIn.readLine()
  serverBinding.flatMap(_.unbind()).onComplete(_ => userSystem.terminate())
//val a = Http().bindAndHandle(route, "localhost", 9090)
}