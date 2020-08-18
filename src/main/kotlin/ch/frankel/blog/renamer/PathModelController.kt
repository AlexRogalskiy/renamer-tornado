package ch.frankel.blog.renamer

import tornadofx.Controller

class PathModelController : Controller() {
    init {
        subscribe<DirectoryPathUpdatedEvent> {
            fire(PathModelUpdatedEvent(it.path))
        }
    }
}