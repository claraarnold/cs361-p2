package fa.nfa;

import fa.State;

import java.lang.reflect.Array;
import java.util.*;
import java.util.LinkedHashMap;

public class NFA implements NFAInterface {

    /* instance variables */
    public LinkedHashSet<Character> sigma;
    public LinkedHashSet<NFAState> states;
    public String q0;
    public LinkedHashSet<NFAState> F;
    public LinkedHashMap<Character, Set<NFAState>> transitions;
//    public LinkedHashMap<NFAState, LinkedHashMap<Character, NFAState>> transitionTable;
    public LinkedHashSet<NFAState> eClosures;

    /**
     * Constructor for Non-Deterministic Finite Automata (NFA)
     * Instantiates NFA 5-tuple
     */
    public NFA() {
        sigma = new LinkedHashSet<>();
        states = new LinkedHashSet<>();
        q0 = "";
        F = new LinkedHashSet<>();
        transitions = new LinkedHashMap<>();
//        transitionTable = new LinkedHashMap<>();
        eClosures = new LinkedHashSet<>();
    }

    @Override
    public boolean addState(String name) {
        boolean retVal = false;
        NFAState newState = new NFAState(name);
        if(states.isEmpty()) {
            states.add(newState);
            retVal = true;
        } else {
            for(NFAState s : states) {
                if(s.toString().equals(name)) { // if there is already a state with 'name'
                    return retVal;
                }
            }
        }
        if(retVal && !states.contains(newState)) { // if state created successfully
            states.add(newState);
        }
        return retVal;
    }

    @Override
    public boolean setFinal(String name) {
        return false;
    }

    @Override
    public boolean setStart(String name) {
        return false;
    }

    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    @Override
    public boolean accepts(String s) {
        return false;
    }

    @Override
    public Set<Character> getSigma() {
        return null;
    }

    @Override
    public NFAState getState(String name) {
        /* this now returns NFAState instead of State,
        don't know why the test was bugging about this */
        return null;
    }

    @Override
    public boolean isFinal(String name) {
        return false;
    }

    @Override
    public boolean isStart(String name) {
        return false;
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        return null;
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        return null;
    }

    @Override
    public int maxCopies(String s) {
        return 0;
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        return false;
    }

    @Override
    public boolean isDFA() {
        boolean retVal = false;

        // set of NFAStates
        Set<NFAState> toStates;

        // check if each state only has one transition per symbol
        for (NFAState s : states) {
            for (Character c : sigma) {
                // TODO: needs a try/catch? probably going to return null at some point
                toStates = s.transitions.get(c);
                // if it's a DFA, toStates should have at most 1 element
                retVal = toStates.size() <= 1;
            }
        }
        return retVal;
    }
}
