package com.example.dodam.controller.schedule;

import com.example.dodam.domain.schedule.Schedule;
import com.example.dodam.service.schedule.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ScheduleControllor {

    private final ScheduleService scheduleService;
    @Autowired
    public ScheduleControllor(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 등록
    // Schedule 객체 전달, scheduleId 반환
    @PostMapping("/schedule")
    public ResponseEntity<?> addSchedule(@RequestBody Schedule schedule){
        return new ResponseEntity<>(scheduleService.save(schedule), HttpStatus.CREATED);
    }
}
