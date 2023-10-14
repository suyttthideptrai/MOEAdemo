package org.example;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;

import java.util.List;

public class StableMarriageSample {
    public static float roundToDecimalPlaces(double value, int decimalPlaces) {
        double multiplier = (double) Math.pow(10, decimalPlaces);
        return (float) (Math.round(value * multiplier) / multiplier);
    }
    public static void printOutResult(NondominatedPopulation result){
        System.out.println("Chromosomes:            |" + "OBJ_1\t" + "OBJ_2");
        System.out.println("                        |" + "Happi  Stable");
        for (Solution solution : result) {
            System.out.print("\n" + solution.getVariable(0).toString() + "\t\t|");
            System.out.print(roundToDecimalPlaces(-solution.getObjective(0), 4) + "\t"); // Negate to show maximized objective
            System.out.print(Math.abs(roundToDecimalPlaces(solution.getObjective(1), 4)));
        }
        System.out.println();
    }
    public static void printOutResult(List<Solution> result){
        System.out.println("Chromosomes:            |" + "OBJ_1\t" + "OBJ_2");
        System.out.println("                        |" + "Happi  Stable");
        for (Solution solution : result) {
            System.out.print("\n" + solution.getVariable(0).toString() + "\t\t|");
            System.out.print(roundToDecimalPlaces(-solution.getObjective(0), 4) + "\t"); // Negate to show maximized objective
            System.out.print(roundToDecimalPlaces(solution.getObjective(1), 4) );
        }
        System.out.println();
    }
    public static void printOutSolution(Solution result){
        System.out.println("Chromosomes:            |" + "OBJ_1\t" + "OBJ_2");
        System.out.println("                        |" + "Happi  Stable");
        System.out.print("\n" + result.getVariable(0).toString() + "\t|");
        System.out.print(roundToDecimalPlaces(-result.getObjective(0), 4) + "\t"); // Negate to show maximized objective
        System.out.print(roundToDecimalPlaces(result.getObjective(1), 4) );
        System.out.println();
    }

    public static void main(String[] args) {
        int n = 10;  // Number of boys = Number of girls

        int[][] boysPreferences = {
                {2, 1, 3, 4, 5, 6, 7, 8, 9, 10},
                {5, 3, 1, 4, 2, 6, 7, 9, 8, 10},
                {8, 4, 1, 2, 5, 6, 7, 3, 9, 10},
                {6, 1, 3, 2, 5, 4, 7, 8, 10, 9},
                {7, 2, 1, 3, 4, 6, 8, 5, 9, 10},
                {3, 2, 1, 7, 5, 6, 4, 8, 9, 10},
                {2, 3, 4, 5, 6, 1, 7, 8, 9, 10},
                {5, 2, 4, 3, 1, 6, 7, 8, 9, 10},
                {2, 3, 6, 1, 5, 4, 7, 8, 9, 10},
                {4, 2, 3, 1, 5, 6, 7, 8, 9, 10}
        };

        int[][] girlsPreferences = {
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {2, 1, 4, 3, 6, 5, 7, 9, 8, 10},
                {4, 3, 1, 2, 7, 6, 8, 5, 9, 10},
                {1, 4, 3, 2, 5, 6, 7, 8, 10, 9},
                {3, 2, 4, 1, 6, 5, 7, 8, 9, 10},
                {1, 5, 3, 2, 6, 4, 7, 8, 9, 10},
                {4, 2, 3, 1, 6, 5, 7, 8, 9, 10},
                {1, 2, 4, 3, 5, 6, 7, 8, 9, 10},
                {2, 4, 3, 1, 6, 5, 7, 8, 9, 10},
                {2, 3, 1, 4, 5, 6, 7, 8, 9, 10}
        };


        Problem problem = new StableMarriage(n, boysPreferences, girlsPreferences);
        Problem problem1 = new StableMarriageWithFitness(n, boysPreferences, girlsPreferences);
        NondominatedPopulation result = new Executor()
                        .withProblem(problem)
                        .withAlgorithm("NSGAII")
                        .withMaxEvaluations(10000)
                        .withProperty("populationSize", 100)
                        .run();
        NondominatedPopulation result1 = new Executor()
                .withProblem(problem1)
                .withAlgorithm("NSGAII")
                .withMaxEvaluations(10000)
                .withProperty("populationSize", 100)
                .run();
        printOutResult(result);
        System.out.println();
        System.out.println("Chromosomes:            |" + "OBJ_1");
        System.out.println("                        |" + "fitness");
        for (Solution solution : result1) {
            System.out.print("\n" + solution.getVariable(0).toString() + "\t\t|");
            System.out.print(roundToDecimalPlaces(-solution.getObjective(0), 4) + "\t"); // Negate to show maximized objective
        }
        System.out.println();

    }
}
