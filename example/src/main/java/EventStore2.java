import jdk.jfr.EventType;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class EventStore2 {

    private final Path file;

    public EventStore2(Path file) {
        this.file = file;
    }

    private static List<RecordedEvent> readAllEvents(Path file) {
        try {
            return RecordingFile.readAllEvents(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean matchesType(RecordedEvent event, EventType type) {
        return event.getEventType().getName().equals(type.getName());
    }

    public List<RecordedEvent> getEvents(EventType type) {
        try (var recording = new RecordingFile(file)) {
            List<RecordedEvent> events = new ArrayList<>();
            while (recording.hasMoreEvents()) {
                var event = recording.readEvent();
                if (matchesType(event, type)) {
                    events.add(event);
                }
            }
            return events;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<EventType> getMatchingEventTypes(String regex) {
        return readAllEvents(file).stream()
                .map(RecordedEvent::getEventType)
                .filter(t -> t.getName().matches(regex))
                .distinct()
                .toList();
    }
}
