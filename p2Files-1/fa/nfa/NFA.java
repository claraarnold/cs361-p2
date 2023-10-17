package fa.nfa;

import fa.State;

import java.lang.reflect.Array;
import java.util.*;
import java.util.LinkedHashMap;

public class NFA implements NFAInterface {

    /* instance variables */
    public LinkedHashSet<Character> sigma;
    public LinkedHashSet<NFAState> states;
    public LinkedHashSet<NFAState> finalStates;
    public String startState;
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
        sigma.add('e');     // epsilon is a part of every alphabet
        states = new LinkedHashSet<>();
        finalStates = new LinkedHashSet<>();
        startState = "";
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
        if(!retVal) { // if state created successfully
            states.add(newState);
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean setFinal(String name) {
        boolean retVal = false;
        boolean done = false;
        NFAState newState = new NFAState(name);
        if (states.isEmpty()) {
            return retVal;
        } else {
            while (!done) {
                for (NFAState s : states) {
                    if (s.toString().equals(name)) {
                        finalStates.add(newState);
                        retVal = true; // successfully added 'name' to finalStates
                        break;
                    } else {
                        retVal = false; // no state with 'name' exists
                    }
                }
                done = true;
            }
        }
        return retVal;
    }

    @Override
    public boolean setStart(String name) {
        boolean retVal = false;
        boolean done = false;
        if (states.isEmpty()) {
            return retVal;
        } else {
            while (!done) {
                for (NFAState s : states) {
                    if (s.toString().equals(name)) {
                        startState = name;
                        retVal = true; // successfully set 'name' to startState
                        break;
                    } else {
                        retVal = false; // no state with 'name' exists
                    }
                }
                done = true;
            }
        }
        return retVal;
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
        return sigma;
    }

    @Override
    public NFAState getState(String name) {
        for (NFAState s : states) {
            if (s.toString().equals(name)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public boolean isFinal(String name) {
        for (NFAState s : finalStates) {
            return s.toString().equals(name);
        }
        return false;
    }

    @Override
    public boolean isStart(String name) {
        return startState.equals(name);
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        if(transitions.containsKey(onSymb)) {
            Set<NFAState> transitionStates = transitions.get(onSymb);
            if(transitionStates != null) {
                return transitionStates; // returning transition states for the symb
            }
        }
        return null; // if no transition states for symb
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        LinkedHashSet<NFAState> epsilonClosure = new LinkedHashSet<>();
        Stack<NFAState> stack = new Stack<>(); // to perform DFS

        stack.push(s);
        /* Loop through stack until empty to get epsilon transitions with 'e' */
        while(!stack.isEmpty()) {
            NFAState currState = stack.pop();
            epsilonClosure.add(currState); // add currState to epsilonClosure
            Set<NFAState> epsilonTransitions = eClosure(currState);
            for(NFAState nextState : epsilonTransitions) {
                // push unvisited states onto stack
                if(!epsilonClosure.contains(nextState)) {
                    stack.push(nextState);
                }
            }
        }
        eClosures = epsilonClosure; // updating instance var.

        return epsilonClosure;
    }

    @Override
    public int maxCopies(String s) {
        return 0;
    }

    @Override
    public boolean addTransition(String fromState, Set<String> toStates, char onSymb) {
        boolean retVal = false;

        // pretty sure this all has to loop through the size of toStates, executed by this for loop:
        for (int i = 0; i < toStates.size(); i++) {
            boolean done = false;
            boolean dneFlagFromState = true;
            boolean dneFlagToState = true;
            NFAState to = new NFAState("");

            // fromState check exist
            for (NFAState s : states) {
                if (s.toString().equals(fromState)) {
                    dneFlagFromState = false;
                    break;
                }
            }
            if (dneFlagFromState) {
                return false;
            }

            // set toState
            for (NFAState s : states) {
                for (String z : toStates) {
                    if (s.getName().equals(z)) {
                        to = s;
                        dneFlagToState = false;
                        break;
                    }
                }
            }

            // return if toState does not exist
            // (will be true if it doesn't find it while setting)
            if (dneFlagToState) {
                return false;
            }

            // alphabet check exist
            if (sigma.contains(onSymb)) {
                while (!done) {
                    for (NFAState s : states) {
                        if (s.toString().equals(fromState)) {
                        s.addTransition(onSymb, to);
//                        transitions
//                                .computeIfAbsent(current, k -> new LinkedHashMap<>())
//                                .put(onSymb, to);
                        retVal = true;
                        break;
                        }
                    }
                    done = true;
                }
            }
        }
        return retVal;
    }

    @Override
    public boolean isDFA() {
        boolean retVal = false;

        // set of NFAStates
        Set<NFAState> toStates;

        // check if each state only has one transition per symbol
        for (NFAState s : states) {
            for (Character c : sigma) {
                if (s.transitions.get(c) != null) {
                    toStates = s.transitions.get(c);
                    if (c == 'e') {
                        retVal = !toStates.isEmpty();
                        if (retVal) {
                            return false;
                        }
                    }
                    // if it's a DFA, toStates should have at most 1 element
                    retVal = toStates.size() <= 1; // check if e has at least one transition
                    if (!retVal) {
                        return false;
                    }
                }
            }
//            s.transitionTable.get(sigma)
        }
        return retVal;
    }
}
