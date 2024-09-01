package twitter.clone

case class TwitterState(user: User) {
  def addTweet(tweet: String): TwitterState = TwitterState(
    user.addTweet(tweet)
  )
  def getTweets(): Seq[Tweet] = user.tweets

  def follow(name: String): TwitterState = {
    TwitterState(user.follow(name))
  }

  def unFollow(name: String): TwitterState = {
    TwitterState(user.unfollow(name))
  }

  override def toString: String = user.toString
}

object TwitterState {
  val empty: TwitterState = TwitterState(User.empty)
}
