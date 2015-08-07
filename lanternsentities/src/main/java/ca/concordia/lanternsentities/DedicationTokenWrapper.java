package ca.concordia.lanternsentities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
        DedicationToken first = stack.get(0);
        if (first != null) {
        	sb.append(first.getTokenType()+"=[" + first.getTokenValue());
            for (int i = 1; i < stack.size(); i++) {
                sb.append("," + stack.get(i).getTokenValue());
            }
            sb.append("]");
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
}
