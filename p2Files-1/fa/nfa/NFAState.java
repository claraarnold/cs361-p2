package fa.nfa;

import fa.State;

import java.util.*;

public class NFAState extends State {

    /* instance variables */
    public LinkedHashMap<Character, LinkedHashSet<NFAState>> transitions;
//    public LinkedHashMap<NFAState, LinkedHashMap<Character, NFAState>> transitionTable;

    /**
     * Constructor to each State's multiple path options
     *
     * @param name - name of state
     */
    public NFAState(String name) {
        super(name);
        transitions = new LinkedHashMap<>();
//        transitionTable = new LinkedHashMap<>();
    }

    /**
     * if transitions contains the transition character,
     * get the set and add to it. if not, create a set with the toState in it,
     * then put the transition character and this new set.
     *
     * @param onSymb - transition character
     * @param toState - state transitioning to
     */
    public void addTransition(char onSymb, NFAState toState) {
        /* if transitions contains the transition character,
           get the set and add to it. if not, create a set with the toState in it,
           then put the transition character and this new set.
         */
        if (transitions.containsKey(onSymb)) {
            Set<NFAState> existingSet = transitions.get(onSymb);

            existingSet.add(toState);
        } else {
            LinkedHashSet<NFAState> s = new LinkedHashSet<>();
            s.add(toState);
            transitions.put(onSymb, s);
        }
    }
}
