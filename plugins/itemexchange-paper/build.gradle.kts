plugins {
	id("io.papermc.paperweight.userdev")
}

version = "2.0.1"

dependencies {
	paperweight {
		paperDevBundle("1.20.4-R0.1-SNAPSHOT")
	}

	compileOnly(project(":plugins:civmodcore-paper"))
	compileOnly(project(":plugins:namelayer-paper"))
	compileOnly(project(":plugins:citadel-paper"))
	compileOnly(project(":plugins:jukealert-paper"))

	compileOnly("org.projectlombok:lombok:1.18.24")
	annotationProcessor("org.projectlombok:lombok:1.18.24")
}
