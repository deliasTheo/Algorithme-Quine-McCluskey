import delias.mcCluskey.CategoryManager;
import delias.mcCluskey.Minterm;
import delias.mcCluskey.MintermCategory;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Minterm minterm4 = new Minterm(4, 4);
        Minterm minterm2 = new Minterm(2, 4);

        Minterm minterm3 = new Minterm(3, 4);
        Minterm minterm6 = new Minterm(6, 4);
        System.out.println("Minterne de depart : " + minterm4.toString() + ", " + minterm2.toString() + ", " + minterm3.toString() + ", " + minterm6.toString());

        MintermCategory MC1 = new MintermCategory(minterm4, minterm2);
        MintermCategory MC2 = new MintermCategory(minterm6, minterm3);
        System.out.println("Merge de catégory : " + MC1.merge(MC2).toString());

        List<Minterm> list = new ArrayList<>();
        list.add(new Minterm(-1, 1, 0));
        list.add(new Minterm(1, -1, 0));
        list.add(new Minterm(0, 0, -1));
        list.add(new Minterm(-1, 0, 0));

        CategoryManager cm = new CategoryManager(list, 3);
        System.out.println("Create category : " + cm.mintermCategories.toString());
        List<Minterm> res = cm.mergeCategories();
        System.out.println("Merge category : " + res.toString());
        System.out.println("Size merged category : " + res.size());


    }
}