name := "design_pattern"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "com.google.inject" % "guice" % "4.0", // https://mvnrepository.com/artifact/com.google.inject/guice
  "com.h2database" % "h2" % "1.4.196",
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "com.typesafe.akka" %% "akka-stream" % "2.6.14",
  "joda-time" % "joda-time" % "2.10.10"  // https://mvnrepository.com/artifact/joda-time/joda-time

)

// Fail compilation on warnings
//scalacOptions += "-Xfatal-warnings"
