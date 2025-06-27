name := "codogenic-ic-plugin"
organization := "com.codogenic"
version := "2025.1.27.1"
scalaVersion := "2.13.16"


val resourceDirs = Seq("resources")
Compile / unmanagedResourceDirectories ++= resourceDirs.map(baseDirectory.value / _)

ThisBuild / intellijPluginName := s"cbook-prod"
ThisBuild / intellijBuild := "251.26927.28"
ThisBuild / intellijPlatform := IntelliJPlatform.IdeaCommunity
intellijPlugins += "org.intellij.scala:2025.1.27".toPlugin

ThisBuild / bundleScalaLibrary := false

// Disable the searchable options index task if not needed
buildIntellijOptionsIndex := {
  streams.value.log.warn("Skipping buildIntellijOptionsIndex (no settings UI present)")
}


lazy val scalaIntellij = project.in(file("."))
  //https://github.com/JetBrains/sbt-idea-plugin/issues/139#issuecomment-2867677170
  .settings(
    packageLibraryMappings ++= Seq(
      "org.scala-lang"  % "scala-.*" % ".*"        -> None,
      "org.scala-lang.modules" % "scala-.*" % ".*" -> None
    )
  )
  .enablePlugins(SbtIdeaPlugin)
