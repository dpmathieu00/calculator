# Calculator

This is a Java-based application that uses the JavaFX framework to provide the capabilities of a standard calculator.

  ![til](https://raw.githubusercontent.com/dpmathieu00/calculator/master/Assets/demo2.gif)

## Features
  This Calculator provides an implementation for each major function: addition, subtraction, multiplication, division, exponentiation, and square roots. The user can also specify how the calculator will process their input using parentheses, otherwise the default follows the standardized order of operations.
  
  Using JavaFX, the display transfers earlier portions of an equation being input to another portion of the screen to avoid clutter and increase visibility.

  Using logical operations in the code, the calculator understands which inputs are legal for a user to make at any any given point and will only ever allow legal inputs to be displayed and processed on the screen.

  To increase flow, inputting any function after a result has been calculated and displayed on the screen will begin a new equation using that result as the first term.
