import jdk.jfr.EventType;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class EventStore {

    private final Path file;

    public EventStore(Path file) {
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
        return readAllEvents(file).stream()
                .filter(e -> matchesType(e, type))
                .toList();
    }

    public List<EventType> getMatchingEventTypes(String regex) {
        return readAllEvents(file).stream()
                .map(RecordedEvent::getEventType)
                .filter(t -> t.getName().matches(regex))
                .distinct()
                .toList();
    }
}
