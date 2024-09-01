ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .settings(
    name := "golem-test"
  )
  .enablePlugins(WasmComponentPlugin)
  .settings(libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.1.0")
  .settings(libraryDependencies += "com.lihaoyi" %%% "upickle" % "3.1.0")
  .settings(wasmComponentPackageName := "twitter.clone")
