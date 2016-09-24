package traceability;

/** Source: http://cs.joensuu.fi/~zhao/Link/Similarity_strings.html
 *
 */
public class Levenshtein 
{
	/** Source: http://rosettacode.org/wiki/Levenshtein_distance#Java
	 * Method returning the Levensthein distance
	 */
	public static int distance(String a, String b) 
	{
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
	
	/** Method returning the percentage value of the Levensthein distance
	 *@param first string
	 *@param second string
	 */
	public static double returnDistanceInPercentage(String first, String second) 
	{
		int distance = distance(first, second);
        double percentage = ((double) distance) / (Math.max(first.length(), second.length()));
        percentage = 1-percentage;
        //System.out.println(percentage);
        return percentage; 
	}
}
