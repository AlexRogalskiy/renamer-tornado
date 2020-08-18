package ch.frankel.blog.renamer

import javafx.event.EventTarget
import javafx.scene.control.TextField
import tornadofx.*

internal abstract class FiringTextField<T : FXEvent>(promptText: String) : TextField() {

    private val controller = object : Controller() {
        fun send(event: T) = fire(event)
    }

    init {
        this.promptText = promptText
        textProperty().addListener { _, _, value ->
            controller.send(create(value))
        }
    }

    abstract fun create(value: String): T
}

internal fun EventTarget.replacementTextfield(op: TextField.() -> Unit = {}) = object : FiringTextField<ReplacementUpdatedEvent>("Replacement") {
    override fun create(value: String) = ReplacementUpdatedEvent(value)
}.attachTo(this, op)

internal fun EventTarget.patternTextfield(op: TextField.() -> Unit = {}) = object : FiringTextField<PatternUpdatedEvent>("Pattern") {
    override fun create(value: String) = PatternUpdatedEvent(value)
}.attachTo(this, op)

internal fun EventTarget.directoryTextfield(op: TextField.() -> Unit = {}) = object : FiringTextField<DirectoryPathUpdatedEvent>("Folder") {
    override fun create(value: String) = DirectoryPathUpdatedEvent(value)
}.apply {
    text = System.getProperty("user.home")
}.attachTo(this, op)