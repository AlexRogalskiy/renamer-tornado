package ch.frankel.blog.renamer

import javafx.geometry.HPos
import javafx.scene.layout.Priority
import tornadofx.*

class RenamerView : View("File Renamer") {

    private val space = 4.0

    override val root = gridpane {
        padding = insets(space)
        directoryTextfield {
            subscribe<DirectoryChosenEvent> {
                textProperty().set(it.path)
            }
            gridpaneConstraints {
                columnIndex = 0
                fillWidth = true
                hGrow = Priority.ALWAYS
                marginBottom = space
            }
        }
        folderPickerButton {
            gridpaneConstraints {
                columnIndex = 1
                hAlignment = HPos.RIGHT
                marginBottom = space
            }
        }
        patternTextfield {
            gridpaneConstraints {
                rowIndex = 1
                fillWidth = true
                hGrow = Priority.ALWAYS
            }
        }
        replacementTextfield {
            gridpaneConstraints {
                columnRowIndex(1, 1)
                fillWidth = true
                hGrow = Priority.ALWAYS
                marginLeft = space
            }
        }
        scrollpane {
            filesTableview()
            isFitToHeight = true
            isFitToWidth = true
            gridpaneConstraints {
                rowIndex = 2
                fillHeightWidth = true
                hGrow = Priority.ALWAYS
                vGrow = Priority.ALWAYS
                columnSpan = 2
                marginTopBottom(space)
            }
        }
        renamerButton {
            gridpaneConstraints {
                columnRowIndex(1, 3)
                hAlignment = HPos.RIGHT
            }
        }
    }

    init {
        fire(PathModelUpdatedEvent(System.getProperty("user.home")))
    }
}