package ch.frankel.blog.renamer

import java.io.File
import java.util.regex.PatternSyntaxException
import javafx.event.EventTarget
import javafx.scene.control.TableView
import tornadofx.*

class FileTableView : TableView<FileWrapper>() {

    private val controller = find<FileModelController>()

    init {
        columnResizePolicy = CONSTRAINED_RESIZE_POLICY
        readonlyColumn("Name", FileWrapper::name)
        readonlyColumn("Candidate name", FileWrapper::preview).cellDecorator {
            style = "-fx-background-color: ${if (rowItem.name != rowItem.preview) "yellow" else "transparent"};"
        }
        columns.forEach { it.maxWidth = Integer.MAX_VALUE * 50.0 }
        items = controller.files
    }
}

internal fun EventTarget.filesTableview(op: TableView<FileWrapper>.() -> Unit = {}) = FileTableView().attachTo(this, op)

data class FileWrapper(private val file: File,
                       val regex: Regex,
                       val replacement: String) {
    val name: String = file.name
    val preview: String
        get() = regex.replace(file.name, replacement)
}

class FileModelController : Controller() {

    private var root: File? = null
    private var regex = "".toRegex()
    private var replacement = ""
    internal val files = observableListOf<FileWrapper>()

    init {
        subscribe<PathModelUpdatedEvent> {
            root = File(it.path)
            refresh()
        }
        subscribe<PatternUpdatedEvent> {
            try {
                regex = it.pattern.toRegex()
                refresh()
            } catch (e: PatternSyntaxException) {
                // NOTHING TO DO
            }
        }
        subscribe<ReplacementUpdatedEvent> {
            replacement = it.replacement
            refresh()
        }
        subscribe<RenamedEvent> {
            refresh()
        }
    }

    private fun refresh() {
        files.setAll(
            root.children()
                ?.sortedBy { it.name }
                ?.map { FileWrapper(it, regex, replacement) }
                ?: emptyList()
        )
    }
}