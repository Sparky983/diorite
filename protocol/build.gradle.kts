plugins {
    id("diorite.library-conventions")
}

dependencies {
    api("io.projectreactor:reactor-core:3.4.23")
    api("net.kyori:adventure-api:4.11.0")
    api("net.kyori:adventure-nbt:4.11.0")
    api(project(":world"))

    implementation(project(":common"))
    implementation("net.kyori:adventure-text-serializer-gson:4.11.0")
}
