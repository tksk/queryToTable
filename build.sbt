import AssemblyKeys._

assemblySettings

name := "queryToTable"

version := "0.1.0"

organization := "org.tksk"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
    "com.github.scopt" %% "scopt" % "3.2.0"
  , "org.jsoup" % "jsoup" % "1.7.3"
)

//jarName in assembly := "qutie.jar"
