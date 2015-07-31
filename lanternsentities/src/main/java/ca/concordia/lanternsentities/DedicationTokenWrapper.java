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
        for (int i = 0; i < stack.size(); i++) {
            if (i != 0) {
                sb.append(" ");
            }
            sb.append(stack.get(i));
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
