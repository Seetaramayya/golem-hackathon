package twitter.clone

import org.scalajs.dom

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.util.{Failure, Success}

object HttpClient {

  // TODO: OUCH... BLOCKING CALL
  def blockingFetchTweets(userIds: Seq[String]): Seq[Tweet] = {
    userIds.flatMap(fetchOtherUserTweets)
  }

  def fetchOtherUserTweets(userId: String): Seq[Tweet] = {
    val baseUrl = System.getenv("BASE_URL")
    val url = s"http://localhost:9006/v1/$userId/tweets"
    println(s"Trying to fetch tweets of $userId with $url")

    fetchTweets(url)
  }

  private def delay(duration: Int): Unit = {
    val end = js.Date.now() + duration
    while (js.Date.now() < end) {
      // STILL NOT WORKING
    }
  }

  @js.native
  @JSImport("axios", JSImport.Namespace)
  object Axios extends js.Object {
    def get(url: String): js.Promise[js.Dynamic] = js.native
  }

  def fetchData(url: String): Future[js.Dynamic] = {
    Axios.get(url).toFuture
  }

  private def fetchTweets(url: String): Seq[Tweet] = {
    var result: Option[Seq[Tweet]] = None
    fetchJson(url).onComplete {
      case Success(tweets) => result = Some(tweets)
      case Failure(exception) =>
        exception.printStackTrace()
        result = Some(Seq.empty[Tweet])
    }

    // :D most ugliest code I have ever return in my life
    while (result.isEmpty) {
      delay(1000)
    }

    result.getOrElse(Seq.empty)
  }

  private def fetchJson(url: String): Future[Seq[Tweet]] = {
    dom
      .fetch(url)
      .toFuture
      .flatMap(_.json().toFuture)
      .map { data =>
        val json = data.asInstanceOf[js.Array[js.Dynamic]]
        json.map { item =>
          Tweet(
            timestamp = item.timestamp.asInstanceOf[String].toLong,
            message = item.message.asInstanceOf[String]
          )
        }.toSeq
      }
  }

}
