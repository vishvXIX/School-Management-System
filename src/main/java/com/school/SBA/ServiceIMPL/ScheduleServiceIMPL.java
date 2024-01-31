package com.school.SBA.ServiceIMPL;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.SBA.Entity.Schedule;
import com.school.SBA.Exception.IllagalRequestException;
import com.school.SBA.Repository.ScheduleRepository;
import com.school.SBA.Repository.SchoolRepository;
import com.school.SBA.RequestDTO.ScheduleRequest;
import com.school.SBA.ResponseDTO.ScheduleResponse;
import com.school.SBA.ResponseDTO.SchoolResponse;
import com.school.SBA.Service.ScheduleService;
import com.school.SBA.Utility.ResponseStructure;

import jakarta.validation.Valid;

@Service
public class ScheduleServiceIMPL implements ScheduleService {

	@Autowired
	private ScheduleRepository repository;

	@Autowired
	private SchoolRepository schoolRepository;

	@Autowired
	private ResponseStructure<ScheduleResponse> structure;

	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> createSchedule(int schoolId,ScheduleRequest schedulerequest) {
		return schoolRepository.findById(schoolId).map(s->{
			if(s.getSchedule() == null) {
				Schedule schedule = mapToSchedule(schedulerequest);
				schedule= repository.save(schedule);
				s.setSchedule(schedule);
				schoolRepository.save(s);
				structure.setStatus(HttpStatus.CREATED.value());
				structure.setMessage("schedule object created Sucessfully");
				structure.setData(mapToScheduleResponse(schedule,false));
				return new ResponseEntity<ResponseStructure<ScheduleResponse>>(structure,HttpStatus.CREATED);
			}else 
				throw new IllagalRequestException("Schedule object is alredy present");
		}).orElseThrow(()->new IllagalRequestException("School has only one school id that is of ADMINS"));

	}

	private Schedule mapToSchedule(@Valid ScheduleRequest scheduleRequest)
	{
		return Schedule.builder()
				.openAt(scheduleRequest.getOpenAt())
				.closeAt(scheduleRequest.getCloseAt())
				.classHourPerDay(scheduleRequest.getClassHourPerDay())
				.classHourLengthInMinutes(Duration.ofMinutes(scheduleRequest.getClassHourLengthInMinutes()))
				.breakTime(scheduleRequest.getBreakTime())
				.breakLengthInMinutes(Duration.ofMinutes(scheduleRequest.getBreakLengthInMinutes()))
				.lunchTime(scheduleRequest.getLunchTime())
				.lunchLengthInMinutes(Duration.ofMinutes(scheduleRequest.getLunchLengthInMinutes()))
				.build();
	}

	private ScheduleResponse mapToScheduleResponse(Schedule schedule, boolean isDeleted)
	{
		return ScheduleResponse.builder()
				.ScheduleId(schedule.getScheduleId())
				.openAt(schedule.getOpenAt())
				.closeAt(schedule.getCloseAt())
				.classHourPerDay(schedule.getClassHourPerDay())
				.classHourLengthInMinutes((int)schedule.getClassHourLengthInMinutes().toMinutes())
				.breakTime(schedule.getBreakTime())
				.breakLengthInMinutes((int)schedule.getBreakLengthInMinutes().toMinutes())
				.lunchTime(schedule.getLunchTime())
				.lunchLengthInMinutes((int) schedule.getLunchLengthInMinutes().toMinutes())
				.isDeleted(schedule.isDeleted())
				.build();
	}

	@Override
	public List<SchoolResponse> findSchedule(int schoolId) {
		// TODO Auto-generated method stub
		return null;
	}
//
//	@Override
//	public ResponseEntity<ResponseStructure<ScheduleResponse>> deleteById(int scheduleId) {
//
//		return repository.findById(scheduleId)
//				.map(schedules -> {
//					schedules.setDeleted(true);
//					repository.deleteById(scheduleId);
//					repository.save(schedules);
//					structure.setStatus(HttpStatus.OK.value());
//					structure.setMessage("schedule Program deleted successfully");
//					structure.setData(mapToScheduleResponse(schedules,true));
//
//					return new ResponseEntity<>(structure, HttpStatus.OK);
//				})
//				.orElseThrow(() -> new IllagalRequestException("schedule Program not found by id"));
//	}
	
	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> deleteById(int scheduleId) throws Exception {

		Optional<Schedule> optional = repository.findById(scheduleId);
		if(optional.isPresent()) {
			Schedule schedule = optional.get();
			repository.delete(schedule);
//			ResponseStructure<Schedule> responseStructure = new ResponseStructure<>();
			structure.setStatus(HttpStatus.OK.value());
			structure.setMessage("Deleted Successfully...");
			structure.setData(mapToScheduleResponse(schedule, false));
			return new  ResponseEntity<ResponseStructure<ScheduleResponse>>(structure,HttpStatus.OK);
		}
		else {
			throw new Exception("Schedule Not Found");
		}
	}



}
