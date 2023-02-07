package com.flukebsk.quartzschedulerdatabase.controller;

import com.flukebsk.quartzschedulerdatabase.model.JobDescriptor;
import com.flukebsk.quartzschedulerdatabase.model.JobUpdate;
import com.flukebsk.quartzschedulerdatabase.service.QuartzService;
import lombok.RequiredArgsConstructor;
import org.quartz.JobDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quartz")
@RequiredArgsConstructor
public class QuartzController {
    private final QuartzService quartzService;

    @GetMapping(path = "/groups/jobs")
    public ResponseEntity<JobDescriptor> findAllJobs() {
        List<JobDescriptor> jobs = quartzService.findAllJobs();
        if (jobs.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(jobs, HttpStatus.OK);
        }
    }

    @PostMapping(path = "/groups/{group}/jobs")
    public ResponseEntity<JobDescriptor> createJob(@PathVariable String group, @RequestBody JobDescriptor descriptor) throws ClassNotFoundException {
        return new ResponseEntity(quartzService.createJob(group, descriptor), HttpStatus.CREATED);
    }

    @GetMapping(path = "/groups/{group}/jobs/{name}")
    public ResponseEntity<JobDescriptor> findJob(@PathVariable String group, @PathVariable String name) {
        return quartzService.findJob(group, name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/groups/{group}/jobs/{name}")
    public ResponseEntity<JobDetail> updateJob(@PathVariable String group, @PathVariable String name, @RequestBody JobUpdate jobUpdate) throws ClassNotFoundException {

        return quartzService.updateJob(group, name, jobUpdate).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/groups/{group}/jobs/{name}")
    public ResponseEntity<Void> deleteJob(@PathVariable String group, @PathVariable String name) {
        quartzService.deleteJob(group, name);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/groups/{group}/jobs/{name}/pause")
    public ResponseEntity<Void> pauseJob(@PathVariable String group, @PathVariable String name) {
        quartzService.pauseJob(group, name);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/groups/{group}/jobs/{name}/resume")
    public ResponseEntity<Void> resumeJob(@PathVariable String group, @PathVariable String name) {
        quartzService.resumeJob(group, name);
        return ResponseEntity.noContent().build();
    }
}
