package events;

import java.util.EventListener;

public interface ProcessOverrideFileListener extends EventListener {
    void onEvent();

    void onDestroy();
}
