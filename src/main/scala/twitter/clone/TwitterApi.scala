package twitter.clone

import twitter.clone.HttpClient.blockingFetchTweets

import scala.util.Try

@cloud.golem.WitExport
object TwitterApi extends Api { self =>
  private var userState = UserState.empty
  private var state = TwitterState.empty

  override def addUser(name: String): WitResult[String, String] = {
    println(s"Adding user $name")
    userState.addIfValid(name) match {
      case Right(s) =>
        userState = s
        userState.findUserByName(name) match {
          case Some(user) => WitResult.ok(user.id.toString())
          case None       => WitResult.err("Internal server error")
        }
      case Left(error) => WitResult.err(s"Bad request $error")
    }
  }

  override def addTweet(m: String): WitResult[String, String] = {
    Try {
      val tweet = if (m.length > 200) s"${m.take(200)}..." else m
      println(s"Adding tweet $tweet")
      state = state.addTweet(tweet)
      WitResult.ok(s"Tweet added: ${tweet}")
    }.toOption
      .getOrElse(WitResult.err(s"Bad request, invalid user id"))
  }

  override def follow(
      name: String
  ): TwitterApi.WitResult[String, String] = {
    Try {
      println(s"Following $name")
      state = state.follow(name)
      WitResult.ok(s"Following $name $state")
    }.getOrElse {
      WitResult.err(s"Error while try to follow: $name")
    }
  }

  override def unFollow(
      name: String
  ): TwitterApi.WitResult[String, String] = {
    Try {
      println(s"Un following $name")
      state = state.unFollow(name)
      WitResult.ok(s"Un following $name $state")
    }.getOrElse {
      WitResult.err(s"Error while try to unfollow: $name")
    }
  }

  override def getTweets(): TwitterApi.WitList[Tweet] = {
    Try {
      println("Getting Tweets")
      WitList.fromList(
        state.getTweets().map(t => Tweet(t.timestamp, t.message)).toList
      )
    }.getOrElse {
      WitList.fromList(List.empty)
    }
  }

  override def timeline(): TwitterApi.WitList[Tweet] = {
    val followingUserIds = state.user.following.toSeq

    val either = Try {
      WitList.fromList(blockingFetchTweets(followingUserIds).toList)
    }.toEither

    either match {
      case Left(error) =>
        val message = error.getMessage
        val stacktrace = error.getStackTrace.mkString("\n")

        WitList.fromList(
          List(Tweet(System.currentTimeMillis(), s"${message} ${stacktrace}"))
        )
      case Right(tweets) => tweets
    }
  }
}
