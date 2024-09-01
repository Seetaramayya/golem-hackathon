package twitter.clone

import twitter.clone.UserState.maxAllowedNameLength

case class UserState(users: Seq[User]) {
  private val sameUser: BigInt => User => Boolean = id => user => user.id == id
  def addIfValid(name: String): Either[String, UserState] = {
    if (isValid(name)) {
      val state = findUserByName(name) match {
        case Some(user) => this
        case None =>
          val user = newUser(name)
          UserState(user +: users)
      }
      Right(state)
    } else {
      Left(s"Adding user $name failed, user exists or user validation failed")
    }
  }

  def findUser(userId: BigInt): Option[User] = users.find(sameUser(userId))

  def findUserByName(name: String): Option[User] = users.find(_.name == name)

  private def isValid(name: String): Boolean =
    name.nonEmpty && name.length < maxAllowedNameLength

  private def newUser(name: String): User =
    User(
      nextId,
      name = name,
      following = Set(),
      tweets = Seq()
    )

  private def nextId: BigInt =
    users.headOption.map(_.id).map(_ + 1).getOrElse(BigInt(1))
}

object UserState {
  val empty: UserState = UserState(Seq.empty)
  val maxAllowedNameLength = 200
}
