package fa.nfa;

import fa.State;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class NFAState extends State {

    /* instance variables */
    public LinkedHashMap<Character, Set<NFAState>> transitions;
    public LinkedHashMap<NFAState, LinkedHashMap<Character, NFAState>> transitionTable;

    /**
     * Constructor to each State's multiple path options
     *
     * @param name - name of state
     */
    public NFAState(String name) {
        super(name);
        transitions = new LinkedHashMap<>();
        transitionTable = new LinkedHashMap<>();
    }
}
