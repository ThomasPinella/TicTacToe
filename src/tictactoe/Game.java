
package tictactoe;

import java.util.Scanner;

public class Game 
{
    String ai;
    StateTree tree;
    State currentState;
    public void play()
    {
        int player = Integer.valueOf(getPlayer());
        tree = new StateTree(new State(), ai);
        
        currentState = tree.currentState;
        currentState.printState();
        
        boolean playing = true;
        if (player == 1)
        {
            getPlayerMove();
        }
        
        while (playing)
        {
            //comp move
            //System.out.println("TREE's CURRENT STATE:------------------");
            //tree.currentState.printState();
            //System.out.println("-----------------------------------");
            currentState = tree.makeAIMove();
            currentState.printState();
            //System.out.println("This State's util: " + currentState.util);
            if (currentState.terminalState && currentState.util != 0)
            {
                System.out.println("Game over... You lose :(");
                playing = false;
                break;
            }
            else if (currentState.terminalState && currentState.util == 0)
            {
                System.err.println("Draw!");
                playing = false;
                break;
            }
            else
            {
                getPlayerMove();
            }
            if (currentState.terminalState && currentState.util != 0)
            {
                System.err.println("Game over... You win :)");
                playing = false;
                break;
            }
            else if (currentState.terminalState && currentState.util == 0)
            {
                System.err.println("Draw!");
                playing = false;
                break;
            }
        }
    }
    
    public void getPlayerMove()
    {
        Scanner input = new Scanner(System.in);
        System.err.println("Your move...");
        String move = input.next();
        int val = Integer.valueOf(move);
        while (!currentState.squareIsEmpty(val))
        {
            System.err.println("Move is not valid. Please try again...");
            move = input.next();
            val = Integer.valueOf(move);
        }
        currentState = tree.updateCurrentState(val);
        currentState.printState();
        //System.out.println("This State's util: " + currentState.util);
    }
    
    public String getPlayer()
    {
        Scanner input = new Scanner(System.in);
        
        System.err.println("Would you like to be X or O?");
        String player = input.next();
        if (player.equals("X") || player.equals("x"))
        {
            player = "1";
            ai = "-1";
        }
        else if (player.equals("O") || player.equals("o"))
        {
            player = "-1";
            ai = "1";
        }
        else
        {
            System.err.println("Please enter either an X or an O.");
            getPlayer();
        }
        return player;
    }
}