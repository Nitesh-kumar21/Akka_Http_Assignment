
import scala.collection.mutable.ListBuffer

class UserRepoImpl extends UserRepo {
  var userList: ListBuffer[User] = ListBuffer.empty

  override def addUser(user: User): Unit = userList.append(user)

  override def deleteUser(userName: String): User = {
    val deletedUser =  userList.filter(_.name == userName).head
    userList = userList.filter(_.name != userName)
    deletedUser
  }

  override def getUser(userId: String): User = userList.find(_.uid == userId).head

  override def getAllUsers: ListBuffer[User] = userList
}