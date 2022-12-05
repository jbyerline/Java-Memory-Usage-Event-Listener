import java.text.DecimalFormat

class Main {
  static void main(String[] args) {
    // Percentage Formatter
    DecimalFormat df = new DecimalFormat("0.00")
    // Set initial threshold percentage
    MemoryWarningSystem.setPercentageUsageThreshold(0.6)
    // Instantiate Memory Warning System
    MemoryWarningSystem mws = new MemoryWarningSystem()
    // Create a listener
    mws.addListener(new MemoryWarningSystem.Listener() {
      // Define Listener interface method
      void memoryUsageLow(long usedMemory, long maxMemory) {
        // Calculate percentage of memory used
        double percentageUsed = (((double) usedMemory) / maxMemory) * 100
        // Print warning message
        System.out.println("--- WARNING: Memory usage low! ---")
        System.out.println("Memory used: " + Utils.getDynamicSpace(usedMemory) + " of " + Utils.getDynamicSpace(maxMemory) + " --> " + df.format(percentageUsed) + "%")
        // Set new threshold for 2nd alert
        MemoryWarningSystem.setPercentageUsageThreshold(0.8)
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