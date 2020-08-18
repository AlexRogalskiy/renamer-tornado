package ch.frankel.blog.renamer

import java.io.File
import javafx.event.EventTarget
import javafx.scene.control.Button
import tornadofx.*

internal class FolderPickerButton : Button("Browse") {

    private val controller = find<FolderPickerButtonController>()

    init {
        action {
            controller.showDirectoryChooser()
        }
    }
}

internal class FolderPickerButtonController : Controller() {

    private lateinit var folder: File

    init {
        subscribe<PathModelUpdatedEvent> {
            val sentPath = File(it.path)
            if (sentPath.isDirectory) {
                folder = File(it.path)
            }
        }
    }

    fun showDirectoryChooser() {
        chooseDirectory("Open", folder)?.let {
            fire(DirectoryChosenEvent(it.path))
            folder = it
        }
    }
}

internal fun EventTarget.folderPickerButton(op: Button.() -> Unit = {}) = FolderPickerButton().attachTo(this, op)