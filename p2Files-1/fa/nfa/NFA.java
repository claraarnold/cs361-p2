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
        Queue<NFAState> stateQueue = new LinkedList<>();

        //find start & add to queue
        NFAState current = new NFAState("");
        for (NFAState state : states) {
            if (isStart(state.getName())) {
                current = state;
            }
        }
        stateQueue.add(current);

        Set<NFAState> visited = new LinkedHashSet<>();

        boolean accepted = false;

        while (!stateQueue.isEmpty()) {
            NFAState currentState = stateQueue.poll();

            if (visited.contains(currentState)) { continue; }

            visited.add(currentState);

            if (isFinal(currentState.getName())) { // no longer checking if s.isEmpty()
                accepted = true;
            } else {
                accepted = false;
            }

            for (char c : s.toCharArray()) {
                boolean hasRegularTransition = false;

                if (currentState.transitions.containsKey(c)) {
                    stateQueue.addAll(currentState.transitions.get(c));
                    hasRegularTransition = true;
                }

                for (NFAState nextState : eClosure(currentState)) {
                    if (!stateQueue.contains(nextState)) {
                        stateQueue.add(nextState);
                    }
                }

                if (!hasRegularTransition) {
                    break;
                }
            }

            if (isFinal(currentState.getName())) {
                accepted = true;
            }
        }

        return accepted;

//        Set<NFAState> currStates = eClosure(getState(startState)); // initial state
//        Queue<Set<NFAState>> queue = new LinkedList<>();
//        queue.add(currStates);
//
//        int currentPosition = 0;
//
//        while(!queue.isEmpty() && currentPosition < s.length()) {
//            char currentSigma = s.charAt(currentPosition);
//            Set<NFAState> nextStates = new HashSet<>();
//
//            for(NFAState state : currStates) {
//                Set<NFAState> transitionStates = transitions.get(currentSigma); // getting transitions for sigma
//
//                if(transitionStates != null) {
//                    nextStates.addAll(transitionStates);
//                }
//            }
//
//            if(nextStates.isEmpty()) {
//                return false; // no possible transitions for current sigma
//            }
//
//            queue.add(nextStates);
//            currStates = nextStates;
//            currentPosition++;
//        }
//
//        for(Set<NFAState> statesSet : queue) {
//            for(NFAState state : statesSet) {
//                if(isFinal(state.getName())) {
//                    return true; // at least one copy is in accept state
//                }
//            }
//        }
//
//        return false; // no accept state found



//        boolean retVal = false;
//        NFAState current = new NFAState("");
//        for (NFAState state : states) {
//            if (isStart(state.getName())) {
//                current = state;
//            }
//        }
//
//        // loop through string
//        for (Character c : s.toCharArray()) {
//            // symbol not in language
//            if (!sigma.contains(c)) {
//                return false;
//            }
//
//            // move through machine
//            try {
////                current.getNextState();
//            } catch (NullPointerException e) {
//                return false;
//            }
//        }
//
//
//
//        return retVal;
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
        // change to retVal
        boolean retVal = false;
        for (NFAState s : finalStates) {
            if (s.toString().equals(name)) {
                retVal = true;
                break;
            }
        }
        return retVal;
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
       Set<NFAState> epsilonClosure = new LinkedHashSet<>();
       Set<NFAState> visited = new LinkedHashSet<>();

       // stack for DFS
        Stack<NFAState> stack = new Stack<>();
        stack.push(s);

        while (!stack.isEmpty()) {
            NFAState current = stack.pop();

            if (!visited.contains(current)) {
                epsilonClosure.add(current);
                visited.add(current);

                // add all states reachable from current on an 'e'
                for (NFAState next : current.getEpsilonTransitions()) {
                    stack.push(next);
                }
            }
        }

        return epsilonClosure;
    }

    @Override
    public int maxCopies(String s) {
        int maxCopies = 0;
        int compVal = 0;
        Set<NFAState> visited = new LinkedHashSet<>();
//        Set<NFAState> startState = new LinkedHashSet<>();
        Queue<NFAState> queue = new ArrayDeque<>();

        // start state
        NFAState start = new NFAState("");
        for (NFAState state : states) {
            if (isStart(state.getName())) {
                start = state;
//                startState.add(state);
            }
        }

        queue.add(start);

        while (!queue.isEmpty()) {
            NFAState currentState = queue.poll();
//            if (!visited.contains(currentState)) {
                visited.add(currentState);
//            }
//            maxCopies++;

            for (Character c : s.toCharArray()) {
                Set<NFAState> nextStates = currentState.getNextState(c);
                for (NFAState nextState : nextStates) {
                    if (!visited.contains(nextState)) {
                        queue.add(nextState);
                        visited.add(nextState);
                    }
                }
            }







//                for (Character c : s.toCharArray()) {
//                    try {
//                        Set<NFAState> nextStates = currentState.getNextState(c);
//                        for (NFAState nextState : nextStates) {
//                            if (!visited.contains(nextState)) {
//                                queue.add(nextState);
//                                visited.add(nextState);
//
//                                Set<NFAState> eTransitions = eClosure(currentState);
//                                queue.addAll(eTransitions);
//                            }
//                        }
//                    } catch (NullPointerException ignored) {
//                        // ignored catch body
//                    }
//                }


//                maxCopies = Math.max(maxCopies, queue.size());
            }
        return maxCopies;
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
//                          transitions
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
