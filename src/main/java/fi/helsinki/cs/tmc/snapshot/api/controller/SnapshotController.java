package fi.helsinki.cs.tmc.snapshot.api.controller;

import fi.helsinki.cs.tmc.snapshot.api.exception.NotFoundException;
import fi.helsinki.cs.tmc.snapshot.api.model.Snapshot;
import fi.helsinki.cs.tmc.snapshot.api.service.SnapshotService;
import fi.helsinki.cs.tmc.snapshot.api.service.TmcService;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/participants", produces = "application/json")
public final class SnapshotController {

    @Autowired
    private SnapshotService snapshotService;

    @Autowired
    private TmcService tmcService;

    @RequestMapping(method = RequestMethod.GET, value = "{participant}/snapshots")
    public List<Snapshot> list(@PathVariable final Long participant) throws IOException {

        final String username = tmcService.findUsernameById("", participant);

        if (username == null) {
            throw new NotFoundException();
        }

        return snapshotService.findAll("/hy/", username);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{participant}/snapshots/{snapshot}")
    public Snapshot read(@PathVariable final Long participant, @PathVariable final Long snapshot) throws IOException {

        final String username = tmcService.findUsernameById("", participant);

        if (username == null) {
            throw new NotFoundException();
        }

        final Snapshot result = snapshotService.find("/hy/", username, snapshot);

        if (result == null) {
            throw new NotFoundException();
        }

        return result;
    }
}
