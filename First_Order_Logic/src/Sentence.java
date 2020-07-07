import java.util.*;
public class Sentence {
	String sentence;
	public Sentence(String sentence)
	{
		this.sentence = sentence;
	}
	
	public static HashMap<Predicate, List<Predicate>> hashKB (String[] kbArray, String query) {
		HashMap<Predicate, List<Predicate>> kbHashMap = new HashMap<Predicate,List<Predicate>>();
		for (String kbSentences : kbArray) {
			//calling convert to cnf function
			//Storing all predicates in an arrayList
			ArrayList<Predicate> cnfSentence = convertToCNF(kbSentences);
			//for every predicate in arraylist 
			//add values in hashMap
			//key will be every predicare and value will be entire cnf converted sentence
			for (Predicate predicate : cnfSentence) {
				kbHashMap.put(predicate, cnfSentence);
			}
		}
		if (query.charAt(0) == '~') {
		Predicate queryPred = new Predicate(query.substring(1,query.indexOf("(")) , query.substring(query.indexOf("(")+1, query.indexOf(")")).split(","));
		unification(kbHashMap,queryPred);
		} else {
			Predicate queryPred = new Predicate('~' + query.substring(0,query.indexOf("(")) , query.substring(query.indexOf("(")+1, query.indexOf(")")).split(","));
			unification(kbHashMap,queryPred);
		}
		//call unification function on the hashmap
		//unification(kbHashMap,queryPred);
		return kbHashMap;
	}
	
	
	
	
	private static void unification(HashMap<Predicate, List<Predicate>> kbHashMap, Predicate queryPred) {
		//check for each key in hashMap if it can be unified
		//if the predicate name and no of arguments are equal
		//check if unification is possible
		//if true add the resolved statement to kb 
		//remove the two sentences used for resolution from kb
		Iterator<Predicate> itr = kbHashMap.keySet().iterator();

		while (itr.hasNext()) {

			Predicate p = itr.next();

			if (queryPred.name.charAt(0) == '~') {
			if((queryPred.name).equals('~' + p.name) && (Predicate.argCount(p) == Predicate.argCount(queryPred))) {
				//check if two can be unified 

				HashMap<String,String> substitutionMap = checkUnification(queryPred, p);
				System.out.println("susbs " + substitutionMap);
				kbHashMap = unify(substitutionMap, kbHashMap.get(queryPred), kbHashMap.get(p), kbHashMap, queryPred, p);
			}
			} else {
				if((queryPred.name).equals(p.name.substring(1, p.name.length())) && (Predicate.argCount(p) == Predicate.argCount(queryPred))) {
					//check if two can be unified 

					HashMap<String,String> substitutionMap = checkUnification(queryPred, p);
					System.out.println("susbs " + substitutionMap);
					kbHashMap = unify(substitutionMap, kbHashMap.get(queryPred), kbHashMap.get(p), kbHashMap, queryPred, p);
				}
				
			}
		}
	}

	private static HashMap<Predicate,List<Predicate>> unify(HashMap<String, String> substitutionMap, List<Predicate> firstPredicateList, List<Predicate> itrNextList,
			HashMap<Predicate, List<Predicate>> kbHashMap, Predicate firstPredicate, Predicate itrNext) {
		//for both the sentences substitute the unifier values
		List<Predicate> firstPredicateListNew = new ArrayList<Predicate>();
		List<Predicate> itrNextListNew = new ArrayList<Predicate>();
		for (Predicate firstPredicate1 : firstPredicateList) {
			for (int i = 0; i < firstPredicate1.arguments.length; i++) {
				if (substitutionMap.containsKey(firstPredicate1.arguments[i])) {
					firstPredicate1.arguments[i] = substitutionMap.get(firstPredicate1.arguments[i]);
				}
			}
			firstPredicateListNew.add(firstPredicate1);
		}
		//remove the firstPredicate from the firstPredicateListNew
		firstPredicateListNew = removePredicate(firstPredicateListNew, firstPredicate);
		for (Predicate itrNextPredicate : itrNextList) {
			for (int i = 0; i < itrNextPredicate.arguments.length; i++) {
				if (substitutionMap.containsKey(itrNextPredicate.arguments[i])) {
					itrNextPredicate.arguments[i] = substitutionMap.get(itrNextPredicate.arguments[i]);
				}
			}
			itrNextListNew.add(itrNextPredicate);
		}
		//remove the itrNext predicate from itrNextListNew
		itrNextListNew = removePredicate(itrNextListNew, itrNext);
		List<Predicate> completeList = new ArrayList<Predicate>();
		completeList.addAll(firstPredicateListNew);
		completeList.addAll(itrNextListNew);
		
		//check if completeList is empty then 
		if (completeList.isEmpty()) {
			System.out.println("NO");
		}
		for (Predicate predicate : completeList) {
			kbHashMap.put(predicate, completeList);
		}
		kbHashMap.remove(firstPredicate);
		kbHashMap.remove(itrNext);
		return kbHashMap;
	}

	private static List<Predicate> removePredicate(List<Predicate> predicateListNew, Predicate predi) {

		int flag = 0;
		//Add predicates to the list which do not have the same name and same arguments
		System.out.println(predicateListNew.size());
		for (Predicate pred : predicateListNew) {
			System.out.println("HERE " + pred.name);
			if(pred.name.equals(predi.name)) {
				
				for (int i = 0; i < pred.arguments.length; i++) {
					
					if(pred.arguments[i].equals(predi.arguments[i])) {
						flag = 1;
					} 
				}
			}
			
		if (flag == 1) {
			System.out.println("New size " + predicateListNew.size());
			predicateListNew.remove(pred);
			break;

			}
		}
		if (predicateListNew.isEmpty())
			System.out.println("YES");
		return predicateListNew;
	}

	private static HashMap<String,String> checkUnification(Predicate firstPredicate, Predicate next) {
		// create a substitution hashMap
		HashMap<String,String> substitutionMap = new HashMap<String,String>();
		System.out.println("firstPred "+firstPredicate.name);
		System.out.println("next "+next.name);
		for (int i = 0; i < firstPredicate.arguments.length; i++) {
		System.out.println(firstPredicate.arguments[i]);
		System.out.println(next.name + next.arguments[i]);
		}
		for (int i = 0; i < firstPredicate.arguments.length; i++) {
			//check characters at first position
			String[] subst = checkChar(firstPredicate.arguments[i], next.arguments[i]);
			substitutionMap.put(subst[0],subst[1]);	
			}
		System.out.println("substitution " + substitutionMap);
		return substitutionMap;
	}
		

	private static String[] checkChar(String charAt, String charAt2) {
		String[] oldNewVal = new String[2];
		if (charAt.equals(charAt2)) {
			oldNewVal[0] = charAt;
			oldNewVal[1] = charAt2;
		}
		if (charAt.charAt(0) > charAt2.charAt(0)) {
			oldNewVal[0] = charAt2;
			oldNewVal[1] = charAt;
		}
		if (charAt.charAt(0) < charAt2.charAt(0)) {
			oldNewVal[0] = charAt;
			oldNewVal[1] = charAt2;
		}
		return oldNewVal;
		
	}

	private static ArrayList<Predicate> convertToCNF(String kbSentences) {
		ArrayList<Predicate> cnfSentence = new ArrayList<Predicate>();
		if (kbSentences.contains("=>")) {
			String predArray[] = kbSentences.split("=>");
			cnfSentence.add(new Predicate(predArray[1].substring(0,predArray[1].indexOf("(")) , predArray[1].substring(predArray[1].indexOf("(")+1, predArray[1].indexOf(")")).split(",")));
			String predicates[] = predArray[0].split("&");
			for(int i = 0; i< predicates.length ; i++)
			{
				
				if(predicates[i].charAt(0) != '~' )
				{

					String predicateName = '~' + predicates[i].substring(0,predicates[i].indexOf("("));

					String[] predicateArguments = predicates[i].substring(predicates[i].indexOf("(")+1, predicates[i].indexOf(")")).split(",");
					Predicate kbPredicates = new Predicate(predicateName, predicateArguments);
					cnfSentence.add(kbPredicates);

				} else {

					String predicateName = predicates[i].substring(1,predicates[i].indexOf("("));
					String[] predicateArguments = predicates[i].substring(predicates[i].indexOf("(")+1, predicates[i].indexOf(")")).split(",");
					Predicate kbPredicates = new Predicate(predicateName, predicateArguments);
					cnfSentence.add(kbPredicates);

				}
				
			}
		} else {
			if(kbSentences.charAt(0) != '~' )
			{

				String predicateName = '~' + kbSentences.substring(0,kbSentences.indexOf("("));

				String[] predicateArguments = kbSentences.substring(kbSentences.indexOf("(")+1, kbSentences.indexOf(")")).split(",");
				Predicate kbPredicates = new Predicate(predicateName, predicateArguments);
				cnfSentence.add(kbPredicates);

			} else {

				String predicateName = kbSentences.substring(1,kbSentences.indexOf("("));
				String[] predicateArguments = kbSentences.substring(kbSentences.indexOf("(")+1, kbSentences.indexOf(")")).split(",");
				Predicate kbPredicates = new Predicate(predicateName, predicateArguments);
				cnfSentence.add(kbPredicates);

			}
		}
		return cnfSentence;
	}

	