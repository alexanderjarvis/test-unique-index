import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "test-unique-index"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
      "play.modules.reactivemongo" %% "play2-reactivemongo" % "0.1-SNAPSHOT"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here     
      resolvers += "sgodbillon" at "https://bitbucket.org/sgodbillon/repository/raw/master/snapshots/"
    )

}
