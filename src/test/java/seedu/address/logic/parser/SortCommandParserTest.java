package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPLETION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_OF_APPLICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERNSHIP_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_oneParameterPresent_success() {
        // whitespace only preamble
        assertParseSuccess(parser, "      " + PREFIX_COMPANY_NAME,
                new SortCommand("company"));

        // company parameter
        assertParseSuccess(parser, " " + PREFIX_COMPANY_NAME,
                new SortCommand("company"));

        // position parameter
        assertParseSuccess(parser, " " + PREFIX_INTERNSHIP_POSITION,
                new SortCommand("position"));

        // deadline parameter
        assertParseSuccess(parser, " " + PREFIX_DEADLINE_OF_APPLICATION,
                new SortCommand("deadline"));

        // priority parameter
        assertParseSuccess(parser, " " + PREFIX_PRIORITY,
                new SortCommand("priority"));

        // text after parameter
        assertParseSuccess(parser, " " + PREFIX_PRIORITY + PREAMBLE_NON_EMPTY,
                new SortCommand("priority"));
    }

    @Test
    public void parse_multipleParametersPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "" + PREFIX_PRIORITY + PREFIX_DEADLINE_OF_APPLICATION, expectedMessage);
    }

    @Test
    public void parse_noParameterPresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);

        // command word only
        assertParseFailure(parser, "", expectedMessage);

        // white space only
        assertParseFailure(parser, "      ", expectedMessage);

        // no parameter
        assertParseFailure(parser, PREAMBLE_NON_EMPTY, expectedMessage);

        // unsupported parameter
        assertParseFailure(parser, "" + PREFIX_COMPLETION, expectedMessage);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);

        assertParseFailure(parser, PREAMBLE_NON_EMPTY + "company", expectedMessage);
    }
}