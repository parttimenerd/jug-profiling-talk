import java.nio.file.Path;

public class EventFilterApp2 {

    int countEvents(Path file, String typeRegex) {
        var store = new EventStore2(file);
        var types = store.getMatchingEventTypes(typeRegex);
        int count = 0;
        for (var type : types) {
            count += store.getEvents(type).size();
        }
        return count;
    }

    public static void main(String[] args) {
        var app = new EventFilterApp2();
        var count = app.countEvents(Path.of(args[0]), args[1]);
        System.out.println(count);
    }
}