import org.scalatest.funsuite.AnyFunSuite

class UserRepoImplTest extends AnyFunSuite {

  val userRepo = new UserRepoImpl()
  val username = User(name = "Nitesh", age = 24)


  test("add user") {
    userRepo.addUser(username)
    assert(username == userRepo.getUser(username.uid))
  }
  test("get User") {
    userRepo.addUser(username)
    val id = username.uid
    assert(username == userRepo.getUser(id))
  }
  test("get All User") {
    val numberOfUser = 2
    assert(numberOfUser == userRepo.getAllUsers.size)
  }
  test("delete user") {
    val newUser = User(name = "Neeru", age = 30)
    userRepo.addUser(newUser)
    assert(newUser == userRepo.deleteUser(newUser.name))
  }
}