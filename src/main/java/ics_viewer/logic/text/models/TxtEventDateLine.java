package ics_viewer.logic.text.models;

import java.util.Objects;

import javax.validation.constraints.NotNull;

public class TxtEventDateLine extends Printable {

	private static final String FROM_TO_SEPARATOR = "-";
	private final String from;
	private final String to;
	private final TxtEventRecurrence recurrence;
	
	public TxtEventDateLine(@NotNull String from, String to, TxtEventRecurrence recurrence) {
		Objects.requireNonNull(from);
		this.from = from;
		this.to = to;
		this.recurrence = recurrence;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public TxtEventRecurrence getRecurrence() {
		return recurrence;
	}

	@Override
	protected String toString(boolean toFile, String open, String close) {
		String toPart;
		if (this.to == null) {
			toPart = "";
		} else {
			toPart = " " + FROM_TO_SEPARATOR + " " + open + this.to + close;
		}
		String recurrencePart;
		if (this.recurrence == null) {
			recurrencePart = "";
		} else {
			recurrencePart = " " + this.recurrence.toString(toFile, open, close);
		}
		return open + this.from + close + toPart + recurrencePart;
	}
}
