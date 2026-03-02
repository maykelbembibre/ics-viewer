package ics_viewer.logic.text.models;

public abstract class Printable {

	@Override
	public String toString() {
		return this.toString(false);
	}
	
	public String print() {
		return this.toString(true);
	}
	
	private String toString(boolean toFile) {
		String open, close;
		boolean parentheses = !toFile;
		if (parentheses) {
			open = "(";
			close = ")";
		} else {
			open = "";
			close = "";
		}
		return this.toString(toFile, open, close);
	}
	
	protected abstract String toString(boolean toFile, String open, String close);
}
