import java.nio.file.Path;

public class EventFilterApp {

    int countEvents(Path file, String typeRegex) {
        var store = new EventStore(file);
        var types = store.getMatchingEventTypes(typeRegex);
        int count = 0;
        for (var type : types) {
            count += store.getEvents(type).size();
        }
        return count;
    }

    public static void main(String[] args) {
        var app = new EventFilterApp();
        var count = app.countEvents(Path.of(args[0]), args[1]);
        System.out.println(count);
    }
}