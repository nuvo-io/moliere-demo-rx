name		:= "moliere-demo-rx"

version		:= "0.9.3-SNAPSHOT"

organization 	:= "io.nuvo"

homepage :=  Some(new java.net.URL("http://nuvo.io"))

scalaVersion 	:= "2.11.6"

resolvers += "nuvo.io maven repo" at "http://nuvo-io.github.com/mvn-repo/snapshots"

resolvers += "Vortex Snapshot Repo" at "https://dl.dropboxusercontent.com/u/19238968/vortex/mvn-repo"

libraryDependencies += "io.nuvo" % "moliere_2.11" % "0.9.2-SNAPSHOT"

libraryDependencies += "io.nuvo" % "moliere-rx_2.11" % "0.9.4-SNAPSHOT"

libraryDependencies += "com.prismtech.cafe" % "cafe" % "2.1.2-SNAPSHOT"

libraryDependencies += "org.omg.dds.types" % "builtin-typelib_2.11" % "4.2.0-SNAPSHOT"

libraryDependencies += "io.reactivex" % "rxscala_2.11" % "0.24.1"

autoCompilerPlugins := true

scalacOptions += "-deprecation"

scalacOptions += "-unchecked"

scalacOptions += "-optimise"

scalacOptions += "-feature"

scalacOptions += "-language:postfixOps"

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath + "/xlabs/nuvo/mvn-repo/snapshots")))
