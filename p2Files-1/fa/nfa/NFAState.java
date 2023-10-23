package fa.nfa;

import fa.State;

import java.util.*;

public class NFAState extends State {

    /* instance variables */
    public LinkedHashMap<Character, LinkedHashSet<NFAState>> transitions;
    LinkedHashSet<NFAState> eTransitions = new LinkedHashSet<>();
    public String name;

    /**
     * Constructor to each State's multiple path options
     *
     * @param name - name of state
     */
    public NFAState(String name) {
        super(name);
        this.name = name;
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
            if (onSymb == 'e') {
                eTransitions.add(toState);
            }
        } else {
            LinkedHashSet<NFAState> s = new LinkedHashSet<>();
            s.add(toState);
            transitions.put(onSymb, s);
            if (onSymb == 'e') {
                eTransitions.add(toState);
            }
        }
    }

    /**
     * Returns the set of epsilon transitions from the called NFAState
     *
     * @return - Set of NFAStates
     */
    public Set<NFAState> getEpsilonTransitions() {
        return eTransitions;
    }

    public Set<NFAState> getNextState(char symbol) {
        return transitions.getOrDefault(symbol, null);
    }

    /**
     * toString for an NFA State returns name of the state
     *
     * @return - name
     */
    public String toString() {
        return name;
    }
}
