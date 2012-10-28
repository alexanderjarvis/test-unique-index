package controllers

import play.api._
import play.api.mvc._
import play.api.Play.current
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.ReactiveMongoPlugin
import reactivemongo.api._
import reactivemongo.bson._
import reactivemongo.bson.handlers.DefaultBSONHandlers._
import scala.concurrent.Future

object Users extends Controller with MongoController {
  
  val db = ReactiveMongoPlugin.db
  val collection = db("users")
  
  def create = Action {
    Async {
      collection.insert(BSONDocument("email" -> BSONString("test@example.com"))).map { error => 
        if (error.ok)
          Ok
        else
          BadRequest
      }.recover{ case _ => InternalServerError}
    }
  }
  
  def findAndCreate = Action {
    Async {
      collection.find(BSONDocument("email" -> BSONString("test@example.com"))).headOption.flatMap { user =>
        user match {
          case Some(user) => Future(BadRequest)
          case None => collection.insert(BSONDocument("email" -> BSONString("test@example.com"))).map(_ => Ok)
        }
      }
    }
  }
  
}