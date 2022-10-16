package events;

import java.util.Collection;
import java.util.HashSet;

public class OverrideEvent {
    private Collection<ProcessOverrideFileListener> listeners = new HashSet<>();

    public void register(ProcessOverrideFileListener listener) {
        listeners.add(listener);
    }

    public void start() {
        for (ProcessOverrideFileListener listener : listeners) {
            listener.onEvent();
        }
    }

    public void end() {
        for (ProcessOverrideFileListener listener : listeners) {
            listener.onDestroy();
        }
    }
}
