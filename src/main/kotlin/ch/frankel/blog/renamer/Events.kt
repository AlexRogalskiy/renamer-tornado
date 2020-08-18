package ch.frankel.blog.renamer

import tornadofx.EventBus.RunOn.BackgroundThread
import tornadofx.FXEvent

data class DirectoryChosenEvent(val path: String): FXEvent(BackgroundThread)
data class DirectoryPathUpdatedEvent(val path: String): FXEvent(BackgroundThread)
data class PathModelUpdatedEvent(val path: String): FXEvent(BackgroundThread)
data class PatternUpdatedEvent(val pattern: String): FXEvent(BackgroundThread)
data class ReplacementUpdatedEvent(val replacement: String): FXEvent(BackgroundThread)
class RenamedEvent: FXEvent(BackgroundThread)