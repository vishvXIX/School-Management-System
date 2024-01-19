package com.school.SBA.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.school.SBA.Entity.Subject;
import com.school.SBA.enums.ProgramType;

import jakarta.persistence.ManyToMany;

public class AcademicProgramResponse {

	private int programId;
	private ProgramType programType;
	private String programName;
	private LocalDate beginsAt;
	private LocalDate endsAt;
	@ManyToMany
	private List<Subject> subjects;
	
//	private List<Subject> subjectNames; 

	
//	public List<Subject> getListSubjects() {
//		return subjectNames;
//	}
//
//	public void setListSubject(List<Subject> subjectNames) {
//		this.subjectNames = subjectNames	;
//	}

	public List<Subject> getSubjects() {
		return subjects;
	}



	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}



	public AcademicProgramResponse() {
		super();
	}

	

	public AcademicProgramResponse(int programId, ProgramType programType, String programName, LocalDate beginsAt,
			LocalDate endsAt) {
		super();
		this.programId = programId;
		this.programType = programType;
		this.programName = programName;
		this.beginsAt = beginsAt;
		this.endsAt = endsAt;
//		this.subjectNames = subjectNames;
	}

	public int getProgramId() {
		return programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public ProgramType getProgramType() {
		return programType;
	}

	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public LocalDate getBeginsAt() {
		return beginsAt;
	}

	public void setBeginsAt(LocalDate beginsAt) {
		this.beginsAt = beginsAt;
	}

	public LocalDate getEndsAt() {
		return endsAt;
	}

	public void setEndsAt(LocalDate endsAt) {
		this.endsAt = endsAt;
	}
	
	
	
}

