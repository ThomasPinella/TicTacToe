
package tictactoe;

import java.util.ArrayList;

public class State 
{
    int alpha;
    int beta;
    int[][] board;
    ArrayList<Integer> p1;
    ArrayList<Integer> p2;
    int who;
    boolean terminalState;
    Integer util;
    ArrayList<State> childStates;
    State parentState;
    State nextMaxState;
    State nextMinState;
    
    public State()
    {
        int n = 3;
        board = new int[n][n];
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                board[i][j] = 0;
            }
        }
        
        terminalState = false;
        childStates = new ArrayList<>();
        who = 1;
        p1 = new ArrayList<>();
        p2 = new ArrayList<>();
        util = null;
    }
    
    public void placeX(int loc)
    {
        int row = 0;
        int col = 0;
        if (loc >= 1 && loc <= 3)
        {
            col = loc - 1;
            row = 0;
        }
        if (loc >= 4 && loc <= 6)
        {
            col = loc - 4;
            row = 1;
        }
        if (loc >= 7 && loc <= 9)
        {
            col = loc - 7;
            row = 2;
        }

        board[row][col] = 1;
        
        boolean running = true;
        int counter = 0;
        while (counter < p1.size() && running)
        {
            if (p1.get(counter) > loc)
            {
                p1.add(counter, loc);
                running = false;
            }
            counter++;
        }
        
        if (running)
        {
            p1.add(loc);
        }
    }
    
    public void placeO(int loc)
    {
        int row = 0;
        int col = 0;
        if (loc >= 1 && loc <= 3)
        {
            col = loc - 1;
            row = 0;
        }
        if (loc >= 4 && loc <= 6)
        {
            col = loc - 4;
            row = 1;
        }
        if (loc >= 7 && loc <= 9)
        {
            col = loc - 7;
            row = 2;
        }
        board[row][col] = -1;
        
        boolean running = true;
        int counter = 0;
        while (counter < p2.size() && running)
        {
            if (p2.get(counter) > loc)
            {
                p2.add(counter, loc);
                running = false;
            }
            counter++;
        }
        
        if (running)
        {
            p2.add(loc);
        }
    }
    
    public void checkIfGoalState()
    {
        if (who == -1) //O is about to go, X may have won though, check X (p1)
        {
            check(p1);
            if (terminalState)
            {
                util = 1;
                return;
            }
        }
        
        if (who == 1) //X is about to go, O may have won though, check O (p2)
        {
            check(p2);
            if (terminalState)
            {
                util = -1;
                return;
            }
        }
        
        if (p1.size() + p2.size() >= 9)
        {
            terminalState = true; //Tie
            util = 0;
        }
    }
    
    public void check(ArrayList<Integer> p)
    {
        int[] measure = {0, 0, 0, 0, 0, 0, 0, 0};
        int[][] combos = 
                        {   {1, 2, 3},
                            {4, 5, 6},
                            {7, 8, 9},
                            {1, 5, 9},
                            {3, 5, 7},
                            {1, 4, 7},
                            {2, 5, 8},
                            {3, 6, 9}};
        for (int i = 0; i < p.size(); i++)
        {
            for (int comboC = 0; comboC < combos.length; comboC++)
            {
                if (p.get(i) == combos[comboC][measure[comboC]])
                {
                    measure[comboC]++;
                    if (measure[comboC] == 3)
                    {
                        terminalState = true;
                        return;
                    }
                }
            }
        }
    }
    
    public void generateChildStates()
    {
        checkIfGoalState();
        
        if (terminalState)
        {
            return;
        }
        
        ArrayList<Integer> possibleLocs = new ArrayList<>();
        int len = 9;
        for (int i = 1; i <= len; i++)
        {
            //if (p1.indexOf(i) == -1 && p2.indexOf(i) == -1)
            if (!p1.contains(i) && !p2.contains(i))
            {
                possibleLocs.add(i);
            }
        }
        
        //System.out.println(possibleLocs.size());
        for (int i = 0; i < possibleLocs.size(); i++)
        {
            State s = new State();
            s.copyThisState(this);
            if (who == 1)
            {
                s.placeX(possibleLocs.get(i)); //Also add who and placeO
                s.who = -1;
            }
            else
            {
                s.placeO(possibleLocs.get(i)); //Also add who and placeO
                s.who = 1;
            }
            //s.printState();
            childStates.add(s);
            s.parentState = this;
            
        }
    }
    
    public void copyThisState(State s)
    {
        //this.p1 = s.p1; //This might not be correct because of pointers.
        //this.p2 = s.p2;
        for (int i = 0; i < s.p1.size(); i++)
        {
            this.p1.add(s.p1.get(i));
        }
        for (int i = 0; i < s.p2.size(); i++)
        {
            this.p2.add(s.p2.get(i));
        }
        
        //For the look of the board...
        
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                this.board[i][j] = s.board[i][j];
            }
        }
    }
    
    public void printState()
    {
        System.err.println(board[0][0] + " | " + board[0][1] + " | " + board[0][2]);
        System.err.println("----------");
        System.err.println(board[1][0] + " | " + board[1][1] + " | " + board[1][2]);
        System.err.println("----------");
        System.err.println(board[2][0] + " | " + board[2][1] + " | " + board[2][2]);
        System.err.println();
        /*
        System.out.print("p1: ");
        for (int i = 0; i < p1.size(); i++)
        {
            System.out.print(p1.get(i) + ", ");
        }
        System.out.println();
        System.out.print("p2: ");
        for (int i = 0; i < p2.size(); i++)
        {
            System.out.print(p2.get(i) + ", ");
        }
        */
        System.out.println();
        System.out.println();
    }
    
    public boolean squareIsEmpty(int val)
    {
        if (p1.contains(val) || p2.contains(val))
        {
            return false;
        }
        return true;
    }
    
    public boolean isEqualTo(State s)
    {
        if (this.p1.equals(s.p1) && this.p2.equals(s.p2))
        {
            return true;
        }
        return false;
    }
}