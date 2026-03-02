package ics_viewer.logic.text.models;

import java.util.Objects;

import javax.validation.constraints.NotNull;

public class TxtEventRecurrence extends Printable {

	private final Integer recurrenceValue;
	private final String recurrenceUnit;
	private final String recurrenceDay;
	
	/**
	 * When <code>null</code>, it is forever.
	 */
	private final String recurrenceUntil;

	public TxtEventRecurrence(Integer recurrenceValue, @NotNull String recurrenceUnit, String recurrenceDay,
			String recurrenceUntil) {
		Objects.requireNonNull(recurrenceUnit);
		this.recurrenceValue = recurrenceValue;
		this.recurrenceUnit = recurrenceUnit;
		this.recurrenceDay = recurrenceDay;
		this.recurrenceUntil = recurrenceUntil;
	}

	public Integer getRecurrenceValue() {
		return recurrenceValue;
	}

	public String getRecurrenceUnit() {
		return recurrenceUnit;
	}

	public String getRecurrenceDay() {
		return recurrenceDay;
	}

	public String getRecurrenceUntil() {
		return recurrenceUntil;
	}
	
	static String nullable(Object object, String open, String close) {
		String result;
		if (object == null) {
			result = "";
		} else {
			result = " " + open + object.toString() + close;
		}
		return result;
	}
	
	@Override
	protected String toString(boolean toFile, String open, String close) {
		String valuePart;
		if (toFile && this.recurrenceValue == 1) {
			valuePart = "";
		} else {
			valuePart = nullable(this.recurrenceValue, open, close);
		}
		String untilPart;
		if (this.recurrenceUntil == null) {
			untilPart = " " + open + "forever" + close;
		} else {
			untilPart = " until " + open + this.recurrenceUntil + close;
		}
		return "every" + valuePart +
		" " + open + this.recurrenceUnit + close + nullable(this.recurrenceDay, open, close) + untilPart;
	}
}
