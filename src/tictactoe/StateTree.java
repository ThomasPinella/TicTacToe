
package tictactoe;

public class StateTree 
{    
    int ai;
    State currentState;
    public StateTree(State root, String x)
    {
        ai = Integer.valueOf(x);
        buildTree(root);
        //System.out.println("Finished building state tree!");
        getUtil(root);
        //System.out.println("Finished adding utilities!");
        currentState = root;
    }
    
    public void buildTree(State s)
    {
        s.generateChildStates();
        for (State child : s.childStates)
        {
            buildTree(child);
        }
       
    }
    
    public int getUtil(State s)
    {
        if (s.terminalState)
        {
            System.out.println("term util: " + s.util); // TODO: Find out why there are no terminals here! Base case isn't being reached
            return s.util;
        }
        int max = -100;
        int min = 100;
        State maxState = new State();
        State minState = new State();
        int num;
        for (State child : s.childStates)
        {
            if (child.util == null)
            {
                num = getUtil(child);
            }
            else
            {
                num = child.util;
            }
            
            if (num > max)
            {
                max = num;
                maxState = child;
            }

            if (num < min)
            {
                min = num;
                minState = child;
            }
        }
        
        s.nextMaxState = maxState;
        s.nextMinState = minState;
        if (s.who == -1)
        {
            s.util = min; //was max
        }
        if (s.who == 1)
        {
            s.util = max;
        }
        //s.printState();
        //System.out.println(s.util);
//        for (StackTraceElement ste : Thread.currentThread().getStackTrace())
//        {
//            System.out.println(ste);
//        }
        return s.util;
    }
    
    public State updateCurrentState(int move)
    {
        if (currentState.who == 1)
        {
            currentState.placeX(move);
        }
        if (currentState.who == -1)
        {
            currentState.placeO(move);
        }
        for (State child : currentState.childStates)
        {
            if (child.isEqualTo(currentState))
            {
                currentState = child;
                return currentState;
            }
        }
        return currentState;
    }
    
    public State makeAIMove()
    {
        /*
        for (State s : currentState.childStates)
        {
            s.printState();
            System.out.println("UTIL: " + s.util);
        }
        */
        if (ai == 1)
        {
            currentState = currentState.nextMaxState;
            //System.out.println("UTIL (maxing): " + currentState.util);
            return currentState;
        }
        else
        {
            currentState = currentState.nextMinState;
            //System.out.println("UTIL (minning): " + currentState.util);
            return currentState;
        }
    }
}
