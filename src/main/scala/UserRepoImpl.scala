
import scala.collection.mutable.ListBuffer

class UserRepoImpl extends UserRepo {
  var userList: ListBuffer[User] = ListBuffer.empty

  override def addUser(user: User): Unit = userList.append(user)

  override def deleteUser(userName: String): Option[User] = {
    val deletedUser =  userList.find(_.name == userName)
    userList = userList.filter(_.name != userName)
    deletedUser
  }


  override def getUser(userId: String): Option[User] = userList.find(_.uid == userId)

  override def getAllUsers: ListBuffer[User] = userList
}