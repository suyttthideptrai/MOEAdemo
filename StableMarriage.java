package org.example;

import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.Permutation;

public class StableMarriage implements Problem {
    //n = number of boys = number of girls
    private final int n;
    //2 dimension preference list array: index -> array of preference list length = n
    /*
    example of 2d dimension that represent person preferences for partner (case n=5)
    {
    {1,2,3,4,5},
    {2,3,5,4,1},
    {5,4,1,2,3},
    {2,1,3,5,4},
    {3,4,1,2,5}
    }
    each sub array is the preference list of person index = i (i inside 0 to 4)

    same type of array for partner
     */
    private final int[][] person;
    private final int[][] partner;

    public StableMarriage(int n, int[][] person, int[][] partner) {
        this.n = n;
        this.person = person;
        this.partner = partner;
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(1, 2); // 1 variable, 2 objectives
        solution.setVariable(0, EncodingUtils.newPermutation(n));
        return solution;
    }

    @Override
    public String getName() {
        return "Stable Marriage Problem";
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

    private double calculateIndividualCoupleSatisfaction(int personTarget, int partnerTarget) {
        // Get the preference list for the person and partner
        int[] personPreferences = person[personTarget];
        int[] partnerPreferences = partner[partnerTarget];
        // Find the position of the person & partner in the preference list
        int personRank = partnerPreferences[personTarget];
        int partnerRank = personPreferences[partnerTarget];
        // Calculate individual couple satisfaction (example: higher values for higher preference)
        return personRank + partnerRank;
    }

    private double calculateEgalitarianOfEachCouple(int personTarget, int partnerTarget) {
        // Get the preference lists for both the person and the partner
        int[] personPreferences = person[personTarget];
        int[] partnerPreferences = partner[partnerTarget];
        // Find the positions of the person & partner in the preference lists
        int personRank = partnerPreferences[personTarget];
        int partnerRank = personPreferences[partnerTarget];
        // Calculate egalitarian happiness for this couple (Egalitarian = Pw - Pm, lower is more stable)
        return partnerRank - personRank;
    }

    @Override
    public void evaluate(Solution solution) {
        double overallSatisfaction = 0.0;
        double totalEgalitarian = 0.0;
        int[] PartnerMatches = ((Permutation) solution.getVariable(0)).toArray();
        for(int i = 0; i < n ; i++){
            int partner = PartnerMatches[i]; // The current person's matched partner
            int person = i; // Current person, as it's 1-based

            // Calculate each couple satisfaction after matching (you need to define how this is calculated)
            double individualSatisfaction = calculateIndividualCoupleSatisfaction(person, partner);

            // Calculate egalitarian for this couple after matching (Egalitarian = Pw - Pm, lower is more stable)
            double egalitarian = calculateEgalitarianOfEachCouple(person, partner);

            // Update overall satisfaction and total egalitarian
            overallSatisfaction += individualSatisfaction;
            totalEgalitarian += egalitarian;
        }

        solution.setObjective(0, -overallSatisfaction);
        solution.setObjective(1, totalEgalitarian);
    }

    @Override
    public void close() {
        // Close any resources if needed
    }
}

