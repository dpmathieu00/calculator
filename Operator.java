//this class defines what an operator means in the context of a calculator, which includes the inherent order of operations that they are beholden to
//by assigning them groups
public class Operator 
{
    private String operator;
    private int level;

    public Operator(String operator)
    {
        this.operator = operator;
        if(operator.equals("+") || operator.equals("-")) level = 3;
        if(operator.equals("*") || operator.equals("/")) level = 2;
        if(operator.equals("^")) level = 1;
    }

    public int getLevel()
    {
        return level;
    }

    public String getOperator()
    {
        return operator;
    }

    public int order()
    {
        return Math.abs(3 - level);
    }
}