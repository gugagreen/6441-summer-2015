package ca.concordia.lanternsentities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ca.concordia.lanternsentities.enums.DedicationType;

import java.util.Stack;

/**
 * Dedication Token Wrapper
 *
 * @version 1.0
 */
@XmlRootElement
public class DedicationTokenWrapper {

    private Stack<DedicationToken> stack;

    public DedicationTokenWrapper() {
        this.stack = new Stack<DedicationToken>();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if(!stack.isEmpty()){
        	DedicationToken first = stack.get(0);
        	if (first != null) {
        		sb.append(first.getTokenType()+"=[" + first.getTokenValue());
        		for (int i = 1; i < stack.size(); i++) {
        			sb.append("," + stack.get(i).getTokenValue());
        		}
        		sb.append("]");
        	}
        }
        else{
        	sb.append("Empty Stack");
        }
        return "Token Stack [" + sb.toString() + "]";
    }

    @XmlElement(name = "token")
    public Stack<DedicationToken> getStack() {
        return stack;
    }

    public void setStack(Stack<DedicationToken> stack) {
        this.stack = stack;
    }
    
    /**
     *  Get a decreasingly sorted list of dedication type depending on the dedication value at the top of dedication stacks in the game.
     *  Note: Ignores generic type.
     * @param gameDedications - An array of {@link DedicationTokenWrapper} to be sorted
     * @return The sorted list of DedicationType
     */
 	public static DedicationType[] sortDedications(DedicationTokenWrapper[] gameDedications){
 		DedicationType[] sortedType = new DedicationType[gameDedications.length - 1];
 		int[] gameDedicationsValue = new int[gameDedications.length - 1];

 		DedicationType[] dType = DedicationType.values();
 		Stack<DedicationToken> dedicationStack;
 		Stack<DedicationToken> genericStack = gameDedications[gameDedications.length - 1].getStack();
 		
 		for (int i = 0; i != gameDedications.length - 1; ++i) {
 			dedicationStack = gameDedications[i].getStack();
 			if (dedicationStack.isEmpty()) {
 				if (! (genericStack.isEmpty())) {
 					gameDedicationsValue[i] = genericStack.peek().getTokenValue();
 				} else {
 					gameDedicationsValue[i] = 0;
 				}
 			} else {
 				gameDedicationsValue[i] = dedicationStack.peek().getTokenValue();
 			}
 			sortedType[i] = dType[i];
 		}
 		
 		int key;
 		int j;
 		DedicationType type;
 		
 		for (int i = 1; i != gameDedications.length - 1; ++i){
 				key = gameDedicationsValue[i];
 				type = sortedType[i];
 				
 				j = i - 1;
 				while (j != -1 && gameDedicationsValue[j] < key){
 					gameDedicationsValue[j+1] = gameDedicationsValue[j];
 					sortedType[j+1] = sortedType[j];
 					--j ;
 				}
 				gameDedicationsValue[j+1] = key;
 				sortedType[j+1] = type;
 		}
 		return sortedType;	
 	}
}
