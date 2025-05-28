package com.example.unitconverterapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    //Set initial values for each input and output element
    Spinner sourceUnitSpinner;
    Spinner destinationUnitSpinner;
    public EditText valueInput;
    public TextView unitInput;
    public TextView valueOutput;
    public TextView unitOutput;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Set up the spinners with the string arrays from strings.xml
        sourceUnitSpinner = findViewById(R.id.spinnerSource);
        ArrayAdapter<CharSequence> adapterSource = ArrayAdapter.createFromResource(this,R.array.units, android.R.layout.simple_spinner_dropdown_item);
        adapterSource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceUnitSpinner.setAdapter(adapterSource);

        destinationUnitSpinner = findViewById(R.id.spinnerDestination);
        ArrayAdapter<CharSequence> adapterDestination = ArrayAdapter.createFromResource(this,R.array.units, android.R.layout.simple_spinner_dropdown_item);
        adapterDestination.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destinationUnitSpinner.setAdapter(adapterDestination);

        //Assign each declared variable to the relevant element on the layout
        valueInput = findViewById(R.id.editTextInput);
        unitInput = findViewById(R.id.textSourceUnit);
        valueOutput = findViewById(R.id.textOutput);
        unitOutput = findViewById(R.id.textDestinationUnit);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        //Tell the submit button to run the function
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitClicked(view);
            }
        });
    }

    public void submitClicked(View view) {
        //Pull the selected values and set them as temporary variables
        String submittedUnitInput = sourceUnitSpinner.getSelectedItem().toString();
        String submittedUnitOutput = destinationUnitSpinner.getSelectedItem().toString();
        String submittedValueInput = valueInput.getText().toString();

        //Set input/output units to the values selected in the spinners
        unitInput.setText(submittedUnitInput);
        unitOutput.setText(submittedUnitOutput);

        //Read the input value as a double so that it can be used in equations
        double inputValue = Double.parseDouble(submittedValueInput);
        double inputValueConverted = 0;
        double outputValueConverted = 0;

        //If statements to ensure that the input and output units are of the same type
        if (submittedUnitInput.matches("Inches|Feet|Yards|Miles") && submittedUnitOutput.matches("Inches|Feet|Yards|Miles")) {
            //Depending on the input unit, convert from the selected unit to centimetres
            switch(submittedUnitInput) {
                case "Inches":
                    inputValueConverted = inputValue*2.54;
                    break;
                case "Feet":
                    inputValueConverted = inputValue*30.48;
                    break;
                case "Yards":
                    inputValueConverted = inputValue*91.44;
                    break;
                case "Miles":
                    inputValueConverted = inputValue*160934;
                    break;
            }
            //Depending on the output unit, convert from centimetres to the selected unit
            switch(submittedUnitOutput) {
                case "Inches":
                    outputValueConverted = inputValueConverted/2.54;
                    break;
                case "Feet":
                    outputValueConverted = inputValueConverted/30.48;
                    break;
                case "Yards":
                    outputValueConverted = inputValueConverted/91.44;
                    break;
                case "Miles":
                    outputValueConverted = inputValueConverted/160934;
                    break;
            }
            //Set the output text to the calculated value
            valueOutput.setText(Double.toString(outputValueConverted));

        } else if (submittedUnitInput.matches("Pounds|Ounces|Tons") && submittedUnitOutput.matches("Pounds|Ounces|Tons")) {
            //Depending on the input unit, convert from the selected unit to kilograms
            switch(submittedUnitInput) {
                case "Pounds":
                    inputValueConverted = inputValue*0.453592;
                    break;
                case "Ounces":
                    inputValueConverted = inputValue*0.0283495;
                    break;
                case "Tons":
                    inputValueConverted = inputValue*907.185;
                    break;
            }
            //Depending on the output unit, convert from kilograms to the selected unit
            switch(submittedUnitOutput) {
                case "Pounds":
                    outputValueConverted = inputValueConverted/0.453592;
                    break;
                case "Ounces":
                    outputValueConverted = inputValueConverted/0.0283495;
                    break;
                case "Tons":
                    outputValueConverted = inputValueConverted/907.185;
                    break;
            }
            //Set the output text to the calculated value
            valueOutput.setText(Double.toString(outputValueConverted));

        } else if (submittedUnitInput.matches("Celsius|Fahrenheit|Kelvin") && submittedUnitOutput.matches("Celsius|Fahrenheit|Kelvin")) {
            //Depending on the input unit, convert from the selected unit to celsius
            switch(submittedUnitInput) {
                case "Celsius":
                    inputValueConverted = inputValue;
                    break;
                case "Fahrenheit":
                    inputValueConverted = (inputValue-32)/1.8;
                    break;
                case "Kelvin":
                    inputValueConverted = inputValue-273.15;
                    break;
            }
            //Depending on the output unit, convert from celsius to the selected unit
            switch(submittedUnitOutput) {
                case "Celsius":
                    outputValueConverted = inputValueConverted;
                    break;
                case "Fahrenheit":
                    outputValueConverted = (inputValueConverted*1.8)+32;
                    break;
                case "Kelvin":
                    outputValueConverted = inputValueConverted+273.15;
                    break;
            }
            //Set the output text to the calculated value
            valueOutput.setText(Double.toString(outputValueConverted));

        } else {
            //Else, if the units are incompatible, show an error message
            unitInput.setText("");
            unitOutput.setText("");
            valueOutput.setText("Error! Wrong unit type.");
        }
    }
}