rootProject.name = "diorite"

dependencyResolutionManagement {
    includeBuild("build-logic")
}

sequenceOf(
    "client",
    "common",
    "protocol",
    "world"
).forEach { include(it) }
