package ics_viewer.logic.text;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.validation.constraints.NotNull;

import ics_viewer.logic.text.models.TxtCalendarEvent;


public class TxtCalendarWriter extends PrintWriter {

	private boolean first = true;
	
	public TxtCalendarWriter(@NotNull FileWriter fileWriter) {
		super(fileWriter);
	}
	
	public void writeEvent(TxtCalendarEvent event) throws IOException {
		if (event != null) {
			if (!this.first) {
				this.println();
				this.println();
			}
			this.print(event.print());
			first = false;
		}
	}
}
