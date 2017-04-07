package Calcu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
//babo
public class CalcApp {
	
	private static final Map<Character, Integer> basic = new HashMap<Character, Integer>();
	static{
		basic.put('-', 1);
		basic.put('+', 1);
		basic.put('*', 2);
		basic.put('/', 2);
		basic.put('(', 0);
	}
	
	

	public double calc(String[] tokens){
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < tokens.length; i++) {
			
			if(isNumeric(tokens[i])){
				sb.append(tokens[i]);
			}
			else if(tokens[i].equals("+") || tokens[i].equals("-") || tokens[i].equals("*") || tokens[i].equals("/")
			|| tokens[i].equals("(") || tokens[i].equals(")")){
				sb.append(tokens[i]);
			}
		}
		String str = toSuffix(sb.toString());
		
		return Double.parseDouble(dealEquation(str));
	}
	
	public boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if(!isNum.matches()){
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		final CalcApp app = new CalcApp();
		final StringBuilder outputs = new StringBuilder();
		Arrays.asList(args).forEach(value -> outputs.append(value + " "));
		System.out.println("Addition of values: " + outputs + " = ");
		System.out.println(app.calc(args));
	}
	
	public String toSuffix(String infix){
		// queue��  ���� ���� ��� ����.
		List<String> queue = new ArrayList<String>();
		//����� �ҷ��� �ҋ� ��ȣ�� ����ִ´�.
		List<Character> stack = new ArrayList<Character>();
		// ���ڿ� ��ȣ�� ���� �߶󳽴�.
		char[] charArr = infix.trim().toCharArray();
		// ���� ǥ�ؿ��� ����.
		String standard = "*/+-()";
		
		char ch = '&'; // �ƹ� �ǹ� ���� ��ȣ  �׳�  �ʱ�ȭ �ϱ� ���� ����
		//char��  ���̸� ����Ѵ�.
		int len = 0;
		// ��������  ���������� ��ȭ�ϴ� ����
		
		for (int i = 0; i < charArr.length; i++) {
			ch = charArr[i]; // ���� �����͸� ����
			
			if(Character.isDigit(ch)){ // ���ڸ�
				len++;
			}
			else if(Character.isLetter(ch)){ // ���ڸ�
				len++;
			}
			else if(ch == '.'){ // �Ҽ��� �̸�
				len++;
			}
			else if(Character.isSpaceChar(ch)){
				/*
				 *  space�̸� �� �ܶ��� �������� ����
				 *  ��  100 * 2 100�ڿ�  �����̽��� �ִٴ� ���� ���ϱ� ������ space������  ����
				 */
				if(len > 0){
					// queue �� �߰�
					queue.add(String.valueOf(Arrays.copyOfRange(charArr, i - len, i)));
					len = 0;
				}
				continue; //  space��  ������  
			}
			else if(standard.indexOf(ch) != -1){ // ���� ������ ǥ������ ������ �ϳ��̸�
				if(len > 0){
					// ���ڶ� �Ǵ��ϰ� queue�� ����ֱ�
					queue.add(String.valueOf(Arrays.copyOfRange(charArr, i -len, i)));
					len = 0;
				}
				if(ch == '('){ // �����ʰ�ȣ �̸� stack�� �ֱ�
					stack.add(ch);
					continue;
				}
				if(!stack.isEmpty()){ // stack �ȿ�  ���� �ִٸ�
					int size = stack.size()-1; // �� ������ ������ index
					boolean flag = false; 
					// ch �� ) �̸� stack ���� ( ������ ����  ������.
					while(size >=0 && ch ==')' && stack.get(size) != '('){
						// �������� �ٽ� queue�� ���� �ֱ�
						queue.add(String.valueOf(stack.remove(size)));
						// ������ stack��  ����⸦ ����ġ����( stack�� ����)
						size--;
						// true �̸� ( ) ������ ���Ҹ� ��� ������ ����
						flag = true;
					}
					// ( ) ���Ұ� �ƴϸ�  ������ �켱 ���Ǹ� ����  stack ���� ������  queue�� ���� �ִ´�.
					while(size >= 0 && !flag && basic.get(stack.get(size)) >= basic.get(ch)){
						queue.add(String.valueOf(stack.remove(size)));
						size--;
					}
				}
				if(ch != ')'){ // ) �ƴϸ�  stack��  ����־�� �Ѵ�.
					stack.add(ch);
				}
				else{ // �ƴϸ� �⵿
					stack.remove(stack.size() - 1);
				}
				
			}
			if(i == charArr.length -1){ // ���� ǥ������  �� ���� ���� ����
				if(len > 0){
					// ���ڸ� �߶� ���� �ִ´�.
					queue.add(String.valueOf(Arrays.copyOfRange(charArr, i-len+1, i+1)));
				}
				int size = stack.size()-1; // �� ������ index
				// ��� stack�� ��ȣ�� �⵿���� queue�� �ֱ�
				while(size >= 0){
					queue.add(String.valueOf(stack.remove(size)));
					size --;
				}
			}
		}
		// space�� �⵿
		return queue.stream().collect(Collectors.joining(","));
		
	}

	public String dealEquation(String equation){
		  String [] arr = equation.split(",");                                    
		  List<String> list = new ArrayList<String>();                            
		         
		  for (int i = 0; i < arr.length; i++) {
		        int size = list.size();
		        switch (arr[i]) {
		           case "+":
		        	   Operator operator = Operator.findOperator(arr[i]);
		        	   double a = operator.evaluate(Double.parseDouble(list.remove(size-2)), Double.parseDouble(list.remove(size-2)));
		        	    //= Double.parseDouble(list.remove(size-2))+ Double.parseDouble(list.remove(size-2)); 
		        	   list.add(String.valueOf(a));     
		        	   break;
		           case "-": 
		        	   Operator operator2 = Operator.findOperator(arr[i]);
		        	   double b = operator2.evaluate(Double.parseDouble(list.remove(size-2)), Double.parseDouble(list.remove(size-2)));
		        	  // double b = Double.parseDouble(list.remove(size-2))- Double.parseDouble(list.remove(size-2)); 
		        	   list.add(String.valueOf(b));     
		        	   break;
		           case "*":
		        	   Operator operator3 = Operator.findOperator(arr[i]);
		        	   double c = operator3.evaluate(Double.parseDouble(list.remove(size-2)), Double.parseDouble(list.remove(size-2)));
		        	   //double c = Double.parseDouble(list.remove(size-2))* 
		        	   //Double.parseDouble(list.remove(size-2)); list.add(String.valueOf(c));     
		        	   break;
		           case "/":
		        	   Operator operator4 = Operator.findOperator(arr[i]);
		        	   double d = operator4.evaluate(Double.parseDouble(list.remove(size-2)), Double.parseDouble(list.remove(size-2)));
		        	   //double d = Double.parseDouble(list.remove(size-2))/ Double.parseDouble(list.remove(size-2)); 
		        	   list.add(String.valueOf(d));     
		        	   break;
		           default: 
		        	   list.add(arr[i]);     
		        	   break;                                    
		         }
		  }
		         
		  return list.size() == 1 ? list.get(0) : "������" ;
	}
}
