package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_OF_APPLICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERNSHIP_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPLICATIONS;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.Internship;
import seedu.address.model.Model;
import seedu.address.model.application.Application;
import seedu.address.model.application.Company;
import seedu.address.model.application.Deadline;
import seedu.address.model.application.Position;
import seedu.address.model.application.Priority;

/**
 * Sorts all the applications in InternSHIP by the specified parameter (an application detail).
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all applications in InternSHIP by the given application detail.\n"
            + "Sorting by company name or position sorts applications in alphabetical order.\n"
            + "Sorting by deadline sorts applications from closer deadlines to later deadlines.\n"
            + "Sorting by priority sorts applications from higher to lower priority.\n"
            + "Parameters:\n"
            + PREFIX_COMPANY_NAME + " "
            + PREFIX_INTERNSHIP_POSITION + " "
            + PREFIX_DEADLINE_OF_APPLICATION + " "
            + PREFIX_PRIORITY + "\n"
            + "Example:\n"
            + "Sort by company name: " + COMMAND_WORD + " " + PREFIX_COMPANY_NAME + "\n"
            + "Sort by position: " + COMMAND_WORD + " " + PREFIX_INTERNSHIP_POSITION + "\n"
            + "Sort by deadline: " + COMMAND_WORD + " " + PREFIX_DEADLINE_OF_APPLICATION + "\n"
            + "Sort by priority: " + COMMAND_WORD + " " + PREFIX_PRIORITY + "\n";

    public static final String MESSAGE_SUCCESS = "Sorted applications by %s";
    public static final String MESSAGE_PARAMETER_NOT_SPECIFIED = "At least one parameter (application detail) "
            + "to sort by must be provided.\n";
    public static final String MESSAGE_EMPTY_LIST = "There is no application in your Internship list to sort";

    private final String parameter;

    /**
     * Creates a SortCommand to sort the applications in InternSHIP by the specified parameter.
     *
     * @param parameter Application detail used to sort the list of applications.
     * (e.g. company name, application deadline)
     */
    public SortCommand(String parameter) {
        requireNonNull(parameter);

        this.parameter = parameter;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Application> lastShownList = model.getFilteredApplicationList();
        final List<Application> immutableLastShownList = new ArrayList<>(lastShownList);
        final Predicate<Application> containedInLastShownListPredicate = immutableLastShownList::contains;

        model.updateFilteredApplicationList(PREDICATE_SHOW_ALL_APPLICATIONS);
        List<Application> allApplications = model.getFilteredApplicationList();

        Comparator<Application> comparator = this.getComparator();

        List<Application> sortedApplicationList = new ArrayList<>(allApplications);
        sortedApplicationList.sort(comparator);

        Internship newSortedInternship = new Internship();
        newSortedInternship.setApplications(sortedApplicationList);
        model.setInternship(newSortedInternship);

        model.updateFilteredApplicationList(containedInLastShownListPredicate);

        int listSize = model.getFilteredApplicationList().size();
        return new CommandResult(listSize == 0
                ? MESSAGE_EMPTY_LIST
                : String.format(MESSAGE_SUCCESS, this.parameter));
    }

    /**
     * Returns the appropriate Comparator depending on the parameter to sort by.
     *
     * @return Comparator object that allows applications to be compared to each other based on the sorting parameter.
     */
    public Comparator<Application> getComparator() {
        switch (this.parameter) {
        case "company":
            return Company.getComparator();
        case "position":
            return Position.getComparator();
        case "deadline":
            return Deadline.getComparator();
        case "priority":
            return Priority.getComparator();
        default:
            // Should not reach this line.
        }
        return null; // Should not reach this line.
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && parameter.equals(((SortCommand) other).parameter)); // state check
    }
}