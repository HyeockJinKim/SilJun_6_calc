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
		         
		  return list.size() == 1 ? list.get(0) : "계산실패" ;
	}