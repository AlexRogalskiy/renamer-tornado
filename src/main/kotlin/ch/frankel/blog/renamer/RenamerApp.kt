package ch.frankel.blog.renamer

import java.io.File
import tornadofx.*

class RenamerApp: App(RenamerView::class) {

    init {
        find(PathModelController::class)
    }
}

fun main(args: Array<String>) {
    launch<RenamerApp>(args)
}

internal fun File?.children() = this?.listFiles()
    ?.filter { !it.isHidden && it.isFile }
    ?.toList()