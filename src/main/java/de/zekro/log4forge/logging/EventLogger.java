package de.zekro.log4forge.logging;

import de.zekro.log4forge.Log4Forge;
import org.apache.logging.log4j.Level;

import java.time.Duration;
import java.util.*;

public class EventLogger {
    private final List<Entry> entryStash;
    private final List<ISink> sinks;
    private final Duration saveInterval;
    private final long maxEntries;
    private Timer saveTimer;

    public EventLogger(long maxEntries, long saveIntervalMills) {
        this(maxEntries, Duration.ofMillis(saveIntervalMills));
    }

    public EventLogger(long maxEntries, Duration saveInterval) {
        this.entryStash = new ArrayList<>();
        this.sinks = new ArrayList<>();
        this.saveInterval = saveInterval;
        this.maxEntries = maxEntries;
        this.startSaveTimer(this::saveJob);
    }

    public void addSink(ISink sink) {
        sink.setMaxEntries(this.maxEntries);
        this.sinks.add(sink);
    }

    public void stopSaveTimer() {
        this.saveTimer.cancel();
    }

    public void log(Entry entry) {
        this.entryStash.add(entry);
    }

    private void startSaveTimer(Runnable callback) {
        this.saveTimer = new Timer();
        this.saveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                callback.run();
            }
        }, this.saveInterval.toMillis(), this.saveInterval.toMillis());
    }

    private void saveJob() {
        if (entryStash.size() == 0) return;

        int size = this.entryStash.size();
        Entry[] entryStashSnapshot = new Entry[size];
        entryStashSnapshot = this.entryStash.toArray(entryStashSnapshot);
        this.entryStash.clear();

        int from = 0;
        int to = size;
        if (size > this.maxEntries) {
            from = size - (int) this.maxEntries;
        }
        final List<Entry> entryStashSnapshotIter = Arrays.asList(entryStashSnapshot).subList(from, to);
        new Thread(() ->
            this.sinks.stream().filter(ISink::isEnabled).forEach(s -> {
                Log4Forge.getLogger().log(Level.INFO, "saving event logs...");
                try {
                    s.append(entryStashSnapshotIter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            })).start();
    }
}
