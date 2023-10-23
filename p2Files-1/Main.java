import fa.nfa.NFA;

import java.util.Set;

public class Main {

    public static void main(String[] args) {
        NFA nfa = new NFA();

        nfa.addSigma('0');
        nfa.addSigma('1');

        nfa.addState("a");
        nfa.setStart("a");
        nfa.addState("b");
        nfa.setFinal("b");

        nfa.addTransition("a", Set.of("a"), '0');
        nfa.addTransition("a", Set.of("b"), '1');
        nfa.addTransition("b", Set.of("a"), 'e');

        int ret = nfa.maxCopies("1");

        System.out.println(ret);
    }
}
