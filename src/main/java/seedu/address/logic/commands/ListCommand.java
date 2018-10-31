package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.ContactType;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactContainsKeywordsPredicate;

/**
 * Finds and lists all contacts in address book which contain all of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_WORD_GENERAL = "%1$s list";
    public static final String COMMAND_WORD_CLIENT = "client list";
    public static final String COMMAND_WORD_SERVICE_PROVIDER = "serviceprovider list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all %1$s which contain all of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: n/[KEYWORD] p/[KEYWORD] e/[KEYWORD] a/[KEYWORD] t/[KEYWORD] ...\n"
            + "Example: " + COMMAND_WORD + " n/alice bob charlie";

    private final ContactContainsKeywordsPredicate predicate;
    private final ContactType contactType;

    public ListCommand(ContactContainsKeywordsPredicate predicate, ContactType contactType) {
        this.predicate = predicate;
        this.contactType = contactType;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        if (predicate.equals(new ContactContainsKeywordsPredicate())) {
            model.updateFilteredContactList(contactType.getFilter());
            return new CommandResult(Messages.MESSAGE_LIST_ALL_PERSON);
        }

        model.updateFilteredContactList(contactType.getFilter().and(predicate));
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredContactList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && predicate.equals(((ListCommand) other).predicate)); // state check
    }
}
