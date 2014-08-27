package fi.helsinki.cs.tmc.snapshot.api.service;

import fi.helsinki.cs.tmc.snapshot.api.exception.NotFoundException;
import fi.helsinki.cs.tmc.snapshot.api.model.Exercise;
import fi.helsinki.cs.tmc.snapshot.api.model.Snapshot;
import fi.helsinki.cs.tmc.snapshot.api.model.SnapshotEvent;
import fi.helsinki.cs.tmc.snapshot.api.model.SnapshotFile;
import fi.helsinki.cs.tmc.snapshot.api.util.EventTransformer;
import fi.helsinki.cs.tmc.snapshot.api.util.KeyLevelEventProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ KeyLevelEventProcessor.class, EventTransformer.class })
public final class DefaultSnapshotServiceTest {

    private static final String INSTANCE = "testInstance";
    private static final String USERNAME = "testUsername";
    private static final String COURSE = "testCourse";
    private static final String EXERCISE = "testExercise";

    @Mock
    private ExerciseService exerciseService;

    @Mock
    private KeyLevelEventProcessor eventProcessor;

    @Mock
    private EventTransformer eventTransformer;

    @InjectMocks
    private DefaultSnapshotService snapshotService;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllReturnsProcessedSnapshots() throws IOException {

        final Exercise exercise = new Exercise(EXERCISE);

        exercise.addSnapshotEvent(new SnapshotEvent());
        exercise.addSnapshotEvent(new SnapshotEvent());

        final Collection<SnapshotEvent> events = exercise.getSnapshotEvents();

        final List<Snapshot> snapshots = new ArrayList<>();

        snapshots.add(new Snapshot("1", 1L, new HashMap<String, SnapshotFile>(), false));
        snapshots.add(new Snapshot("2", 2L, new HashMap<String, SnapshotFile>(), false));
        snapshots.add(new Snapshot("3", 3L, new HashMap<String, SnapshotFile>(), false));

        when(exerciseService.find(INSTANCE, USERNAME, COURSE, EXERCISE)).thenReturn(exercise);
        when(eventTransformer.toSnapshotList(events)).thenReturn(snapshots);

        final List<Snapshot> returnedSnapshots = snapshotService.findAll(INSTANCE, USERNAME, COURSE, EXERCISE);

        verify(exerciseService).find(INSTANCE, USERNAME, COURSE, EXERCISE);
        verify(eventTransformer).toSnapshotList(events);
        verify(eventProcessor).process(events);

        assertEquals(returnedSnapshots, snapshots);
    }

    @Test(expected = NotFoundException.class)
    public void findAllThrowsNotFoundExceptionWhenNullBuiltSnapshots() throws IOException {

        final Exercise exercise = new Exercise(EXERCISE);

        when(exerciseService.find(INSTANCE, USERNAME, COURSE, EXERCISE)).thenReturn(exercise);
        when(eventTransformer.toSnapshotList(exercise.getSnapshotEvents())).thenReturn(null);

        snapshotService.findAll(INSTANCE, USERNAME, COURSE, EXERCISE);
    }

    @Test
    public void findReturnsCorrectSnapshot() throws IOException {

        final List<Snapshot> snapshots = new ArrayList<>();

        final Snapshot snapshot = new Snapshot("0", 0L, new HashMap<String, SnapshotFile>(), false);
        snapshots.add(snapshot);

        snapshots.add(new Snapshot("1", 1L, new HashMap<String, SnapshotFile>(), false));
        snapshots.add(new Snapshot("2", 2L, new HashMap<String, SnapshotFile>(), false));
        snapshots.add(new Snapshot("3", 3L, new HashMap<String, SnapshotFile>(), false));

        when(exerciseService.find(INSTANCE, USERNAME, COURSE, EXERCISE)).thenReturn(new Exercise(EXERCISE));
        when(eventTransformer.toSnapshotList(any(List.class))).thenReturn(snapshots);

        final Snapshot retrieved = snapshotService.find(INSTANCE, USERNAME, COURSE, EXERCISE, "0");
        assertEquals(snapshot, retrieved);
    }

    @Test(expected = NotFoundException.class)
    public void findThrowsNotFoundExceptionIfNoSnapshotIsFound() throws IOException {

        final List<Snapshot> snapshots = new ArrayList<>();

        snapshots.add(new Snapshot("1", 1L, new HashMap<String, SnapshotFile>(), false));
        snapshots.add(new Snapshot("2", 2L, new HashMap<String, SnapshotFile>(), false));
        snapshots.add(new Snapshot("3", 3L, new HashMap<String, SnapshotFile>(), false));

        when(exerciseService.find(INSTANCE, USERNAME, COURSE, EXERCISE)).thenReturn(new Exercise(EXERCISE));
        when(eventTransformer.toSnapshotList(any(List.class))).thenReturn(snapshots);

        snapshotService.find(INSTANCE, USERNAME, COURSE, EXERCISE, "0");
    }
}
