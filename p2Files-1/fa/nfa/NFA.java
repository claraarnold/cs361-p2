package fa.nfa;

import fa.State;

import java.util.*;
import java.util.LinkedHashMap;

public class NFA implements NFAInterface {

    /* instance variables */
    public LinkedHashSet<Character>sigma;
    public LinkedHashSet<Character>Q;
    public String q0;
    public LinkedHashSet<NFAState>F;
    public Map<Character, Set<NFAState>> transitions;
    public LinkedHashMap<NFAState, LinkedHashMap<Character, NFAState>> transitionTable;
    public LinkedHashSet<Character> eClosures;

    /**
     * Constructor for Non-Deterministic Finite Automata (NFA)
     * Instantiates NFA 5-tuple
     */
    public NFA() {
        sigma = new LinkedHashSet<>();
        Q = new LinkedHashSet<>();
        q0 = "";
        F = new LinkedHashSet<>();
        transitions = new HashMap<>();
        transitionTable = new LinkedHashMap<>();
        eClosures = new LinkedHashSet<>();
    }

    @Override
    public boolean addState(String name) {
        return false;
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
    public State getState(String name) {
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
        return false;
    }
}
