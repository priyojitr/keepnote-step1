package com.stackroute.keepnote.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.repository.NoteRepository;

/*Annotate the class with @Controller annotation. @Controller annotation is used to mark 
 * any POJO class as a controller so that Spring can recognize this class as a Controller
 * */

@Controller
public class NoteController {
	/*
	 * From the problem statement, we can understand that the application requires
	 * us to implement the following functionalities.
	 * 
	 * 1. display the list of existing notes from the collection. Each note should
	 * contain Note Id, title, content, status and created date. 2. Add a new note
	 * which should contain the note id, title, content and status. 3. Delete an
	 * existing note. 4. Update an existing note.
	 */

	/*
	 * Get the application context from resources/beans.xml file using
	 * ClassPathXmlApplicationContext() class. Retrieve the Note object from the
	 * context. Retrieve the NoteRepository object from the context.
	 */
	private transient ApplicationContext appContext = new ClassPathXmlApplicationContext("beans.xml");
	private Note note = (Note) appContext.getBean("note");
	private transient NoteRepository noteRepository = (NoteRepository) appContext.getBean("noteRepository");
	private static final String VIEW_NAME = "index";
	private static final String NOTE_ID = "noteId";
	private static final String NOTE_LIST = "noteList";

	/*
	 * Define a handler method to read the existing notes by calling the
	 * getAllNotes() method of the NoteRepository class and add it to the ModelMap
	 * which is an implementation of Map for use when building model data for use
	 * with views. it should map to the default URL i.e. "/"
	 */
	@GetMapping("/")
	public String getAllNotes(final ModelMap model) {
		final List<Note> noteList = noteRepository.getAllNotes();
		model.addAttribute(NOTE_LIST, noteList);
		return VIEW_NAME;
	}

	/*
	 * Define a handler method which will read the Note data from request parameters
	 * and save the note by calling the addNote() method of NoteRepository class.
	 * Please note that the createdAt field should always be auto populated with
	 * system time and should not be accepted from the user. Also, after saving the
	 * note, it should show the same along with existing notes. Hence, reading notes
	 * has to be done here again and the retrieved notes object should be sent back
	 * to the view using ModelMap. This handler method should map to the URL
	 * "/saveNote".
	 */
	@PostMapping("/saveNote")
	public String saveNote(final ModelMap model, final HttpServletRequest request) {
		if(request.getParameter(NOTE_ID).isEmpty()
				||request.getParameter("noteTitle").isEmpty()
				||request.getParameter("noteContent").isEmpty()
				||request.getParameter("noteStatus").isEmpty()) {
			model.addAttribute("error", "Required fields are missing");
		}else if(noteRepository.exists(Integer.parseInt(request.getParameter(NOTE_ID)))) {
			model.addAttribute("error", "Duplicate note id");
		}else {
			// creating new note object from application context and assigning values
			final Note addNote = (Note)appContext.getBean("note");
			addNote.setCreatedAt(LocalDateTime.now());
			addNote.setNoteId(Integer.parseInt(request.getParameter(NOTE_ID)));
			addNote.setNoteTitle(request.getParameter("noteTitle"));
			addNote.setNoteContent(request.getParameter("noteContent"));
			addNote.setNoteStatus(request.getParameter("noteStatus"));
			
			// saving the new note created
			noteRepository.addNote(addNote);
		}
		//update note list
		model.addAttribute(NOTE_LIST, noteRepository.getAllNotes());
		return VIEW_NAME;
	}

	/*
	 * Define a handler method to delete an existing note by calling the
	 * deleteNote() method of the NoteRepository class This handler method should
	 * map to the URL "/deleteNote"
	 */
	@GetMapping("/deleteNote")
	public String deleteNote(final ModelMap model, final @RequestParam int noteId) {
		//delete provided note id from list
		noteRepository.deleteNote(noteId);
		//update note list
		model.addAttribute(NOTE_LIST, noteRepository.getAllNotes());
		return "redirect:/";
	}

}