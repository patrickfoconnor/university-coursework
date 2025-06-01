package wumpus;

import java.util.Stack;

import wumpus.Environment.Action;

enum Directions{
	EAST, NORTH, WEST, SOUTH
}

public class TestAgent extends AgentFOL{
    boolean debug = true;

    //end of cave horizontally
	int Xmax;
	//end of cave vertically
	int Ymax;
	boolean bumpFlag;
	boolean goldFlag;
	// Flag is set when wumpus is killed by an arrow
	boolean screamFlag;
	// Flag is set when wumpus is located using knowledge base created
	boolean wumpusFound;
	int arrow;
	// Wumpus location
	int Wumpus_X;
	int Wumpus_Y;
	
	private State myAgentWorld[][];
	private MyAgent myAgent;

	
	// Gold Finding Forward DFS Stack
	Stack<State> path = new Stack<State>();
	// Returning Shortest Path DFS stack (after grabbing gold)
	Stack<State> goldStack = new Stack<State>();
	
	private class State{
		int X;
		int Y;
		boolean isVisited;
		boolean isReVisited;		// isVistedReturnFlag for returning shortest path DFS
		boolean isSafe;				// whether cell is safe or not
		int wumpusOdds;		// this is used to pin-point the wumpus location
		
		State(int x, int y){
			this.X = x;
			this.Y = y;
			Wumpus_X = -1;
			Wumpus_Y = -1;
			isVisited = false;
			isReVisited = false;
			isSafe = false;
			wumpusFound = false;
			wumpusOdds = 0;	// Risk can be 0 to 4, -1 for no risk (that means safe block)
		}
	}
	
	private class MyAgent{
		int X;
		int Y;
		Directions currentDirection;
		
		MyAgent(){
			this.X = 0;
			this.Y = 0;
			this.currentDirection = Directions.EAST;
		}
		
		@Override
		public String toString(){
			return String.format("X:"+this.X+" Y:"+this.Y+" Directions:"+this.currentDirection);
		}
	}
	
	
	public TestAgent( )
	{
		myAgentWorld = new State[10][10];
		
		for(int y=0; y<10; y++){
			for(int x=0; x<10; x++){
				myAgentWorld[y][x] = new State(y,x);
			}
		}
		
		myAgent = new MyAgent(); 
		Xmax = 10;
		Ymax = 10;
		bumpFlag = false;
		goldFlag = false;
		screamFlag = false;
		arrow = 1;
	}

    public Action getAction
    (boolean stench,
    boolean breeze,
    boolean glitter,
    boolean bump,
    boolean scream
    ) {
        // Logic for scream
		if(scream){
			screamFlag = true;
			arrow = 0;
		}

		// Logic for glitter
		if(glitter){
			goldFlag = true;
			return Action.GRAB;
		}
		
		// Logic for shortest path returning DFS after grabbing gold
		if(goldFlag){
			if(myAgent.X == 0 && myAgent.Y == 0)
				return Action.CLIMB;
			
			// Mark Own Cell Safe
			setCellSafeReVisited(myAgent.X, myAgent.Y);
			
			// Mark Neighbours safe, it will help in shortest path.
			if((!breeze && !stench) || (screamFlag && stench && !breeze)){
				setAdjacentSafe(myAgent.X, myAgent.Y);
			}
			
			// Start Shortest DFS from here
			State nextShortestCell = getAdjacentShortestPath(myAgent.X, myAgent.Y, myAgent.currentDirection);
			
			// If no more neighbours to explore, backtrace to previous node 
			if(nextShortestCell == null) {
				State destState = goldStack.peek();
				Action currAction = getNextAction(destState, myAgent);
				
				if(currAction != Action.FORWARD){
					setAgentDirection(myAgent, currAction);
				}
				// Forward Action, Update Agent Coordinates as state Coordinates
				else{
					myAgent.X = destState.X;
					myAgent.Y = destState.Y;
					goldStack.pop();
				}
				return currAction;
			}
			else {
				
				Action currAction = getNextAction(nextShortestCell, myAgent);
				
				if(currAction != Action.FORWARD){
					setAgentDirection(myAgent, currAction);
				}
				// Forward Action, Update Agent Coordinates as state Coordinates
				else{
					// Push in stack and move forward into the depth
					goldStack.push(myAgentWorld[myAgent.X][myAgent.Y]);
					myAgent.X = nextShortestCell.X;
					myAgent.Y = nextShortestCell.Y;
				}
				
				return currAction;
			}
			
		}
		
		// Edge Case, if breeze at the starting position, climb out directly		
		if((breeze) && (myAgent.X == 0 && myAgent.Y == 0)){			
			//printStatus();
			return Action.CLIMB;
		}
		
		// Edge Case, if stench at the starting position, shoot the arrow and move ahead	
		if((stench) && (myAgent.X == 0 && myAgent.Y == 0) && (!breeze) && (arrow > 0)){	
			
			// increment the wumpus risk factor in stench neighbours [increment only for non-visited cells]
			if(!myAgentWorld[myAgent.X][myAgent.Y].isVisited) {
				addAdjacentRisk(myAgent.X, myAgent.Y);
			}
			
			setCellSafeVisited(myAgent.X, myAgent.Y);
			
			// Shoot the arrow
			arrow--;
			setNextCellSafe(myAgent);
			return Action.SHOOT;
		}

		// Logic for bump
		if(bump){
			//bumpFlag = true;
			State state = path.pop();
			
			if(myAgent.currentDirection == Directions.EAST){
				Xmax = myAgent.X;
			}
			if(myAgent.currentDirection == Directions.NORTH){
				Ymax = myAgent.Y;
			}	

			myAgent.X = state.X;
			myAgent.Y = state.Y;	
		}

		// Logic for stench and breeze
		boolean none = (!stench && !breeze);
		boolean either = (stench || breeze);
		if(none || either){			
			if(none){
				setAdjacentSafe(myAgent.X, myAgent.Y);
			}
			
			if(either){
				if(screamFlag && stench && !breeze){
					setAdjacentSafe(myAgent.X, myAgent.Y);
				}
				
				if(stench) {
					// increment the wumpus risk factor in stench neighbours [increment only for non-visited cells]
					if(!myAgentWorld[myAgent.X][myAgent.Y].isVisited) {
						addAdjacentRisk(myAgent.X, myAgent.Y);
					}	
					
					// if you know the wumpus location, and you have the arrow, then kill the wumpus
					if(wumpusFound && (arrow > 0) && !breeze) {
						State wumpusCell = myAgentWorld[Wumpus_X][Wumpus_Y];
						Action currAction = getNextAction(wumpusCell, myAgent);
						
						if(currAction != Action.FORWARD) {
							setAgentDirection(myAgent, currAction);
							return currAction;
						}
						else {
							arrow--;
							setNextCellSafe(myAgent);
							return Action.SHOOT;
						}
					}
					
					// if you know the wumpus location, then mark all stench neighbours safe except wumpus cell
					if(wumpusFound && !breeze && !myAgentWorld[myAgent.X][myAgent.Y].isVisited) {
						setAdjacentStenchSafe(myAgent.X, myAgent.Y);
					}
				}
			}

			//mark itself safe and visited
			setCellSafeVisited(myAgent.X, myAgent.Y);
			
			//find the next adjacent unvisited safe cell
			State nextUnvisitedSafeCell = getNextUnvisitedSafeCell(myAgent.X, myAgent.Y, myAgent.currentDirection);
			
			// reached start position after all the dead ends
			if(path.isEmpty() && (nextUnvisitedSafeCell == null)){
				return Action.CLIMB;
			}
			
			// try to kill the wumpus using arrow by guestimating through the risk_factor values [when all the neighbours are unsafe].
			if((nextUnvisitedSafeCell == null) && (stench) && (arrow > 0) && (!breeze)) {
				State possible_wumpus_state = possibleWumpusState(myAgent.X, myAgent.Y, myAgent.currentDirection);
				if(possible_wumpus_state != null) {
					
					Action currAction = getNextAction(possible_wumpus_state, myAgent);
					
					if(currAction != Action.FORWARD) {
						setAgentDirection(myAgent, currAction);
						return currAction;
					}
					else {
						arrow--;
						setNextCellSafe(myAgent);
						return Action.SHOOT;
					}
				}
			}
			
			// Pop from the stack and backtrace from the dead end
			if(nextUnvisitedSafeCell == null){
				State destState = path.peek();
				Action currAction = getNextAction(destState, myAgent);
				
				if(currAction != Action.FORWARD){
					setAgentDirection(myAgent, currAction);
				}
				// Forward Action, Update Agent Coordinates as state Coordinates
				else{
					myAgent.X = destState.X;
					myAgent.Y = destState.Y;
					path.pop();
				}
				return currAction;
			}
			else{
				Action currAction = getNextAction(nextUnvisitedSafeCell, myAgent);
				
				if(currAction != Action.FORWARD){
					setAgentDirection(myAgent, currAction);
				}
				// Forward Action, Update Agent Coordinates as state Coordinates
				else{
					path.push(myAgentWorld[myAgent.X][myAgent.Y]);
					myAgent.X = nextUnvisitedSafeCell.X;
					myAgent.Y = nextUnvisitedSafeCell.Y;
				}
				return currAction;
			}
		}
		
		
		if(path.isEmpty()){
			return Action.CLIMB;
		}
		return Action.CLIMB;
        
    }
    // mark adjacent next (based on the agent direction) cell safe 
	private void setNextCellSafe(MyAgent agent){
		
		int x = agent.X;
		int y = agent.Y;
		
		switch(agent.currentDirection){
		
			case EAST:{
				if(x+1 < Xmax && x+1 >= 0){
					myAgentWorld[x+1][y].isSafe = true;
					myAgentWorld[x+1][y].wumpusOdds = -1;
				}
				break;
			}
			case WEST:{
				if(x-1 < Xmax && x-1 >= 0){
					myAgentWorld[x-1][y].isSafe = true;
					myAgentWorld[x-1][y].wumpusOdds = -1;
				}
				break;
			}
			case NORTH:{
				if(y+1 < Ymax && y+1 >= 0){
					myAgentWorld[x][y+1].isSafe = true;
					myAgentWorld[x][y+1].wumpusOdds = -1;
				}
				break;
			}
			case SOUTH:{
				if(y-1 < Ymax && y-1 >= 0){
					myAgentWorld[x][y-1].isSafe = true;
					myAgentWorld[x][y-1].wumpusOdds = -1;
				}
				break;
			}
		}
	}
	
	// print function for debugging
	/*private void printStatus() {
		//agent
		System.out.println("Agent:");
		System.out.println(myAgent.toString());
		
		System.out.println();
		System.out.println("World:");
		
		for(int y=0; y<10; y++){
			for(int x=0; x<10; x++){
				System.out.println(myAgentWorld[y][x].toString());
			}
		}
	}*/
	
	// get Next Unvisited Shorted Path Neighbour for returning DFS after grabbing gold
	private State getAdjacentShortestPath(int x, int y, Directions agentsCurrentDirection) {
		
		// if facing west, give preference to west first.
		if(agentsCurrentDirection == Directions.WEST) {
			// West
			if(x-1 < Xmax && x-1 >= 0){
				if((myAgentWorld[x-1][y].isSafe == true) && (myAgentWorld[x-1][y].isReVisited == false)){
					return myAgentWorld[x-1][y];
				}
			}
			// South 
			if(y-1 < Ymax && y-1 >= 0){
				if((myAgentWorld[x][y-1].isSafe == true) && (myAgentWorld[x][y-1].isReVisited == false)){
					return myAgentWorld[x][y-1];
				}
			}
		}
		else{	// if facing south, give preference to south first.
			// South 
			if(y-1 < Ymax && y-1 >= 0){
				if((myAgentWorld[x][y-1].isSafe == true) && (myAgentWorld[x][y-1].isReVisited == false)){
					return myAgentWorld[x][y-1];
				}
			}
			// West
			if(x-1 < Xmax && x-1 >= 0){
				if((myAgentWorld[x-1][y].isSafe == true) && (myAgentWorld[x-1][y].isReVisited == false)){
					return myAgentWorld[x-1][y];
				}
			}
		}
		
		//East
		if(x+1 < Xmax && x+1 >= 0){
			if((myAgentWorld[x+1][y].isSafe == true) && (myAgentWorld[x+1][y].isReVisited == false)){
				return myAgentWorld[x+1][y];
			}
		}
		
		// North
		if(y+1 < Ymax && y+1 >= 0){
			if((myAgentWorld[x][y+1].isSafe == true) && (myAgentWorld[x][y+1].isReVisited == false)){
				return myAgentWorld[x][y+1];
			}
		}
		
		return null;
	}
	
	// Guestimate possible Wumpus state Neighbour on the basis of risk factor
	private State possibleWumpusState(int x, int y, Directions agentsCurrentDirection) {
		
		State state = myAgentWorld[x][y];
		int currentMax = -1;
		
		if((x+1 < Xmax && x+1 >= 0) && (myAgentWorld[x+1][y].wumpusOdds != -1)){
			if(myAgentWorld[x+1][y].wumpusOdds > currentMax) {
				currentMax = myAgentWorld[x+1][y].wumpusOdds;
				state = myAgentWorld[x+1][y];
			}
		}
		
		if((y+1 < Ymax && y+1 >= 0) && (myAgentWorld[x][y+1].wumpusOdds != -1)){
			if(myAgentWorld[x][y+1].wumpusOdds > currentMax) {
				currentMax = myAgentWorld[x][y+1].wumpusOdds;
				state = myAgentWorld[x][y+1];
			}
		}
		
		if((x-1 < Xmax && x-1 >= 0) && (myAgentWorld[x-1][y].wumpusOdds != -1)){
				if(myAgentWorld[x-1][y].wumpusOdds > currentMax) {
					currentMax = myAgentWorld[x-1][y].wumpusOdds;
					state = myAgentWorld[x-1][y];
				}
		}
		
		if((y-1 < Ymax && y-1 >= 0) && (myAgentWorld[x][y-1].wumpusOdds != -1)){
			if(myAgentWorld[x][y-1].wumpusOdds > currentMax) {
				currentMax = myAgentWorld[x][y-1].wumpusOdds;
				state = myAgentWorld[x][y-1];
			}
		}
		
		// No unvisited neighbour found	
		if(currentMax == -1)
			return null;
		
		return state;
	}


	// Helper Functions
	private State goEast(int x, int y){
		//east
		if(x+1 < Xmax && x+1 >= 0){
			if((myAgentWorld[x+1][y].isSafe == true) && (myAgentWorld[x+1][y].isVisited == false)){
				return myAgentWorld[x+1][y];
			}
		}
		return null;
	}

	// Helper Functions
	private State goNorth(int x, int y){
		//north
		if(y+1 < Ymax && y+1 >= 0){
			if((myAgentWorld[x][y+1].isSafe == true) && (myAgentWorld[x][y+1].isVisited == false)){
				return myAgentWorld[x][y+1];
			}
		}
		return null;
	}

	// Helper Functions
	private State goWest(int x, int y){
		//west
		if(x-1 < Xmax && x-1 >= 0){
			if((myAgentWorld[x-1][y].isSafe == true) && (myAgentWorld[x-1][y].isVisited == false)){
				return myAgentWorld[x-1][y];
			}
		}
		return null;
	}

	// Helper Functions
	private State goSouth(int x, int y){
		//south
		if(y-1 < Ymax && y-1 >= 0){
			if((myAgentWorld[x][y-1].isSafe == true) && (myAgentWorld[x][y-1].isVisited == false)){
				return myAgentWorld[x][y-1];
			}
		}
		return null;
	}

	// get Next Unvisited Safe Cell for forwarding DFS algorithm
	private State getNextUnvisitedSafeCell(int x, int y, Directions agentsCurrentDirection) {
		if(agentsCurrentDirection == Directions.EAST){
			State east = goEast(x, y);
			if(east != null){
				return east;
			}
			State north = goNorth(x, y);
			if(north != null){
				return north;
			}
			State south = goSouth(x, y);
			if(south != null){
				return south;
			}
			State west = goWest(x, y);
			if(west != null){
				return west;
			}
		}
		else if(agentsCurrentDirection == Directions.WEST){
			State west = goWest(x, y);
			if(west != null){
				return west;
			}
			State north = goNorth(x, y);
			if(north != null){
				return north;
			}
			State south = goSouth(x, y);
			if(south != null){
				return south;
			}
			State east = goEast(x, y);
			if(east != null){
				return east;
			}
		}
		else if(agentsCurrentDirection == Directions.NORTH){
			State north = goNorth(x, y);
			if(north != null){
				return north;
			}
			State west = goWest(x, y);
			if(west != null){
				return west;
			}
			State east = goEast(x, y);
			if(east != null){
				return east;
			}
			State south = goSouth(x, y);
			if(south != null){
				return south;
			}
		}
		else{
			State south = goSouth(x, y);
			if(south != null){
				return south;
			}
			State west = goWest(x, y);
			if(west != null){
				return west;
			}
			State east = goEast(x, y);
			if(east != null){
				return east;
			}
			State north = goNorth(x, y);
			if(north != null){
				return north;
			}
		}
		return null;
	}

	// Mark isVisited and isSafe flags as true
	private void setCellSafeVisited(int x, int y) {
		State currentCell = myAgentWorld[x][y];
		currentCell.isSafe = true;
		currentCell.isVisited = true;
		currentCell.wumpusOdds = -1;
	}
	
	// Mark isReVisited and isSafe flags as true
	private void setCellSafeReVisited(int x, int y) {
		State currentCell = myAgentWorld[x][y];
		currentCell.isSafe = true;
		currentCell.isReVisited = true;
		currentCell.wumpusOdds = -1;
	}

	// setAgentDirection on the basis of rotation action
	private void setAgentDirection(MyAgent myAgent, Action currAction){
		int val = myAgent.currentDirection.ordinal();
		if(currAction == Action.TURN_LEFT){
			// Add one to myagent direction and handle case when it is > 3
			// if > 3 : Make it east
			val++;
			if(val > 3){
				myAgent.currentDirection = Directions.EAST;
			}
			else{
				myAgent.currentDirection = getDirection(val);
			}
		}
		else{
			// Subtract one to myagent direction and handle case when it is < 0
			// if < 0 : Make it South
			val--;
			if(val < 0){
				myAgent.currentDirection = Directions.SOUTH;
			}
			else{
				myAgent.currentDirection = getDirection(val);
			}
		}
		
		
	}
	
	// get Agent's current Directions
	private Directions getDirection(int val) {
		switch(val){
			case 0: {
				return Directions.EAST;
			}
			case 1: {
				return Directions.NORTH;
			}
			case 2: {
				return Directions.WEST;
			}
			case 3: {
				return Directions.SOUTH;
			}
		}
		return null;

	}

	// get Next move for the agent on the basis of next state to go
	private Action getNextAction(State state, MyAgent myAgent) {
		
		Directions desiredDirection = null;
		
		if((state.X == myAgent.X && state.Y == myAgent.Y) || (state.X != myAgent.X && state.Y != myAgent.Y)){
			return null;
		}
		
		if(state.X != myAgent.X){
			if(state.X > myAgent.X){
				desiredDirection = Directions.EAST;
			}
			else{
				desiredDirection = Directions.WEST;
			}
		}
		
		if(state.Y != myAgent.Y){
			if(state.Y > myAgent.Y){
				desiredDirection = Directions.NORTH;
			}
			else{
				desiredDirection = Directions.SOUTH;
			}
		}
		
		if(desiredDirection == myAgent.currentDirection){
			return Action.FORWARD;
		}

		if(myAgent.currentDirection == Directions.EAST){
			if(desiredDirection == Directions.NORTH){
				return Action.TURN_LEFT;
			}
		}

		if(myAgent.currentDirection == Directions.NORTH){
			if(desiredDirection == Directions.WEST){
				return Action.TURN_LEFT;
			}
		}

		if(myAgent.currentDirection == Directions.WEST){
			if(desiredDirection == Directions.SOUTH){
				return Action.TURN_LEFT;
			}
		}

		if(myAgent.currentDirection == Directions.SOUTH){
			if(desiredDirection == Directions.EAST){
				return Action.TURN_LEFT;
			}
		}
		
		return Action.TURN_RIGHT;		
	}

	// Mark all neighbours safe
	private void setAdjacentSafe(int x, int y) {
		if(x+1 < Xmax && x+1 >= 0){
			myAgentWorld[x+1][y].isSafe = true;
			myAgentWorld[x+1][y].wumpusOdds = -1;
		}
		if(x-1 < Xmax && x-1 >= 0){
			myAgentWorld[x-1][y].isSafe = true;
			myAgentWorld[x-1][y].wumpusOdds = -1;
		}
		if(y+1 < Ymax && y+1 >= 0){
			myAgentWorld[x][y+1].isSafe = true;
			myAgentWorld[x][y+1].wumpusOdds = -1;
		}
		if(y-1 < Ymax && y-1 >= 0){
			myAgentWorld[x][y-1].isSafe = true;
			myAgentWorld[x][y-1].wumpusOdds = -1;
		}
	}
	
	// Mark Stench Neighbours Safe Except Wumpus Cell Neighbour
	private void setAdjacentStenchSafe(int x, int y) {
		if(x+1 < Xmax && x+1 >= 0){
			if(!((x+1 == Wumpus_X) && (y == Wumpus_Y))) {
				myAgentWorld[x+1][y].isSafe = true;
				myAgentWorld[x+1][y].wumpusOdds = -1;
			}
		}
		if(x-1 < Xmax && x-1 >= 0){
			if(!((x-1 == Wumpus_X) && (y == Wumpus_Y))) {
				myAgentWorld[x-1][y].isSafe = true;
				myAgentWorld[x-1][y].wumpusOdds = -1;
			}
		}
		if(y+1 < Ymax && y+1 >= 0){
			if(!((x == Wumpus_X) && (y+1 == Wumpus_Y))) {
				myAgentWorld[x][y+1].isSafe = true;
				myAgentWorld[x][y+1].wumpusOdds = -1;
			}
		}
		if(y-1 < Ymax && y-1 >= 0){
			if(!((x == Wumpus_X) && (y-1 == Wumpus_Y))) {
				myAgentWorld[x][y-1].isSafe = true;
				myAgentWorld[x][y-1].wumpusOdds = -1;
			}
		}
	}
	
	// Increase Wumpus Risk Factor for Neighbours [for only unvisited stench]
	private void addAdjacentRisk(int x, int y) {
		
		int num_of_risky_cells_count = 0;
		
		if(x+1 < Xmax && x+1 >= 0){
			if(myAgentWorld[x+1][y].wumpusOdds != -1) {
				myAgentWorld[x+1][y].wumpusOdds++;
				
				if(myAgentWorld[x+1][y].wumpusOdds >= 2) {
					num_of_risky_cells_count++;
					wumpusFound = true;
					Wumpus_X = x+1;
					Wumpus_Y = y;
				}
			}
		}
		
		if(x-1 < Xmax && x-1 >= 0){
			if(myAgentWorld[x-1][y].wumpusOdds != -1) {
				myAgentWorld[x-1][y].wumpusOdds++;
				if(myAgentWorld[x-1][y].wumpusOdds >= 2) {
					num_of_risky_cells_count++;
					wumpusFound = true;
					Wumpus_X = x-1;
					Wumpus_Y = y;
				}
			}
		}
		if(y+1 < Ymax && y+1 >= 0){
			if(myAgentWorld[x][y+1].wumpusOdds != -1) {
				myAgentWorld[x][y+1].wumpusOdds++;
				if(myAgentWorld[x][y+1].wumpusOdds >= 2) {
					num_of_risky_cells_count++;
					wumpusFound = true;
					Wumpus_X = x;
					Wumpus_Y = y+1;
				}
			}
		}
		if(y-1 < Ymax && y-1 >= 0){
			if(myAgentWorld[x][y-1].wumpusOdds != -1) {
				myAgentWorld[x][y-1].wumpusOdds++;
				if(myAgentWorld[x][y-1].wumpusOdds >= 2) {
					num_of_risky_cells_count++;
					wumpusFound = true;
					Wumpus_X = x;
					Wumpus_Y = y-1;
				}
			}
		}
		
		// Sanity Check whether we have located wumpus 100% or not
		if(wumpusFound) {
			if(num_of_risky_cells_count > 1) {
				wumpusFound = false;
				Wumpus_X = -1;
				Wumpus_Y = -1;
			}
		}
	}
    public void setDebug(boolean value) {
        debug = value;
    }

    public void beforeAction(Explorer explorer) {
        if (debug) {
            System.out.println(explorer.debug());
        }
		explorer.setPerceptions();
        
    }

    /*public void afterAction(Explorer explorer) {
        // TODO Auto-generated method stub
        if(debug){
            if(explorer.isDead()){
            //end attempt
            }
            else{
                if (explorer.hasGold())
                {
                    explorer.setAction(Action.EXIT);
                }
                else{
                    explorer.setPerceptions();
				//printStatus();
                //the agent is alive and will update any flags it needs to
            }
        }
			//printStatus();



        }
		if (debug) {
            System.out.println(explorer.getLastAction());
            if (explorer.isDead()) {
                System.out.println("GAME OVER!");
            }
        }
        
    }*/
    
    
    
}
