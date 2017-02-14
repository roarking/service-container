import sbt.Keys._
import sbt._
import scoverage.ScoverageSbtPlugin

object Test {

  // Create a default Scala style task to run with tests
  lazy val testScalastyle = taskKey[Unit]("testScalastyle")

  lazy val testSettings = Seq(

    // Don't run the tests in parallel
    parallelExecution in sbt.Test := false,

    // Don't package test jars since it does not handle resources properly
    exportJars in sbt.Test := false,

    // Setup the system to run Scalastyle when running tests
    testScalastyle := org.scalastyle.sbt.ScalastylePlugin.scalastyle.in(sbt.Test).toTask("").value,
    (test in sbt.Test) := (test in sbt.Test) dependsOn testScalastyle,

    // Include the code coverage settings
    ScoverageSbtPlugin.ScoverageKeys.coverageExcludedPackages := "<empty>;edazdarevic.*",
    ScoverageSbtPlugin.ScoverageKeys.coverageMinimum := 70,
    ScoverageSbtPlugin.ScoverageKeys.coverageFailOnMinimum := true,

    ScoverageSbtPlugin.ScoverageKeys.coverageHighlighting := {
      true
    }
  )
}
