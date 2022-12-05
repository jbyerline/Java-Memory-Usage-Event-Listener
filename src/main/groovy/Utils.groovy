import java.text.DecimalFormat

class Utils {
    // Converts a long number that represents Bytes into a human readable form
    static String getDynamicSpace(long diskSpaceUsed){
        if (diskSpaceUsed <= 0) {
            return "0"
        }
        final String[] units = new String[] { "B", "KiB", "MiB", "GiB", "TiB" }
        int digitGroups = (int) (Math.log10(diskSpaceUsed) / Math.log10(1024))
        return new DecimalFormat("#,##0.##").format(diskSpaceUsed / Math.pow(1024, digitGroups)) + " " + units[digitGroups]
    }
}
