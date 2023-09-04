# **Modulo Times Table Visualization**

Visualize patterns emerging from times tables using modulo arithmetic, all set on a circular path. This JavaFX application provides an interactive window to explore and manipulate the visualization in real-time.

## **Table of Contents**

- [Features](#features)
- [Setup & Running](#setup--running)
- [Usage](#usage)
- [Classes & Components](#classes--components)
- [Acknowledgements](#acknowledgements)

## **Features**

- **Interactive Controls**: Adjust the current times table number, the increment step, and delay for visualization.
- **Colors**: Customize the line color for better visualization or personal preference.
- **Jump to Specific Parameters**: Quickly switch to specific times table numbers and points.
- **Start/Stop Controls**: Control the animation and visualization in real-time.

## **Setup & Running**

1. Ensure you have **JavaFX** set up on your machine.
2. Clone the repository (you need access to lobogit gitlab):
    ```
    git clone https://lobogit.unm.edu/aabusang/modulo-times-table
    ```
3. Navigate to the project directory and compile the Java files:
    ```
    javac *.java
    ```
4. Run the `Main` class to start the application:
    ```
    java Main
    ```
5. On Intellij: Click the run button at the top right

## **Usage**

1. **Run & Stop Buttons**: Start and pause the visualization.
2. **Increment Slider**: Adjust how quickly the times table number changes.
3. **Delay Slider**: Set a delay for the visualization, allowing for slower or faster animations.
4. **Jump to Specific Parameters**: Input boxes to jump to a particular times table number and number of points.
5. **Colors**: Choose the color for the visualized lines.

## **Classes & Components**

- **`Main`**: The primary class responsible for the GUI setup, controls, and visualization.
- **`PointOnCircle`**: Represents points on a circle, used for the times table visualization. Adjusts raw coordinates using offsets to fit them in the canvas correctly.
- **`Visualization`**: A class responsible for generating the lines based on the modulo times tables. It creates the patterns that emerge when connecting points on a circle according to modulo arithmetic rules.
- **`DecimalTextVerifier`**: A utility class to verify and format decimal inputs, ensuring that the user inputs are processed correctly.

## **Acknowledgements**

Special thanks to:
- Joseph Haugh and Nicholas Livingstone for guidance and support.
- Mathologer YouTube Channel and Simon Plouffe for details on the workings of the concept.
