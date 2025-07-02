name := "minimal-ic-plugin"
organization := "com.codogenic"
version := "2025.1.27.4"
scalaVersion := "3.3.5"


val resourceDirs = Seq("resources")
Compile / unmanagedResourceDirectories ++= resourceDirs.map(baseDirectory.value / _)

ThisBuild / intellijPluginName := s"minimal-cbook-prod"
ThisBuild / intellijBuild := "251.26927.28"
ThisBuild / intellijPlatform := IntelliJPlatform.IdeaCommunity
intellijPlugins += "org.intellij.scala:2025.1.27".toPlugin

// Disable the searchable options index task if not needed
buildIntellijOptionsIndex := {
  streams.value.log.warn("Skipping buildIntellijOptionsIndex (no settings UI present)")
}

lazy val scalaIntellij = project.in(file("."))
  .settings(
    //ThisBuild / bundleScalaLibrary := false,
    //bundleScalaLibrary not working, so manually excluding scala libs
    //https://github.com/JetBrains/sbt-idea-plugin/issues/139#issuecomment-2867677170
    packageLibraryMappings ++= Seq(
      "org.scala-lang"  % "scala-.*" % ".*"        -> None,
      "org.scala-lang.modules" % "scala-.*" % ".*" -> None
    ),
    // Add Java source directories
    Compile / unmanagedSourceDirectories += baseDirectory.value / "src" / "main" / "java",
    // Add IntelliJ Platform dependencies for Java compilation
    libraryDependencies ++= Seq(
      "org.jetbrains" % "annotations" % "24.0.1" % "provided"
    )
  )
  .enablePlugins(SbtIdeaPlugin)
