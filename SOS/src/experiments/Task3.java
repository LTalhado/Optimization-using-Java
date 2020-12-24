package experiments;

import interfaces.Experiment;
import interfaces.Algorithm;
import algorithms.CMAES;
import algorithms.CMAES_11;
import algorithms.RI_CMAES11;


public class Task3 extends Experiment
{
	
	public Task3(int probDim) {
		// TODO Auto-generated constructor stub
		super(probDim,"Task3");
		
		Algorithm a;// ///< A generic optimiser.
		
		
		a = new RI_CMAES11();
		add(a);
		
		a = new CMAES(); //N.B. this algorithm makes use of "generateRandomSolution" that has been implemented.
		add(a);
		
		
		a = new CMAES_11();
		add(a);
	}

}