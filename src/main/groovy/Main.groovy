import java.text.DecimalFormat
import java.time.Duration

class Main {
  static void main(String[] args) {
    double threshold = 0.1d
    // Get start time of program
    final long startTime = System.nanoTime()
    // Percentage Formatter
    DecimalFormat df = new DecimalFormat("0.00")
    // Set initial threshold percentage
    MemoryWarningSystem.setPercentageUsageThreshold(threshold)
    // Instantiate Memory Warning System
    MemoryWarningSystem mws = new MemoryWarningSystem()
    // Create a listener
    mws.addListener(new MemoryWarningSystem.Listener() {
      // Define Listener interface method
      void memoryUsageLow(long usedMemory, long maxMemory) {
        // Calculate runtime of program so far
        final long elapsedTime = System.nanoTime() - startTime
        Duration duration = Duration.ofNanos(elapsedTime)
        String formattedElapsedTime = String.format(
                "%02d Day %02d Hour %02d Min %02d Sec elapsed.", duration.toDays(),
                duration.toHours() % 24, duration.toMinutes() % 60, duration.toSeconds() % 60)
        // Calculate percentage of memory used
        double percentageUsed = (((double) usedMemory) / maxMemory) * 100
        // Print warning message
        System.out.println("--- WARNING: Memory usage low! ---")
        System.out.println("Memory used: " + Utils.getDynamicSpace(usedMemory) + " of " + Utils.getDynamicSpace(maxMemory) + " --> " + df.format(percentageUsed) + "%")
        System.out.println(formattedElapsedTime)
        if (threshold < 0.5d){
          threshold = threshold + 0.1d
        } else {
          System.out.println("Memory Full, exiting program gracefully")
          Runtime.getRuntime().halt(1)
        }
        // Set new threshold for 2nd alert
        MemoryWarningSystem.setPercentageUsageThreshold(threshold)
      }
    })
    // Make a LinkedList of Doubles
    Collection<Double> numbers = new LinkedList<Double>()
    // Forever
    while (true) {
      // Add random numbers into the list until we fill up the heap
      numbers.add(Math.random())
    }
  }
}