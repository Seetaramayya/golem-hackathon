package twitter.clone

case class User(
    id: BigInt,
    name: String,
    following: Set[String],
    tweets: Seq[Tweet]
) {
  def follow(otherUser: String): User = copy(following = following + otherUser)

  def unfollow(otherUser: String): User =
    copy(following = following - otherUser)

  def addTweet(tweet: String): User =
    copy(tweets = Tweet(System.currentTimeMillis(), tweet) +: tweets)

  override def toString: String = s"following ${following.mkString(",")}"
}

object User {
  // TODO: handle this ugliness
  val empty: User = User(BigInt("-1"), "", Set(), Seq())
}
