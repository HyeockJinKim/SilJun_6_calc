	public String toSuffix(String infix){
		// queue에  후위 식이 들어 간다.
		List<String> queue = new ArrayList<String>();
		//계산을 할려고 할떄 부호를 집어넣는다.
		List<Character> stack = new ArrayList<Character>();
		// 숫자와 부호를 전부 잘라낸다.
		char[] charArr = infix.trim().toCharArray();
		// 판정 표준오르 쓴다.
		String standard = "*/+-()";
		
		char ch = '&'; // 아무 의미 없는 부호  그냥  초기화 하기 위한 설정
		//char의  길이를 기록한다.
		int len = 0;
		// 중위식을  후위식으로 변화하는 과정
		
		for (int i = 0; i < charArr.length; i++) {
			ch = charArr[i]; // 현재 데이터를 저장
			
			if(Character.isDigit(ch)){ // 숫자면
				len++;
			}
			else if(Character.isLetter(ch)){ // 문자면
				len++;
			}
			else if(ch == '.'){ // 소수점 이면
				len++;
			}
			else if(Character.isSpaceChar(ch)){
				/*
				 *  space이면 한 단락이 끝난것을 말함
				 *  예  100 * 2 100뒤에  스페이스가 있다는 것을 말하기 때문에 space전것을  저장
				 */
				if(len > 0){
					// queue 에 추가
					queue.add(String.valueOf(Arrays.copyOfRange(charArr, i - len, i)));
					len = 0;
				}
				continue; //  space가  있으면  
			}
			else if(standard.indexOf(ch) != -1){ // 위에 정해준 표준중의 임의의 하나이면
				if(len > 0){
					// 숫자라 판단하고 queue에 집어넣기
					queue.add(String.valueOf(Arrays.copyOfRange(charArr, i -len, i)));
					len = 0;
				}
				if(ch == '('){ // 오른쪽괄호 이면 stack에 넣기
					stack.add(ch);
					continue;
				}
				if(!stack.isEmpty()){ // stack 안에  뭐가 있다면
					int size = stack.size()-1; // 맨 마지막 원소의 index
					boolean flag = false; 
					// ch 가 ) 이면 stack 에서 ( 만날때 까지  꺼낸다.
					while(size >=0 && ch ==')' && stack.get(size) != '('){
						// 꺼낸것을 다시 queue에 집어 넣기
						queue.add(String.valueOf(stack.remove(size)));
						// 영원히 stack의  꼭대기를 가리치게함( stack의 성질)
						size--;
						// true 이면 ( ) 사이의 원소를 계속 꺼냄을 지속
						flag = true;
					}
					// ( ) 원소가 아니면  연산자 우선 순의를 비교해  stack 에서 꺼내서  queue에 집어 넣는다.
					while(size >= 0 && !flag && basic.get(stack.get(size)) >= basic.get(ch)){
						queue.add(String.valueOf(stack.remove(size)));
						size--;
					}
				}
				if(ch != ')'){ // ) 아니면  stack에  집어넣어야 한다.
					stack.add(ch);
				}
				else{ // 아니면 출동
					stack.remove(stack.size() - 1);
				}
				
			}
			if(i == charArr.length -1){ // 중위 표현식의  맨 끝에 까지 오면
				if(len > 0){
					// 숫자를 잘라서 집어 넣는다.
					queue.add(String.valueOf(Arrays.copyOfRange(charArr, i-len+1, i+1)));
				}
				int size = stack.size()-1; // 맨 마지막 index
				// 모든 stack의 부호를 출동시켜 queue에 넣기
				while(size >= 0){
					queue.add(String.valueOf(stack.remove(size)));
					size --;
				}
			}
		}
		// space로 출동
		return queue.stream().collect(Collectors.joining(","));
		
	}