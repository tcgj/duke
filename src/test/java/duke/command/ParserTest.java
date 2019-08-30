package duke.command;

import org.junit.jupiter.api.Test;

import duke.exception.DukeParserException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    @Test
    public void parseDate_validDateFormat_success() throws Exception {
        assertEquals("Fri Dec 20 12:34:00 SGT 2019", Parser.parseDate("20122019 1234").toString());
    }

    @Test
    public void parseDate_invalidDateFormat_exceptionThrown() {
        assertThrows(DukeParserException.class, () -> Parser.parseDate("Dec 20 2019 12pm"));
    }

    @Test
    public void parseInt_validNumber_success() throws Exception {
        assertEquals(14, Parser.parseInt("14"));
    }

    @Test
    public void parseInt_invalidNumber_exceptionThrown() {
        assertThrows(DukeParserException.class, () -> Parser.parseInt("one"));
    }
}
