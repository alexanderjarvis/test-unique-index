package test

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import play.api.Play.current
import scala.concurrent.ExecutionContext
import play.modules.reactivemongo.ReactiveMongoPlugin
import scala.concurrent.Await
import reactivemongo.core.commands.RawCommand
import reactivemongo.bson._
import scala.concurrent.util.Duration
import reactivemongo.api.indexes.Index

class ApplicationSpec extends Specification {
  
  sequential
  
  implicit val ec: ExecutionContext = ExecutionContext.Implicits.global
  def db = ReactiveMongoPlugin.db
  def collection = db("users")
  
  step {
    running(FakeApplication()) {
      try {
        Await.result(db.command(RawCommand(BSONDocument("drop" -> BSONString("users")))), Duration(1, "seconds"))
      } catch {
        case e => //
      }
      Await.result(collection.indexesManager.ensure(Index(List("email" -> true), unique = true)), Duration(1, "seconds"))
    }
  }
  
  "Application" should {
    
    "create User" in {
      running(FakeApplication()) {
        val result = routeAndCall(FakeRequest(POST, "/users")).get       
        status(result) must equalTo(OK)
      }
    }
    
    "create same User again" in {
      running(FakeApplication()) {
        val result = routeAndCall(FakeRequest(POST, "/users")).get       
        status(result) must equalTo(BAD_REQUEST)
      }
    }
    
    
  }
}