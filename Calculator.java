import java.util.*;

//This class is responsible for all internal logic and is completely separate from all visual implementation of the results this class produces
public class Calculator 
{
    //This is the main function which is triggered upon a user asking for a result
    public static float performCalc(String equation)
    {
        //Obtain the next innermost parenthesized portion of an equation
        //Replace that portion with it's result from performCalcWithOrder and loop until all parenthesized portions have been manipulated
        //Make on elast call to performCalcWithOrder and return that result in this method

        List<String> splitEquation = new ArrayList<String>(Arrays.asList(equation.split(" ")));

        if(equation.contains("("))
        {
            if(splitEquation.size() > 1)
            {
                int index;
                String trailingOperation;
                String leadingOperation;

                do
                {
                    //innerEquation represents a list of the current parenthesized portion of the equation being processed
                    List<String> innerEquation = new ArrayList<String>();
                    int lastRPareIndex = 0;
                    int firstLPareIndex = 0;
                    Boolean insideParantheses = false;
                    trailingOperation = "";
                    leadingOperation = "";

                    //loops backwards through the list, populating innerEquation everytime it finds itself within a parenthesized portion and calling performCalcWithOrder when it
                    //determines the innermost portion, which is then replaced with the result
                    for(index = splitEquation.size() - 1; index > -1; index--)
                    {
                        if(splitEquation.get(index).contains(")"))
                        {
                            lastRPareIndex = index;
                            insideParantheses = true;
                            //everytime a new closed parentheses is found it marks the beginning of a new innermost equation
                            innerEquation.clear();
                            trailingOperation = getTrailingData(splitEquation.get(index), ")");
                        }
                        if(splitEquation.get(index).contains("("))
                        {
                            firstLPareIndex = index;
                            leadingOperation = getLeadingData(splitEquation.get(index), "(");

                            //the order of these is imperative as replacing all open parentheses can make it impossible to actually locate and replace the leadingData
                            splitEquation.set(index, splitEquation.get(index).replace(leadingOperation, ""));
                            splitEquation.set(index, splitEquation.get(index).replaceAll("\\(", ""));
                        
                            if(firstLPareIndex == lastRPareIndex) splitEquation.set(index, splitEquation.get(index).replaceAll("\\)", ""));

                            innerEquation.addFirst(splitEquation.get(index));

                            Float result = performCalcWithOrder(innerEquation);
                            String innerResult = leadingOperation + result + trailingOperation;

                            splitEquation.set(firstLPareIndex, innerResult);

                            //loops through and removes the parenthesized portion of the equation that has been calculated
                            for(int i = lastRPareIndex; i > firstLPareIndex; i--)
                            {
                                splitEquation.remove(i);
                            }

                            break;
                        }
                        if(insideParantheses == true)
                        {              
                            innerEquation.addFirst(splitEquation.get(index).replaceAll("\\)", ""));
                        }
                    }

                } while(index > 0 || (!trailingOperation.isEmpty() || !leadingOperation.isEmpty()));
                //if the index has reached 0 and there are no remaining operations/parentheses to account for then the function can move on
            }
            else
            { 
                //if the equation is of length one, then the parentheses involved are no longer of consequence so they are recomed before 
                splitEquation.set(0, splitEquation.get(0).replaceAll("\\)", ""));
                splitEquation.set(0, splitEquation.get(0).replaceAll("\\(", ""));
            }
        }

        return performCalcWithOrder(splitEquation);
    }


    //performs a calculation that takes into account the order of operations, ignoring parentheses
    private static float performCalcWithOrder(List<String> equation)
    {
        List<String> split = equation;
        if(split.size() == 1) return parse(split.getFirst());
        ArrayList<Operator> ex = orderOperands(split);
        float result = 0;
        
        Iterator<Operator> order = ex.iterator();
        Operator listOp = order.next();

        do
        {
            ArrayList<String> updatedEquation = new ArrayList<String>();
            updatedEquation.addFirst(split.getLast());

            for(int i = split.size() - 2; i > 0; i-=2)
            {
                Operator equationOp = new Operator(split.get(i));

                //compares operators on the basis of level so that no operator is skipped based on it's placement in the list
                if(listOp.getLevel() == equationOp.getLevel())
                {
                    if(split.get(i).equals("+")) result = add(parse(split.get(i - 1)), parse(updatedEquation.getFirst()));
                    if(split.get(i).equals("-")) result = subtract(parse(split.get(i - 1)), parse(updatedEquation.getFirst()));
                    if(split.get(i).equals("*")) result = multiply(parse(split.get(i - 1)), parse(updatedEquation.getFirst()));
                    if(split.get(i).equals("/")) result = divide(parse(split.get(i - 1)), parse(updatedEquation.getFirst()));
                    if(split.get(i).equals("^")) result = exponentiate(parse(split.get(i - 1)), parse(updatedEquation.getFirst()));

                    updatedEquation.removeFirst();
                    updatedEquation.addFirst(result + "");

                    if(!order.hasNext())
                    {
                        break;
                    }
                    else listOp = order.next();
                }
                else
                {
                    updatedEquation.addFirst(split.get(i));
                    updatedEquation.addFirst(split.get(i - 1));
                }
            }

            split = updatedEquation;
        } while(split.size() > 1);
        //effectively, if the size of the equation has been reduced down to a single number, the result has been fully calculated, so there is no longer a need to loop

        return result;
    }

    //collect an arrayList of only the operands in an equation using an object, Operator, which holds information on the operand itself and it's relative order
    private static ArrayList<Operator> orderOperands(List<String> split)
    {
        ArrayList<Operator> operands = new ArrayList<Operator>();

        for(int i = 1; i < split.size(); i+= 2)
        {
            Operator op = new Operator(split.get(i));
            operands.add(op);
        }

        Collections.sort(operands, new SortByLevel());

        return operands;
    }

    //obtains and returns all data that occurs in a string after a specified delimiter
    private static String getTrailingData(String str, String delimiter)
    {
        String trailingData = "";
        Boolean hitDelimiter = false;

        for(int i = 0; i < str.length(); i++)
        {
            if(hitDelimiter == true) trailingData = trailingData + str.substring(i, i + 1);
            if(str.substring(i, i + 1).equals(")")) hitDelimiter = true;
        }

        return trailingData;
    }

    //obtains and returns all data that occurs in a string before a specified delimiter
    private static String getLeadingData(String str, String delimiter)
    {
        String leadingData = "";
        Boolean hitDelimiter = false;

        for(int i = str.length() - 1; i > -1; i--)
        {
            if(hitDelimiter == true) leadingData = str.substring(i, i + 1) + leadingData;
            if(str.substring(i, i + 1).equals("(")) hitDelimiter = true;
        }

        return leadingData;
    }

    //converts any string representation of a number into a float, and IF that number is undergoing a sqrt operation, it performs it before parsing
    private static float parse(String a)
    {
        if(a.substring(0, 1).equals("√")) return root(Float.parseFloat(a.substring(1, a.length())));
        else return Float.parseFloat(a);
    }

    //returns the result of adding two float numbers
    private static float add(float a, float b)
    {
        return (a + b);
    }

    //returns the result of subtracting two float numbers, taking into account the order in which they were written by the user
    private static float subtract(float a, float b)
    {
        return (a - b);
    }

    //returns the result of multiplying two float numbers
    private static float multiply(float a, float b)
    {
        return (a * b);
    }

    //returns the result of dividing two float numbers
    private static float divide(float a, float b)
    {
        return (a / b);
    }   

    //returns the result of exponentiating two float numbers
    private static float exponentiate(float a, float b)
    {
        return (float) Math.pow(a, b);
    }

    //returns the result of performing a square root operation on a float number
    private static float root(float a)
    {
        return (float) Math.sqrt(a);
    }
}
