package com.codingchapters.isplugin

trait CodeTemplate {

  def pom(chapterNumber : Int) : String = {

    val scalaVersion = "2.13.10"
    val scalaCompatibilityVersion = "2.13"

    s"""<project xmlns="http://maven.apache.org/POM/4.0.0"
       |         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       |         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       |    <modelVersion>4.0.0</modelVersion>
       |
       |    <groupId>com.example</groupId>
       |    <artifactId>chapter${chapterNumber}</artifactId>
       |    <version>1.0-SNAPSHOT</version>
       |
       |    <properties>
       |        <maven.compiler.source>1.8</maven.compiler.source>
       |        <maven.compiler.target>1.8</maven.compiler.target>
       |    </properties>
       |
       |    <dependencies>
       |        <!-- Scala library -->
       |        <dependency>
       |            <groupId>org.scala-lang</groupId>
       |            <artifactId>scala-library</artifactId>
       |            <version>${scalaVersion}</version>
       |        </dependency>
       |
       |        <!-- ScalaTest -->
       |        <dependency>
       |            <groupId>org.scalatest</groupId>
       |            <artifactId>scalatest_${scalaCompatibilityVersion}</artifactId>
       |            <version>3.2.10</version>
       |            <scope>test</scope>
       |        </dependency>
       |    </dependencies>
       |
       |    <build>
       |        <sourceDirectory>src/main/scala</sourceDirectory>
       |        <testSourceDirectory>src/test/scala</testSourceDirectory>
       |        <plugins>
       |            <plugin>
       |                <groupId>org.scala-tools</groupId>
       |                <artifactId>maven-scala-plugin</artifactId>
       |                <version>2.15.2</version>
       |                <executions>
       |                    <execution>
       |                        <goals>
       |                            <goal>compile</goal>
       |                            <goal>testCompile</goal>
       |                        </goals>
       |                    </execution>
       |                </executions>
       |            </plugin>
       |            <plugin>
       |                <groupId>org.apache.maven.plugins</groupId>
       |                <artifactId>maven-surefire-plugin</artifactId>
       |                <version>3.0.0-M5</version>
       |                <configuration>
       |                    <useFile>false</useFile>
       |                    <disableXmlReport>true</disableXmlReport>
       |                    <includes>
       |                        <include>**/*Test.*</include>
       |                        <include>**/*Spec.*</include>
       |                    </includes>
       |                </configuration>
       |            </plugin>
       |        </plugins>
       |    </build>
       |</project>
       |
       |""".stripMargin
  }

  def specCode() : String = {
    """package com.example.specs
      |
      |import org.scalatest.freespec.AnyFreeSpecLike
      |import org.scalatest.matchers.should
      |
      |class ConnectToServerAndTakesSomeTimeSimulationSpec extends AnyFreeSpecLike with should.Matchers{
      |
      |  "print test should pass" in {
      |    println("print test")
      |    succeed
      |  }
      |}
      |""".stripMargin
  }

  def readMeContent() : String = {
    s"""
       |Click on book tab with icon <>   ---->  """.stripMargin
  }
}
