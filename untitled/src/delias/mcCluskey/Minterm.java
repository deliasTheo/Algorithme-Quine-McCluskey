package delias.mcCluskey;

import java.util.*;

public class Minterm {

    private List<Integer> values = new ArrayList<>();
    private boolean marked;

    /**
     *
     * @param decimal   the decimal number for which we want to calculate the number of bits necessary to represent it
     * @return          the minimum number of bits needed to encode this decimal in binary.
     */
    public static int numberOfBitsNeeded(int decimal) {
        int i=1;
        while (decimal >= Math.pow(2, i)){
            i++;
        }
        return i;
    }

    /*********************************************************
     * Management of the minterms structure
     ******************************************************** */


    /**
     * returns all the numbers that were used to build this minterm.
     * For example, [0*00] may have been created from 0 and 2 (* = -1)
     * @return all the numbers that were used to build this minterm.
     */
    public Collection<Integer> getCombinations() {
        int  numberOfTheMinterm = 0;
        HashSet<Integer> result = new HashSet<>();
        List<Integer> indexOfDifferences = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) == 1){
                numberOfTheMinterm += Math.pow(2, values.size()-1-i);
            }
            if (values.get(i) == -1){
                indexOfDifferences.add((int) Math.pow(2, values.size()-1-i));
            }
        }
        result.add(numberOfTheMinterm);

        for (int i = 0; i < indexOfDifferences.size(); i++){
            for (int j = i+1; j < indexOfDifferences.size(); j++){
                System.out.println(j);
                result.add(numberOfTheMinterm+indexOfDifferences.get(i)+indexOfDifferences.get(j));

            }

            result.add(numberOfTheMinterm+indexOfDifferences.get(i));
        }
        System.out.println(indexOfDifferences.toString());

        return result;
    }



    /**
     * marks the minterm as used to build another minTerm
     */
    public void mark(){
        marked = true;
    }

    /**
     *
     * @return <code>true</code> if the minterm has been used to build another minterm, <code>false</code> otherwise.
     */
    public boolean isMarked(){
        return marked;
    }

    /*********************************************************
     * Management of the minterms contents
     ******************************************************** */
    /**
     *
     * @return return the number of 0 in the minterm
     */
    public int numberOfZero() {
        int numberOfZero = 0;
        for (int value : values){
            if (value == 0){
                numberOfZero++;
            }
        }
        return numberOfZero;
    }

    /**
     *
     * @return return the number of 1 in the minterm
     */
    public int numberOfOne() {
        int numberOfOne = 0;
        for (int value : values){
            if (value == 1){
                numberOfOne++;
            }
        }
        return numberOfOne;
    }



    /*********************************************************
     * Equality
     ******************************************************** */

    /**
     * @param o
     * @return true if the representation in base 2 is the same. Ignore the other elements.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Minterm minterm = (Minterm) o;
        for (int value : values){
            if (!minterm.values.contains(value)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + values.hashCode();
        return result;
    }



/* -------------------------------------------------------------------------
        Constructors
 ------------------------------------------------------------------------- */
    /**
     * Construct a minterm corresponding to the decimal passed in parameter
     * and encode it on the given number of bits.
     * The associated combination then contains <code>decimal</code>.
     * @param decimal       the decimal value representing the minterm
     * @param numberOfBits  the number of bits of encoding of the decimal
     */
    public Minterm(int decimal, int numberOfBits) {
        String binary = Integer.toBinaryString(decimal);
        for (String oneBinay : binary.split("")){
            values.add(Integer.parseInt(oneBinay));
        }
        Collections.reverse(values);
        if(values.size()<numberOfBits){
            int size = values.size();
            for (int i =0; i<(numberOfBits-size); i++){
                values.add(0);
            }
        }
        Collections.reverse(values);
    }

    /**
     * Builds a minterm from its representation in binary which can contain -1.
     * This constructor does not update the associated combinations.
     * The size of the binary representation corresponds to the number of parameters (binary.length).
     * @param binary
     */
    public Minterm(int... binary) {
        for (int oneBinary : binary){
            this.values.add(oneBinary);
        }
    }



    /**
     * Compute the string showing the binary form of the minterm.
     * For example, "101" represents the minterm corresponding to 5,
     * while "1-1" represents a minterm resulting, for example from the merge of 5 and 7 (1 -1 1)
     * @return the string
     */
    @Override
    public String toString() {
        String result = "";
        for (int value : values){
            if (value==-1) {result += "-";}
            else {result +=value;}
        }
        return result;
    }



/* -------------------------------------------------------------------------
        Binary <-> Decimal
 ------------------------------------------------------------------------- */

    /**
     * Calculates the integer value of the binary representation.
     * But in case one of the binary elements is -1, it returns -1.
     * This method is private because it should not be used outside this class.
     * @returns the value of the minterm calculated from its binary representation.
     */
    public int toIntValue(){
        int res = 0;
        for (int value : values) {
            if (value == -1) {
                return -1;
            }
            if (value == 1) {
                res+= Math.pow(2, values.indexOf(value));
            }
        }
        return res;
    }


   /* -------------------------------------------------------------------------
        Merge
 ------------------------------------------------------------------------- */


    /**
     * create a Minterm from the merge of two Minterms when it is posssible otherwise return null
     * Attention two minterms can only be merged if
     *  - they differ by one value at most.
     *  - they are of the same size.
     *  If a merge is possible, the returned minterm
     *  - has the same binary representation as the original minterm, but where at most one slot has been replaced by -1,
     *  - and it has, for the combinations, the merge of the combinations of both minterms <code>this</code> and <code>other</code>)
     *  - and the both mindterms  <code>this</code> and <code>other</code> are marked
     * @param other is another Minterm which we try to merge
     * @return a new Minterm when it is possible to unify, else null

     */
    public Minterm merge(Minterm other) {
        if (!verifyValideMinterm(other)) return null;
        this.mark();
        other.mark();
        int[] mintermValues = createMergedMinterm(other);
        return new Minterm(mintermValues);
    }

    private int[] createMergedMinterm(Minterm other) {
        int[] mintermValues = new int[this.values.size()];
        for (int i = 0; i < this.values.size(); i++) {
            if(this.values.get(i) != other.values.get(i)){
                mintermValues[i] = -1;
            }else{
                mintermValues[i] = this.values.get(i);
            }
        }
        return mintermValues;
    }

    private boolean verifyValideMinterm(Minterm other) {
        if (this.values.size() != other.values.size() ) {return false;}
        int countDifference=0;
        for (int i = 0; i < this.values.size(); i++) {
            if (this.values.get(i)!= other.values.get(i)){
                if(countDifference ==1){
                    return false;
                }
                countDifference++;
            }
        }
        return true;
    }

}
