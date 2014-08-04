package fi.helsinki.cs.tmc.snapshot.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Snapshot implements Comparable<Snapshot> {

    private final Long id;
    private final String course;
    private final String exercise;
    private final Map<String, SnapshotFile> files;
    private final Date timestamp;
    private final boolean fromCompleteSnapshot;

    @JsonCreator
    public Snapshot(@JsonProperty("id") final Long id, @JsonProperty("course") final String course, @JsonProperty("exercise") final String exercise, @JsonProperty("files") final List<SnapshotFile> files) {

        this.id = id;
        this.course = course;
        this.exercise = exercise;
        this.timestamp = new Date(id);
        this.fromCompleteSnapshot = false;

        this.files = new HashMap<>();

        for (SnapshotFile file : files) {
            this.files.put(file.getPath(), file);
        }
    }

    public Snapshot(final Long id, final String course, final String exercise, final Map<String, SnapshotFile> files) {

        this(id, course, exercise, files, false);
    }

    public Snapshot(final Long id, final String course, final String exercise, final Map<String, SnapshotFile> files, final boolean fromCompleteSnapshot) {

        this.id = id;
        this.course = course;
        this.exercise = exercise;
        this.timestamp = new Date(id);
        this.fromCompleteSnapshot = fromCompleteSnapshot;
        this.files = files;
    }

    public Long getId() {

        return id;
    }

    public String getCourse() {

        return course;
    }

    public String getExercise() {

        return exercise;
    }

    public Collection<SnapshotFile> getFiles() {

        return files.values();
    }

    public SnapshotFile getFile(final String path) {

        return files.get(path);
    }

    public Date getTimestamp() {

        return timestamp;
    }

    @Override
    public int compareTo(final Snapshot other) {
        return this.timestamp.compareTo(other.timestamp);
    }

    public void addFile(final SnapshotFile file) {

        files.put(file.getPath(), file);
    }

    @JsonIgnore
    public boolean isFromCompleteSnapshot() {

        return fromCompleteSnapshot;
    }
}
