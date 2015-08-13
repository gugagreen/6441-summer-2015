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
 		int[] sortedGameDedications = new int[gameDedications.length - 1];
 		DedicationType[] sortedType = new DedicationType[gameDedications.length - 1];
 		
 		int key;
 		int j;
 		sortedGameDedications[0] = gameDedications[0].getStack().peek().getTokenValue();
 		sortedType[0] = gameDedications[0].getStack().peek().getTokenType();
 		
 		for (int i = 1; i != gameDedications.length - 1; ++i){
 			DedicationToken top = gameDedications[i].getStack().peek();
 				key = top.getTokenValue();
 				
 				j = i - 1;
 				while (j != -1 && sortedGameDedications[j] < key){
 					sortedGameDedications[j+1] = sortedGameDedications[j];
 					sortedType[j+1] = sortedType[j];
 					--j ;
 				}
 				sortedGameDedications[j+1] = key;
 				sortedType[j+1] = top.getTokenType();
 		}
 		
 		
 		return sortedType;	
 	}
}
