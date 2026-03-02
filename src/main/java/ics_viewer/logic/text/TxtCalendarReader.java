package ics_viewer.logic.text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;

import ics_viewer.logic.text.models.TxtCalendarEvent;
import ics_viewer.logic.text.models.TxtEventDateLine;
import ics_viewer.logic.text.models.TxtEventRecurrence;


public class TxtCalendarReader extends BufferedReader {

	private static final Pattern DATE_LINE_PATTERN = Pattern.compile(
		"(\\d\\d\\/\\d\\d\\/\\d+(?:\\s+\\d\\d:\\d\\d:\\d\\d)?)(?:\\s+-\\s+(\\d\\d\\/\\d\\d\\/\\d+(?:\\s+\\d\\d:\\d\\d:\\d\\d)?))?(?:\\s+every\\s+(?:(\\d+)\\s+)?([\\w]+)\\s+(?:(.+)\\s+)?(?:until\\s+(\\d\\d\\/\\d\\d\\/\\d+|\\d+\\soccurrences)|(forever)))?.*"
	);
	public TxtCalendarReader(@NotNull FileReader fileReader) {
		super(fileReader);
	}
	
	public TxtCalendarEvent readEvent() throws IOException {
		TxtCalendarEvent event = null;
		String line;
		Matcher matcher = null;
		boolean matches = false;
		do {
			do {
				line = this.readLine();
				if (line != null) {
					matcher = DATE_LINE_PATTERN.matcher(line);
					matches = matcher.matches();
				}
			} while (line != null && !matches);
			if (matches) {
				String from = matcher.group(1);
				String to = matcher.group(2);
				String recurrenceValue = matcher.group(3);
				String recurrenceUnit = matcher.group(4);
				String recurrenceDay = matcher.group(5);
				String recurrenceUntil = matcher.group(6);
				String recurrenceForever = matcher.group(7);
				boolean forever = "forever".equals(recurrenceForever);
				String title = this.readLine();
				List<String> notes = new ArrayList<>();
				do {
					line = this.readLine();
					if (line != null && !line.isEmpty()) {
						notes.add(line);
					}
				} while (line != null && !line.isEmpty());
				TxtEventRecurrence recurrence;
				Integer recurrenceValueInt;
				if (recurrenceValue == null) {
					recurrenceValueInt = 1;
				} else {
					recurrenceValueInt = Tools.toInteger(recurrenceValue);
				}
				if (recurrenceUnit != null && (recurrenceUntil != null || forever)) {
					recurrence = new TxtEventRecurrence(recurrenceValueInt, recurrenceUnit, recurrenceDay, (forever ? null : recurrenceUntil));
				} else {
					recurrence = null;
				}
				TxtEventDateLine dateLine;
				if (from == null) {
					dateLine = null;
				} else {
					dateLine = new TxtEventDateLine(from, to, recurrence);
				}
				if (title != null && dateLine != null) {
					event = new TxtCalendarEvent(dateLine, title, String.join("\n", notes));
				}
			} else {
				event = null;
			}
		} while (event == null && line != null);
		return event;
	}
}
