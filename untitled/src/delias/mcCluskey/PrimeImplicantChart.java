package delias.mcCluskey;

import java.util.List;

public class PrimeImplicantChart {

    /**
     * Initializes the grid with the original minterms and values.
     * @param values        Initial decimal values (they are also included in the combinations of minterms).
     * @param mintermList   The list of minterms reduced by merging the categories
     */
    public PrimeImplicantChart(int [] values, List<Minterm> mintermList) {

    }



    /**
     * extracts only the essential minterms; they correspond to the minterms that are the only ones to represent one of the initial values.
     * @return essential minterm list
     */
    public  List<Minterm> extractEssentialPrimeImplicants() {

        return null;
    }


    /**
     * After removing the initial values covered by the essential minterms,
     * choose a minterm for each remaining value not covered by an essential minterm.
     */
    public  List<Minterm> extractRemainingImplicants() {

        return null;
    }

}