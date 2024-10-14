package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.student.StudentNumber.isValidStudentNumber;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;

/**
 * Deletes a student from the system based on the student number provided.
 */
public class DeleteStudentCommand extends Command {
    public static final String COMMAND_WORD = "deletestu";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student identified by the student number used in the displayed student list.\n"
            + "Parameters: STUDENT_NUMBER (must be a valid student number)\n"
            + "Example: " + COMMAND_WORD + " A1234567B";

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted Student: %1$s";

    public static final String MESSAGE_INVALID_STUDENT_NUMBER = "The student number you provided is not valid.";

    public static final String MESSAGE_NONEXISTENT_STUDENT = "This student is not in your student list.";

    private final StudentNumber targetStudentNumber;

    /**
     * Constructs a DeleteStudentCommand to delete the specified student by student number.
     *
     * @param targetStudentNumber The student number of the student to be deleted.
     */
    public DeleteStudentCommand(StudentNumber targetStudentNumber) {
        requireNonNull(targetStudentNumber);
        this.targetStudentNumber = targetStudentNumber;
    }

    /**
     * Executes the command to delete a student from the system.
     *
     * @param model The model in which the student will be deleted.
     * @return A CommandResult indicating the success of the command.
     * @throws CommandException If the student number is invalid or the student does not exist in the list.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // Check if the student number is valid
        if (!isValidStudentNumber(targetStudentNumber.toString())) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_NUMBER);
        }

        // Given a valid student number, check if student exists
        Student studentToDelete = model.getStudentByNumber(targetStudentNumber);
        if (studentToDelete == null) {
            throw new CommandException(MESSAGE_NONEXISTENT_STUDENT);
        }

        model.deleteStudent(studentToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS, studentToDelete));
    }

    /**
     * Checks whether this DeleteStudentCommand is equal to another object.
     *
     * @param other The object to compare to.
     * @return True if the object is equal to this command, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteStudentCommand)) {
            return false;
        }

        DeleteStudentCommand otherDeleteStudentCommand = (DeleteStudentCommand) other;
        return targetStudentNumber.equals(otherDeleteStudentCommand.targetStudentNumber);
    }
}
