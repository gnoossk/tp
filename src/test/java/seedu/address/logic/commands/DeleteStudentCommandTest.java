package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialGroup;
import seedu.address.testutil.StudentBuilder;

public class DeleteStudentCommandTest {
    // Ensure NullPointerException is thrown when no student number is provided
    @Test
    public void constructor_nullStudentNumber_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteStudentCommand(null));
    }

    // Ensure CommandException is thrown with specific message when student with provided name is not found
    @Test
    public void execute_validStudentNumberButStudentNotFound_throwsCommandException() {
        ModelStubWithNoStudent modelStub = new ModelStubWithNoStudent();
        DeleteStudentCommand command = new DeleteStudentCommand(new Name("John Tan"));

        assertThrows(CommandException.class, "This student is not in your student list.", () ->
                command.execute(modelStub));
    }

    @Test
    public void execute_studentDelete_success() throws Exception {
        Student validStudent = new StudentBuilder().withName("John Ng").build();
        DeleteStudentCommandTest.ModelStubWithStudent modelStub =
                new DeleteStudentCommandTest.ModelStubWithStudent(validStudent);
        modelStub.addStudent(validStudent);

        DeleteStudentCommand command = new DeleteStudentCommand(new Name("John Ng"));

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS + validStudent.getName()),
                result.getFeedbackToUser());
    }



    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public Person getPersonByName(Name name) {
            return null;
        }

        @Override
        public Student getStudentByName(Name name) {
            return null;
        }

        @Override
        public boolean hasStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStudent(Student target) {
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStudentList(Predicate<Student> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudent(Student target, Student editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Student> getStudentsByTutorialGroup(TutorialGroup tutorialGroup) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Student> getAllStudentsByName(Name name) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single student.
     */
    private class ModelStubWithStudent extends DeleteStudentCommandTest.ModelStub {
        private final Student student;

        ModelStubWithStudent(Student student) {
            requireNonNull(student);
            this.student = student;
        }

        @Override
        public Student getStudentByName(Name name) {
            requireNonNull(name);
            if (this.student.getName().equals(name)) {
                return this.student;
            }
            return null; // No student found with this name
        }

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return this.student.isSamePerson(student);
        }

        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
        }



    }


    /**
     * A Model stub that always accept the student being added.
     */
    private class ModelStubAcceptingStudentAdded extends DeleteStudentCommandTest.ModelStub {
        final ArrayList<Person> studentsAdded = new ArrayList<>();

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            // Student student = (Student) person;
            return studentsAdded.stream().map(p -> (Student) p).anyMatch(student::isSameStudent);
            // Student student = (Student) person;
            return studentsAdded.stream().map(p -> (Student) p).anyMatch(student::isSameStudent);
        }


        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
            studentsAdded.add(student);
        }


        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
            studentsAdded.add(student);
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    private class ModelStubWithNoStudent extends DeleteStudentCommandTest.ModelStub {
    private class ModelStubWithNoStudent extends DeleteStudentCommandTest.ModelStub {
        @Override
        public Student getStudentByName(Name name) {
            return null;
        }

        @Override
        public boolean hasStudent(Student student) {
            return false;
        }
    }
}
