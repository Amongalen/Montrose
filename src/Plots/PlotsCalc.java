package Plots;

import java.io.*;
import java.util.Arrays;

/**
 *
 * @author Adam Parys
 */
public class PlotsCalc {

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input number of plots for sale:");
        int plotsAmount = Integer.parseInt(br.readLine());
        System.out.println("Input plots' values");
        int[] plotsValues = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        PlotsCalc pc = new PlotsCalc();
        pc.solve(plotsAmount, plotsValues);
    }

    public void solve(int plotsAmount, int[] plotsValues) {
        int maxSoFar = plotsValues[0], maxEndingHere = plotsValues[0],
                bestStart = 0, bestEnd = 0, currentStart = 0;
        for (int currentEnd = 1; currentEnd < plotsAmount; currentEnd++) {
            maxEndingHere += plotsValues[currentEnd];

            if (maxEndingHere > maxSoFar || (maxSoFar == maxEndingHere && currentEnd - currentStart < bestEnd - bestStart)) {
                maxSoFar = maxEndingHere;
                bestStart = currentStart;
                bestEnd = currentEnd;
            }

            if (maxEndingHere <= 0) {
                maxEndingHere = 0;
                currentStart = currentEnd + 1;
            }
        }
        System.out.println((bestStart + 1) + " " + (bestEnd + 1) + " " + maxSoFar);
    }
}
