package fi.helsinki.cs.tmc.snapshot.api.controller;

import fi.helsinki.cs.tmc.snapshot.api.model.Snapshot;
import fi.helsinki.cs.tmc.snapshot.api.service.SnapshotService;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "{instance}/participants/{userId}/courses/{courseId}/exercises/{exerciseId}/snapshots", produces = "application/json")
public final class SnapshotController {

    @Autowired
    private SnapshotService snapshotService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Snapshot> list(@PathVariable final String instance,
                               @PathVariable final String userId,
                               @PathVariable final String courseId,
                               @PathVariable final String exerciseId) throws IOException {

        return snapshotService.findAll(instance, userId, courseId, exerciseId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{snapshotId}")
    public Snapshot read(@PathVariable final String instance,
                         @PathVariable final String userId,
                         @PathVariable final String courseId,
                         @PathVariable final String exerciseId,
                         @PathVariable final String snapshotId) throws IOException {

        return snapshotService.find(instance, userId, courseId, exerciseId, snapshotId);
    }
}
