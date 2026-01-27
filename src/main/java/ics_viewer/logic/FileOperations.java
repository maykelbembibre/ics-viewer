package ics_viewer.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import ics_viewer.exceptions.GeneralException;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

public class FileOperations {

	public static Calendar getCalendar(File icsFile) throws GeneralException {
		FileInputStream fileInputStream = getFileInputStream(icsFile);
		Calendar calendar;
		CalendarBuilder builder = new CalendarBuilder();
		try {
			calendar = builder.build(fileInputStream);
		} catch (IOException e) {
			calendar = null;
		} catch (ParserException e) {
			calendar = null;
		}
		if (calendar == null) {
			throw new GeneralException("The file " + icsFile.getAbsolutePath() + " doesn't have ICS format or its format isn't valid.");
		}
		return calendar;
	}
	
	private static FileInputStream getFileInputStream(File file) throws GeneralException {
		FileInputStream fileInputStream;
		if (file.isFile()) {
			try {
				fileInputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				fileInputStream = null;
			}
		} else {
			fileInputStream = null;
		}
		if (fileInputStream == null) {
			throw new GeneralException(file.getAbsolutePath() + " doesn't point to a file.");
		}
		return fileInputStream;
	}
}
