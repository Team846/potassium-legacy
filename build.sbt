organization := "com.lynbrookrobotics"

name := "potassium"

version := "0.1-SNAPSHOT"

autoScalaLibrary := false

resolvers += "WPILib-Maven" at "http://team846.github.io/wpilib-maven"

libraryDependencies += "edu.wpi.first" % "wpilib" % "0.1.0.201601151923"
libraryDependencies += "edu.wpi.first" % "networktables" % "0.1.0.201601151923"

libraryDependencies += "com.javaslang" % "javaslang" % "2.0.0-RC2"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3"

publishMavenStyle := true
crossPaths := false
publishTo := Some(Resolver.file("gh-pages-repo", baseDirectory.value / "repo"))
