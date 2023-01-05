package delias.mcCluskey;

import java.util.*;

public class MintermCategory extends ArrayList<Minterm> {

    public MintermCategory(Minterm... minterms) {
        for (Minterm minterm : minterms){
            this.add(minterm);
        }
    }

    /**
     * It computes the list of minterms m, such that :
     * - either m results from  merging a minterm from the category "this" with a minterm from the other category ;
     * - either m belongs to the current category (this) and could not be unified with a minterm of the other category
     * @param otherCategory
     * @return  the list of merged minterms
     */
    public List<Minterm> merge(MintermCategory otherCategory){
        Set<Minterm> result = new HashSet<>();
        for (Minterm minterm : this) {
            for (Minterm mintermOtherCategory : otherCategory)
            if (minterm.merge(mintermOtherCategory)!=null) {
                result.add(minterm.merge(mintermOtherCategory));
            }
        }
        if (result.size()==0) {
            return this;
        }
        return new ArrayList<>(result);
    }
}