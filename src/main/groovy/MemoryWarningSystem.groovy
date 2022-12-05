import javax.management.*
import javax.naming.NameNotFoundException
import java.lang.management.*

/**
 * This memory warning system will call the listener when we
 * exceed the percentage of available memory specified.  There
 * should only be one instance of this object created, since the
 * usage threshold can only be set to one number.
 */
class MemoryWarningSystem {
    private final Collection<Listener> listeners =
            new ArrayList<Listener>()

    interface Listener {
        void memoryUsageLow(long usedMemory, long maxMemory);
    }

    MemoryWarningSystem() {
        MemoryMXBean mbean = ManagementFactory.getMemoryMXBean()
        NotificationEmitter emitter = (NotificationEmitter) mbean
        emitter.addNotificationListener(new NotificationListener() {
            void handleNotification(Notification n, Object hb) {
                if (n.getType() == MemoryNotificationInfo.MEMORY_THRESHOLD_EXCEEDED) {
                    long maxMemory = tenuredGenPool.getUsage().getMax()
                    long usedMemory = tenuredGenPool.getUsage().getUsed()
                    for (Listener listener : listeners) {
                        listener.memoryUsageLow(usedMemory, maxMemory)
                    }
                }
            }
        }, null, null)
    }

    boolean addListener(Listener listener) {
        return listeners.add(listener)
    }

    boolean removeListener(Listener listener) {
        return listeners.remove(listener)
    }

    private static final MemoryPoolMXBean tenuredGenPool = findTenuredGenPool()

    static void setPercentageUsageThreshold(double percentage) {
        if (percentage <= 0.0 || percentage > 1.0) {
            throw new IllegalArgumentException("Percentage not in range")
        }
        long maxMemory = tenuredGenPool.getUsage().getMax()
        long warningThreshold = (long) (maxMemory * percentage)
        tenuredGenPool.setUsageThreshold(warningThreshold)
    }

    /**
     * Tenured Space Pool can be determined by it being of type
     * HEAP and by it being possible to set the usage threshold.
     */
    private static MemoryPoolMXBean findTenuredGenPool() {
        for (MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
            if (pool.getType() == MemoryType.HEAP && pool.isUsageThresholdSupported()) {
                return pool
            }
        }
        throw new NameNotFoundException("Could not find Tenured Space Pool")
    }
}