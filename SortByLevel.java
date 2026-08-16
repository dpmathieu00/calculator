import java.util.Comparator;

//comparator that allows operator objects that are initialized to be organized properly by their relative order of operations
public class SortByLevel implements Comparator<Operator>
{

    @Override
    public int compare(Operator a, Operator b) 
    {
        return a.getLevel() - b.getLevel();
    }
    
}
