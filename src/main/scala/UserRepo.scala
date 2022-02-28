

import scala.collection.mutable.ListBuffer

trait UserRepo {
  def addUser(user: User): Unit

  def deleteUser(userName: String): Option[User]

  def getUser(userId: String): Option[User]

  def getAllUsers: ListBuffer[User]

}