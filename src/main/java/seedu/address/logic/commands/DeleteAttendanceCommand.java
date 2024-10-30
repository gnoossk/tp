package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;

/**
 * Deletes the attendance of a student for a specific date.
 */
public class DeleteAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "deleteat";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the attendance of a student for a specific date.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_NAME + " John Doe "
            + PREFIX_DATE + "2019-10-09 ";

    public static final String MESSAGE_SUCCESS = "Attendance deleted: %1$s on %2$s";
    private final Name name;
    private final LocalDate date;
    private Attendance previousAttendance;

    /**
     * Creates a DeleteAttendanceCommand to delete the attendance of the specified student.
     * @param name The name of the student.
     * @param date The date for which to delete attendance.
     */
    public DeleteAttendanceCommand(Name name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Student student = model.getStudentByName(name);
        if (student == null) {
            throw new CommandException("Student not found: " + name);
        }

        previousAttendance = student.getAttendanceRecord().stream()
                .filter(record -> record.getDate().equals(date))
                .findFirst()
                .map(record -> record.getAttendance())
                .orElse(null);

        if (previousAttendance == null) {
            throw new CommandException("No attendance record found for " + name + " on " + date);
        }

        student.deleteAttendance(date);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                name, DateTimeFormatter.ofPattern("dd MMM yyyy").format(date)));

    }

    @Override
    public boolean undo(Model model) {
        Student student = model.getStudentByName(name);
        if (student == null) {
            return false;
        }

        if (previousAttendance == null) {
            student.deleteAttendance(date);
        }
        student.markAttendance(date, previousAttendance.value);
        return true;
    }

}