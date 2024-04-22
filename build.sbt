name := "minimal-codogenic-ic-plugin"
organization := "com.codogenic"
version := "2023.3.27.1"
scalaVersion := "2.13.12"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "3.4.4",
  "org.typelevel" %% "mouse" % "1.2.3",
  "com.github.pathikrit" %% "better-files" % "3.9.2"
)

Compile / unmanagedSourceDirectories ++= Seq(
  baseDirectory.value / "01-java-reflection",
  baseDirectory.value / "02-intellij-commons",
  baseDirectory.value / "11-new-chapter-wizard",
  baseDirectory.value / "21-codingchapters-toolwindow",
  baseDirectory.value / "31-codingchapters-actions-exercise",
  baseDirectory.value / "31-codingchapters-actions-exercise-machinery",
)

Compile / unmanagedResourceDirectories ++= Seq(
  baseDirectory.value / "resources"
)

ThisBuild / intellijPluginName := s"minimal-cbook"
ThisBuild / intellijBuild := "233.14015.106"
ThisBuild / intellijPlatform := intellijPlatform.in(Global).??(IntelliJPlatform.IdeaCommunity).value
intellijPlugins += "org.intellij.scala:2023.3.27".toPlugin
ThisBuild / intellijDownloadSources := true
ThisBuild / bundleScalaLibrary := false

lazy val scalaIntellij = project.in(file("."))
  .enablePlugins(SbtIdeaPlugin)
