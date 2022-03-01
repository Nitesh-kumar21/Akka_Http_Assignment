

import scala.collection.mutable.ListBuffer

trait UserRepo {
  def addUser(user: User): Unit

  def deleteUser(userName: String): User

  def getUser(userId: String): User

  def getAllUsers: ListBuffer[User]

}