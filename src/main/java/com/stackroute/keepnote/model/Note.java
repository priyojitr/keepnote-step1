package com.stackroute.keepnote.model;

import java.time.LocalDateTime;

/*
 * The class "Note" will be acting as the data model for the Note data in the ArrayList.
 */
public class Note {

	/*
	 * This class should have five fields (noteId, noteTitle, noteContent,
	 * noteStatus and createdAt). This class should also contain the getters and
	 * setters for the fields. The value of createdAt should not be accepted from
	 * the user but should be always initialized with the system date
	 */

	private int noteId;
	private String noteTitle;
	private String noteContent;
	private String noteStatus;
	private LocalDateTime createdAt;

	public Note() {
		// default constructor generated - kept blank
	}

	/* All the getters/setters definition should be implemented here */

	public int getNoteId() {
		return this.noteId;

	}

	public void setNoteId(final int noteId) {
		this.noteId = noteId;
	}

	public String getNoteTitle() {
		return this.noteTitle;
	}

	public void setNoteTitle(final String noteTitle) {
		this.noteTitle = noteTitle;
	}

	public String getNoteContent() {
		return this.noteContent;
	}

	public void setNoteContent(final String noteContent) {
		this.noteContent = noteContent;
	}

	public String getNoteStatus() {
		return this.noteStatus;
	}

	public void setNoteStatus(final String noteStatus) {
		this.noteStatus = noteStatus;
	}

	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(final LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	/* Override the toString() method */

	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", noteTitle=" + noteTitle + ", noteContent=" + noteContent + ", noteStatus="
				+ noteStatus + ", createdAt=" + createdAt + "]";
	}

}