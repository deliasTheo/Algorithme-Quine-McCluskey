package delias.mcCluskey;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MintermTest {

    @Test
    public void premierTest(){
        Minterm minterm = new Minterm(1,-1,1);
        assertEquals(-1,minterm.toIntValue());
    }

    @Test
    public void deuxiemeTest(){
        Minterm minterm = new Minterm(25,5);
        assertEquals("11001",minterm.toString());

        minterm = new Minterm(8,4);
        assertEquals("1000",minterm.toString());

        minterm = new Minterm(11,4);
        assertEquals("1011", minterm.toString());
    }

    @Test
    public void troisiemeTest(){
        Minterm minterm1 = new Minterm(1,1,0,0,1);
        Minterm minterm2 = new Minterm(1,1,0,0,0);
        Minterm res = minterm1.merge(minterm2);
        assertTrue(minterm1.isMarked());
        assertTrue(minterm2.isMarked());
        assertFalse(res.isMarked());
        assertEquals("1100-", res.toString());
    }

    @Test
    public void quatriemeTest(){
        assertEquals(1, Minterm.numberOfBitsNeeded(0));
        assertEquals(1, Minterm.numberOfBitsNeeded(1));
        assertEquals(2, Minterm.numberOfBitsNeeded(3));
        assertEquals(3, Minterm.numberOfBitsNeeded(6));
        assertEquals(4, Minterm.numberOfBitsNeeded(15));
        //assertEquals(MASK, Minterm.numberOfBitsNeeded(MASKED_NUMBER));
    }

    @Test
    public void cinquiemeTest(){
        Minterm m = new Minterm(15, 4);
        assertEquals(0, m.numberOfZero());
        assertEquals(4, m.numberOfOne());
    }

    @Test
    public void sixiemeTest(){
        Minterm minterm1 = new Minterm(5, 3);
        Minterm minterm2 = new Minterm(5, 3);
        assertEquals(minterm1, minterm2);
        minterm1.mark();
        assertEquals(minterm1, minterm2);
    }
    @Test
    public void septeurTest(){
        MintermCategory mclass = new MintermCategory();
        Minterm m1 = new Minterm(-1,1,0);
        mclass.add(m1);
        Minterm m2 = new Minterm(1,-1,0);
        mclass.add(m2);

        MintermCategory m2class = new MintermCategory();
        m2class.add(new Minterm(0,0,-1));

        List<Minterm> res = mclass.merge(m2class);
        assertEquals(2,res.size());
        assertTrue( res.contains(m1) ) ;
        assertTrue( res.contains(m2) ) ;


        m2class = new MintermCategory();
        m1 = new Minterm(0,0,-1);
        m2class.add(m1);
        mclass = new MintermCategory();
        mclass.add(new Minterm(-1,1,0));
        mclass.add(new Minterm(1,-1,0));
        res = m2class.merge(mclass);
        assertEquals(1,res.size());
        assertTrue( res.contains(m1) ) ;
        assertFalse( res.contains(m2) );
    }
    @Test
    public void huitTest(){
        List<Minterm> list = new ArrayList<>();
        list.add(new Minterm(0,0,0,0));
        list.add(new Minterm(0,0,0,1));
        list.add(new Minterm(0,0,1,0));
        list.add(new Minterm(0,0,1,1));
        list.add(new Minterm(0,1,0,0));
        list.add(new Minterm(0,1,0,1));
        list.add(new Minterm(0,1,1,0));
        list.add(new Minterm(0,1,1,1));
        list.add(new Minterm(1,0,0,0));
        list.add(new Minterm(1,0,0,1));
        list.add(new Minterm(1,0,1,0));
        list.add(new Minterm(1,0,1,1));
        list.add(new Minterm(1,1,0,0));
        list.add(new Minterm(1,1,0,1));
        list.add(new Minterm(1,1,1,0));
        list.add(new Minterm(1,1,1,1));
        int nbBits = 4;
        CategoryManager manager = new CategoryManager(list,nbBits );
        List<Minterm> res = manager.mergeCategories();
        assertEquals(16,res.size());
        //assertFalse(manager.isLastTurn());
    }

    @Test
    public void testneuf(){
        int nbBits = 3;
        List<Minterm> list = createMintermList(
                new int[]{-1, 1, 0},
                new int[]{1, -1, 0},
                new int[]{0, 0, -1},
                new int[]{-1, 0, 0});
        CategoryManager manager = new CategoryManager(list,nbBits );
        assertEquals(2,manager.getCategory(1).size());
        assertEquals(2,manager.getCategory(0).size());
        List<Minterm> res = manager.mergeCategories();
        assertEquals(3,res.size());
        assertTrue(res.contains(new Minterm(1, -1, 0)));
        assertTrue(res.contains(new Minterm(-1, -1, 0)));
        assertTrue(res.contains(new Minterm(0, 0, -1)));
    }

    private List<Minterm> createMintermList(int[] ints, int[] ints1, int[] ints2, int[] ints3) {
        List<Minterm> test = new ArrayList<>();
        test.add(new Minterm(ints));
        test.add(new Minterm(ints2));
        test.add(new Minterm(ints1));
        test.add(new Minterm(ints3));
        return test;
    }
}