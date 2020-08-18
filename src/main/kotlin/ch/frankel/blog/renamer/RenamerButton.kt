package ch.frankel.blog.renamer

import java.io.File
import java.nio.file.Files
import java.util.regex.PatternSyntaxException
import javafx.event.EventTarget
import javafx.scene.control.Button
import tornadofx.*

class RenamerButton : Button("Apply") {

    private val controller = find<RenamerButtonController>()

    init {
        action {
            controller.rename()
        }
    }
}

class RenamerButtonController : Controller() {

    private var root: File? = null
    private var regex = "".toRegex()
    private var replacement = ""

    init {
        subscribe<PathModelUpdatedEvent> {
            root = File(it.path)
        }
        subscribe<PatternUpdatedEvent> {
            try {
                regex = it.pattern.toRegex()
            } catch (e: PatternSyntaxException) {
                // NOTHING TO DO
            }
        }
        subscribe<ReplacementUpdatedEvent> {
            replacement = it.replacement
        }
    }

    fun rename() {
        if (root != null && regex.pattern.isNotEmpty()) {
            root.children()
                ?.forEach {
                    val source = it.toPath()
                    val target = source.resolveSibling(regex.replace(it.name, replacement))
                    Files.move(source, target)
                }
            fire(RenamedEvent())
        }
    }
}

internal fun EventTarget.renamerButton(op: Button.() -> Unit = {}) = RenamerButton().attachTo(this, op)