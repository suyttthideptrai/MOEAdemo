package org.example;


import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;

import java.util.ArrayList;
import java.util.List;


public class Main {
    //lam tron ket qua
    public static float roundToDecimalPlaces(double value, int decimalPlaces) {
        double multiplier = (double) Math.pow(10, decimalPlaces);
        return (float) (Math.round(value * multiplier) / multiplier);
    }
    public static List<Solution> filterByPreferences(NondominatedPopulation result) {
        List<Solution> filteredSolutions = new ArrayList<>();

        // Iterate through the solutions in the population
        for (Solution solution : result) {
            // Get the objective values for the overflow rate (Objective 1)
            double overflowRate = solution.getObjective(1);


            // Check if the overflow rate is between 0 and 10.0
            if (overflowRate >= 0.0 && overflowRate <= 10.0) {
                filteredSolutions.add(solution);
            }
        }

        return filteredSolutions;
    }
    public static void printOutResult(NondominatedPopulation result){
        System.out.println("Bit String              |" + "OBJ_1\t" + "OBJ_2");
        System.out.println("                        |" + "tValue Overflow");
        for (Solution solution : result) {
            System.out.print("\n" + solution.getVariable(0).toString() + "\t|");
            System.out.print(roundToDecimalPlaces(-solution.getObjective(0), 4) + "\t"); // Negate to show maximized objective
            System.out.print(roundToDecimalPlaces(solution.getObjective(1), 4) );
        }
    }
    public static void printOutResult(List<Solution> result){
        System.out.println("Bit String              |" + "OBJ_1\t" + "OBJ_2");
        System.out.println("                        |" + "tValue Overflow");
        for (Solution solution : result) {
            System.out.print("\n" + solution.getVariable(0).toString() + "\t|");
            System.out.print(roundToDecimalPlaces(-solution.getObjective(0), 4) + "\t"); // Negate to show maximized objective
            System.out.print(roundToDecimalPlaces(solution.getObjective(1), 4) );
        }
    }
    public static void printOutResult(Solution result){
        System.out.println("Bit String              |" + "OBJ_1\t" + "OBJ_2");
        System.out.println("                        |" + "tValue Overflow");
            System.out.print("\n" + result.getVariable(0).toString() + "\t|");
            System.out.print(roundToDecimalPlaces(-result.getObjective(0), 4) + "\t"); // Negate to show maximized objective
            System.out.print(roundToDecimalPlaces(result.getObjective(1), 4) );
    }
    public static Solution findSolutionWithMaxObjective(List<Solution> filteredSolutions) {
        if (filteredSolutions.isEmpty()) {
            return null; // Return null if the list is empty
        }

        Solution maxObjectiveSolution = filteredSolutions.get(0); // Initialize with the first solution

        // Iterate through the filtered solutions to find the one with the maximum OBJ_1 value
        for (Solution solution : filteredSolutions) {
            double currentObjective = -solution.getObjective(0); // Negate to account for maximization

            double maxObjective = -maxObjectiveSolution.getObjective(0);

            if (currentObjective > maxObjective) {
                maxObjectiveSolution = solution; // Update the maximum objective solution
            }
        }

        return maxObjectiveSolution;
    }

    public static void main(String[] args) {
        //create problem instance
        int numberOfItems = 20;
        //gia tri cua tung item
        double[] values = {
                13.5, 21.6, 8.7, 17.4, 4.8,
                10.9, 15.2, 6.5, 11.8, 14.3,
                9.7, 22.1, 12.6, 18.9, 7.3,
                20.5, 16.0, 5.4, 19.7, 3.2
        };
        // can nang cua tung item
        double[] weights = {
                6.4, 12.2, 4.1, 9.6, 5.3,
                7.5, 8.9, 3.8, 6.1, 11.0,
                5.9, 12.7, 7.2, 8.4, 4.6,
                10.3, 9.1, 3.3, 14.8, 2.7
        };
        //can nang toi da co the chua duoc
        double maxWeight = 50.0;
        //create problem instance
        Knapsack problem = new Knapsack(numberOfItems, weights, values, maxWeight);


        //execute
        NondominatedPopulation result = new Executor()
                .withProblem(problem)
                .withAlgorithm("NSGAII")
                .withMaxEvaluations(10000)
                .withProperty("populationSize", 100)
//                .withInstrumenter(instrumenter)
                .run();

        List<Solution> filteredResult = filterByPreferences(result);
        Solution best = findSolutionWithMaxObjective(filteredResult);
        printOutResult(filteredResult);
        System.out.println();
        System.out.println("Best solution: ");
        printOutResult(best);
    }
}
