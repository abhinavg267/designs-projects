name := "designs"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "com.google.inject" % "guice" % "4.0", // https://mvnrepository.com/artifact/com.google.inject/guice
  "com.h2database" % "h2" % "1.4.196",
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3"
)

// Fail compilation on warnings
//scalacOptions += "-Xfatal-warnings"
