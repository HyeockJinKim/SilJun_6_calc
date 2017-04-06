package com.riotgames.sample;

import java.util.Arrays;

/**
 * Calculator application
 */
public class CalcApp {
	
	private static final Map<Character, Integer> basic = new HashMap<Character, Integer>();
	static {
		//priority
		basic.put('+', 1);
		basic.put('-', 1);
		basic.put('*', 2);
		basic.put('/', 2);
		basic.put('(', 0);
	}
	
	
    public double calc(String[] tokens) {
        final double firstOperand;
        final double secondOperand;
        firstOperand = Double.parseDouble(tokens[0]);
        if (tokens.length > 2) {
            secondOperand = Double.parseDouble(tokens[2]);
        } else {
            secondOperand = Double.parseDouble(tokens[1]);
        }
        final Operator operator = Operator.findOperator(tokens[1]);

        return operator.evaluate(firstOperand, secondOperand);

    }

    public static void main( String[] args ) {
        final CalcApp app = new CalcApp();
        final StringBuilder outputs = new StringBuilder();
        Arrays.asList(args).forEach(value -> outputs.append(value + " "));
        System.out.print( "Addition of values: " + outputs + " = ");
        System.out.println(app.calc(args));
    }
}
