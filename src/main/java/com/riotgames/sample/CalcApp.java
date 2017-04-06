package com.riotgames.sample;

import java.util.Arrays;
import java.util.Stack;

/**
 * Calculator application
 */
public class CalcApp {
	double z;
	
	private boolean isAnOperator(String s){
 		return(s.length()==1&&"+-/*".indexOf(s)>=0);
 	}
	
    public double calc(String[] tokens) {
        double firstOperand;
        double secondOperand;
        
        Stack stack=new Stack();//문자배열의 길이만큼 생성
        int num=0;
		
		String[]output=new String[tokens.length];//후위식출력위해만든배열
		for(int i=0;i<tokens.length;i++){
			int size2=stack.size();
			if(tokens[i].equals("("))
				stack.push((Object)tokens[i]);
			else if(tokens[i].equals(")")){// )를 만난다면
				while(size2!=0){
					
					if(stack.peek().toString().equals("("))
						stack.pop();
					else
						output[num++]=(String)stack.pop();
					size2--;				
				}	
			}
			else if((stack.size()==0||stack.peek().toString().equals("("))&&"+-/*".indexOf(tokens[i])>=0)// 연산자이고 stack에 아무것도 없거나 ( 가 저장돼 있다면
				stack.push((Object)tokens[i]);//stack에 넣는다			
				
			else if("+-/*".indexOf(tokens[i])<0)	//피연산자일경우				
					output[num++]=tokens[i];	
			
			else if(stack.peek().toString().equals("*")||stack.peek().toString().equals("/")){// */가 저장되어있다면
				output[num++]=(String)stack.pop();//저장된 연산자를 빼내고
				stack.push((Object)tokens[i]);//넣는다
			}
			else if("/*+-".indexOf(stack.peek().toString())>1)//+-저장되어있고
					if("+-/*".indexOf(tokens[i])>1)//연산자가 / *이라면
						stack.push((Object)tokens[i]);	// pop()시키지 않고 stack에 연산자를 추가시킨다. 				
			
			else if("/*+-".indexOf(stack.peek().toString())>1)//+-저장되어있고
					if(tokens[i].equals("+")||tokens[i].equals("-")){//연산자가 + - 이라면
						output[num++]=(String)stack.pop();//저장된 연산자를 빼내고
						stack.push((Object)tokens[i]);//넣는다 	
					}				
			
		}
		int size1=stack.size();//num과 같다
		String []result=new String[num+size1];		
		for(int i=0;i<num;i++){
			//output에 있는 결과값 출력
				result[i]=output[i];
				//System.out.print(result[i]+" ");							
				
		}
		int i=num;
		while(size1!=0){
			result[i]=(String)stack.pop();
			//System.out.print(result[i]+" ");
			i++;size1--;
		}
		
		//System.out.println();
		for(int i2=0;i2<result.length;i2++){
			String input=result[i2];//첫번째문자부터대입
			if(isAnOperator(input)){//input이 연산자일 경우 실행된다.
				firstOperand=Double.parseDouble((String)stack.pop());//제거시킨원소를double형으로 변환하여 y에 대입
				
				secondOperand=Double.parseDouble((String)stack.pop());//연산자와 만나 stack에서 두개의 피연산자를 꺼내는 과정
				
				final Operator operator = Operator.findOperator(input);

				z=operator.evaluate(secondOperand,firstOperand);
				stack.push(""+z);		
				
			}
			else stack.push(input);
				 
		}//for

		return z;
        
      /*  firstOperand = Double.parseDouble(tokens[0]);
        if (tokens.length > 2) {
            secondOperand = Double.parseDouble(tokens[2]);
        } else {
            secondOperand = Double.parseDouble(tokens[1]);
        }
        final Operator operator = Operator.findOperator(tokens[1]);

        return operator.evaluate(firstOperand, secondOperand);
*/       
    }

    public static void main( String[] args ) {
        final CalcApp app = new CalcApp();
        final StringBuilder outputs = new StringBuilder();
        Arrays.asList(args).forEach(value -> outputs.append(value + " "));
        System.out.print( "Addition of values: " + outputs + " = ");
        System.out.println(app.calc(args));
    }
}
