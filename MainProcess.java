import java.util.Iterator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;

public class MainProcess
{
    @FXML Pane container;
    @FXML Pane numberZero;
    @FXML Pane numberOne;  
    @FXML Pane numberTwo;
    @FXML Pane numberThree;
    @FXML Pane numberFour;
    @FXML Pane numberFive;
    @FXML Pane numberSix;
    @FXML Pane numberSeven;
    @FXML Pane numberEight;  
    @FXML Pane numberNine;
    @FXML Pane period;
    @FXML Pane add;
    @FXML Pane subtract;
    @FXML Pane multiply;
    @FXML Pane divide;
    @FXML Pane power;
    @FXML Pane root;
    @FXML Pane result;
    @FXML Pane clear;
    @FXML Label display;
    @FXML Label equation;
    @FXML Pane leftPare;
    @FXML Pane rightPare;
    Boolean operandPressed = false;
    Boolean zeroPressed = false;
    boolean periodPressed = false;
    boolean equalsPressed = false;
    int leftParePresses = 0;
    int rightParePresses = 0;
    Boolean leftParePressed = false;
    Boolean rightParePressed = false;
    Boolean numberPressed = false;

    //TODO: Implement keyboard input compatability
    //a no-arg initialize method is automatically detected by the loader so that it knows to call it without directly being "invoked"
    public void initialize()
    {
        clear.setOnMouseClicked(event -> 
        {
            display.setText("");
            equation.setText("");
            operandPressed = false;
            zeroPressed = false;
            equalsPressed = false;
            leftParePresses = 0;
            rightParePresses = 0;
            numberPressed = false;
            leftParePressed = false;
            rightParePressed = false;
        });
        numberZero.setOnMouseClicked(event -> {setDisplay("0");});
        numberOne.setOnMouseClicked(event -> {setDisplay("1");});
        numberTwo.setOnMouseClicked(event -> {setDisplay("2"); });
        numberThree.setOnMouseClicked(event -> {setDisplay("3");});
        numberFour.setOnMouseClicked(event -> {setDisplay("4");});
        numberFive.setOnMouseClicked(event -> {setDisplay("5");});
        numberSix.setOnMouseClicked(event -> {setDisplay("6");});
        numberSeven.setOnMouseClicked(event -> {setDisplay("7");});
        numberEight.setOnMouseClicked(event -> {setDisplay("8");});
        numberNine.setOnMouseClicked(event -> {setDisplay("9");});
        period.setOnMouseClicked(event -> {setDisplay(".");});

        add.setOnMouseClicked(event -> {setOperand("+");});
        subtract.setOnMouseClicked(event -> 
        {
            if(operandPressed == true|| leftParePressed == true) setDisplay("-");
            else setOperand("-");
                
        });
        multiply.setOnMouseClicked(event -> {setOperand("*");});
        divide.setOnMouseClicked(event -> {setOperand("/");});
        power.setOnMouseClicked(event -> {setOperand("^");});
        root.setOnMouseClicked(event -> {setDisplay("√");});

        leftPare.setOnMouseClicked(event ->
        {
            setParentheses("(");
        });
        rightPare.setOnMouseClicked(event ->
        {
            if(rightParePresses < leftParePresses)
            {
                setParentheses(")");
            }
        });

        result.setOnMouseClicked(event -> 
        {
            //allows the last result to be inherited without disabling equals functionality, essentially just reiterating that itself is equal to itself
            //also provides implementation for the edge case of computating a single sqrt operation
            if(equation.getText().isEmpty() && display.getText().isEmpty())
            {
                //show error
            }
            else if(equalsPressed == true || equation.getText().isEmpty())
            {
                float product = Calculator.performCalc(display.getText());
                equation.setText(display.getText() + " = ");

                display.setText(Float.toString(product));

                equalsPressed = true;
            }
            else if(!equation.getText().isEmpty())
            {
                float product = Calculator.performCalc(equation.getText() + display.getText());
                equation.setText(equation.getText() + display.getText() + " = ");

                display.setText(Float.toString(product));

                equalsPressed = true;
            }
        });


        ObservableList<Node> a = container.getChildren();
        Iterator itr = a.iterator();
        
        while(itr.hasNext())
        {
            Pane temp = (Pane) itr.next();
            temp.setOnMousePressed(event ->
            {
                ColorAdjust depressed = new ColorAdjust();
                depressed.setBrightness(-0.15);

                temp.setEffect(depressed);
            });
            temp.setOnMouseReleased(event -> 
            {
                ColorAdjust released = new ColorAdjust();
                released.setBrightness(0);

                temp.setEffect(released);
            });
            temp.setOnMouseEntered(event ->
            {
                Glow glow = new Glow();
                glow.setLevel(0.3);

                temp.setEffect(glow);
            });
            temp.setOnMouseExited(event ->
            {
                Glow glow = new Glow();
                glow.setLevel(0);

                temp.setEffect(glow);
            });
        }
    }

    //sets the bottom label in the calculator interface with text
    private void setDisplay(String symbol)
    {
        if(rightParePressed == true) return;

        leftParePressed = false;
        rightParePressed = false;

        if(!symbol.equals("-") && !symbol.equals("√"))
        {
            //can only be a number and dot which is effectively an extension of a number
            numberPressed = true;
        }
        else numberPressed = false;


        if(equalsPressed == true)
        {
            display.setText("");
            equation.setText("");
            equalsPressed = false;
        }
        if(operandPressed == true)
        {
            display.setText(symbol);
            operandPressed = false;
        }
        else if(!symbol.equals("."))
        {
            if(symbol.equals("0"))
            {
                if(display.getText().isEmpty())
                {
                    display.setText(symbol);
                }
                else if(!display.getText().toString().substring(0, 1).equals("0"))
                {
                    display.setText(display.getText() + symbol);
                }
                else if(periodPressed == true)
                {
                    display.setText(display.getText() + symbol);
                }
            }
            else
            {
                if(display.getText().isEmpty())
                {
                    display.setText(symbol);
                }
                else if(!display.getText().toString().substring(0, 1).equals("0"))
                {
                    display.setText(display.getText() + symbol);
                }
                else if(periodPressed == true)
                {
                    display.setText(display.getText() + symbol);
                }
            }
        }
        else
        {
            if(display.getText().isEmpty())
            {
                periodPressed = true;
                display.setText("0" + symbol);
            }
            else if(!display.getText().toString().contains("."))
            {
                periodPressed = true;
                display.setText(display.getText() + symbol);
            }
        }
    }

    //moves a portion of the calculation while adding whatever operation that user wants to the top label to save space
    private void setOperand(String operand)
    {   if(leftParePressed == false)
        {
            rightParePressed = false;
            if(equalsPressed == true)
            {
                equation.setText(display.getText() + " " + operand + " ");
                operandPressed = true;
                equalsPressed = false;
                numberPressed = false;
            }
            else if(leftParePresses != rightParePresses)
            {
                display.setText(display.getText() + " " + operand + " ");
                numberPressed = false;
            }
            else if((!display.getText().isEmpty()) && (leftParePresses == rightParePresses))
            {
                equation.setText(equation.getText() + display.getText() + " " + operand + " ");
                operandPressed = true;
                numberPressed = false;
            }
            else if(operand.equals("-"))
            {
                //only a symbol can be input after this
            }
        }
    }

    //special method that processes the visual logic required to use parentheses
    private void setParentheses(String parentheses)
    {
        if(parentheses.equals("("))
        {
            if(numberPressed == false)
            {
                leftParePressed = true;
                leftParePresses++;
                operandPressed = false;

                if(leftParePresses > rightParePresses + 1)
                {
                    display.setText(display.getText() + parentheses);
                }
                else if(!display.getText().isEmpty() && display.getText().substring(display.getText().length() - 1).equals("√"))
                {
                    display.setText("√" + parentheses);
                } 
                else display.setText(parentheses);
            }
            
        }
        if(parentheses.equals(")"))
        {
            if(numberPressed == true)
            {
                rightParePressed = true;
                operandPressed = false;
                rightParePresses++;
                display.setText(display.getText() + parentheses);
            }
        }
    } 
}

