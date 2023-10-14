package org.example;

import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.BinaryVariable;
import org.moeaframework.core.variable.EncodingUtils;

//tao ra 1 set item de chon loc -> n items
public class Knapsack implements Problem {
    private final int numberOfItems;

    private final double[] weights;
    private final double[] values;
    //minimize overflow weight
    // total weight of solution - max weight -> overflow
    private final double maxWeight;

    // define object of a problem
    public Knapsack(int numberOfItems, double[] weights, double[] values, double maxWeight) {
        this.numberOfItems = numberOfItems;
        this.weights = weights;
        this.values = values;
        this.maxWeight = maxWeight;
    }
    //create solution by permutation of 8 items to put into sack
    // solution : 101110
    @Override
    //solution -> fitnsescore
    //TSM -> [4,5,1,2] ->  totallength: +=
    //Define Solution -> create frame for solution population
    public Solution newSolution() {
        // Define the decision variables (RealVariables)
        // sample solution generate
        Solution solution = new Solution(1, 2);
        solution.setVariable(0, EncodingUtils.newBinary(numberOfItems)); // Binary variable (0 or 1)
        return solution;
    }


    //get
    @Override
    public String getName() {
        return "Multiple Objective Knapsack Problem";
    }

    @Override
    public int getNumberOfVariables() {
        return 1;
    }

    @Override
    public int getNumberOfObjectives() {
        return 2;
    }

    @Override
    public int getNumberOfConstraints() {
        return 0;
    }

    //fitness function: Objedctive F(101001) =
    @Override
    // Take solution as input & produce fitnesscore for each solution
    public void evaluate(Solution solution) {
        double totalScore = 0.0;
        double totalWeight = 0.0;

        BinaryVariable binaryVariable = (BinaryVariable) solution.getVariable(0);

        for (int i=0; i< numberOfItems; i++){
            if(binaryVariable.get(i)){
                // objective 1: fitness score
                totalScore += values[i];
                // objective 2:______________
                totalWeight += weights[i];
            }
        }
        //knapsack problem -> 2 objectives
        //total score
        //obj = -n
        solution.setObjective(0, -totalScore);
        //overflow score check
        if(totalWeight <= maxWeight){
            double overflow = 0;
            solution.setObjective(1, overflow);
        }else{
            //assign the overflow value -> minimize
            double overflow = totalWeight -maxWeight;
            solution.setObjective(1, overflow);
        }
    }
    @Override
    public void close() {

    }
}
