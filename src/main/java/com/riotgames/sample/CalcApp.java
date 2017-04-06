package com.riotgames.sample;

import java.util.Arrays;
import java.util.regex.Pattern;

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
	
	/*
	 * 문자열 str이 숫자인지 확인하는 메소드.
	 */
	public boolean isNumeric(String str) {
		//pattern은 숫자로만 이루어진 패턴.
		Pattern pattern = Pattern.compile("[0-9]*");
		//str이 pattern에 맞는지 확인.
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches())
			return false;
		else
			return true;
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
