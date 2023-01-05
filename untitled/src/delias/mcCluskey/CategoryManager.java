package delias.mcCluskey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryManager {


    public List<MintermCategory> mintermCategories = new ArrayList<>();
    private boolean lastTurn = false;

    /**
     * CategoryManager : compute the categories from a list of minterms according to the number of 11
     * @param mintermList
     * @return
     */
    public CategoryManager(List<Minterm> mintermList, int nbBits) {
        for (int i = 0; i <= nbBits; i++){
            MintermCategory category = new MintermCategory();
            for(Minterm minterm : mintermList){
                if (minterm.numberOfOne()==i){
                    category.add(minterm);
                }
            }
            if (category.size()!=0){
                mintermCategories.add(category);
            }
        }
    }


    public int numberOfCategories(){
        return mintermCategories.size();
    }


    /**
     *
     * @param numberOfOne
     * @return the Category Of Minterms containing  numberOfOne
     */
    public MintermCategory getCategory(int numberOfOne){
        for(MintermCategory category : mintermCategories){
            if (category.get(0).numberOfOne()==numberOfOne){
                return category;
            }
        }
        return null;
    }


    /**
     *  isLastTurn()
     * @return true is it's the last turn.
     */
    public boolean isLastTurn() {

        return lastTurn;
    }

    /**
     * Merge the categories two by two if they have only one "one" between them.
     * The minterms are the result of the merging of the categories.
     * Be careful for a category of n "one", if the category of "n+1" has no minterms,
     *    you must recover the minterms of the category of n "one" which were not marked.
     * This is the last round if no terms could be merged.
     * @return the merged terms
     */
    public List<Minterm> mergeCategories() {
        List<Minterm> res = new ArrayList<>();
        for(int i = 0; i <numberOfCategories()-1; i++) {
            if(mintermCategories.get(i).get(0).numberOfOne()==mintermCategories.get(i+1).get(0).numberOfOne()-1){
                res.addAll(mintermCategories.get(i).merge(mintermCategories.get(i + 1)));
            }
        }
        for(int i = 0; i <numberOfCategories(); i++){
            MintermCategory mintermCategory = mintermCategories.get(i);
            for(Minterm minterm : mintermCategory){
                if(!minterm.isMarked()){
                    res.add(minterm);
                }
            }
        }

        if(res.size()==0){
            lastTurn=true;
        }
        return res;
    }

}

