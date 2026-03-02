package ics_viewer.logic.text.models;

import java.util.Objects;

import javax.validation.constraints.NotNull;

public class TxtCalendarEvent extends Printable {
	
	private final TxtEventDateLine dateLine;
	private final String title;
	private final String notes;
	
	public TxtCalendarEvent(@NotNull TxtEventDateLine dateLine, @NotNull String title, String notes) {
		Objects.requireNonNull(dateLine);
		Objects.requireNonNull(title);
		this.dateLine = dateLine;
		this.title = title;
		this.notes = notes;
	}
	
	public TxtEventDateLine getDateLine() {
		return dateLine;
	}
	public String getTitle() {
		return title;
	}
	public String getNotes() {
		return notes;
	}
	
	@Override
	protected String toString(boolean toFile, String open, String close) {
		String dateLineSeparator, notesPart;
		if (toFile) {
			dateLineSeparator = "\n";
			if (this.notes == null) {
				notesPart = "";
			} else {
				notesPart = "\n" + this.notes;
			}
		} else {
			dateLineSeparator = " ";
			if (this.notes == null) {
				notesPart = "";
			} else {
				notesPart = " (with notes)";
			}
		}
		return this.dateLine.toString(toFile, open, close) + dateLineSeparator + this.title + notesPart;
	}
}
