package experiments;

import interfaces.Experiment;
import interfaces.Algorithm;
import interfaces.Problem;
//import algorithms.*; // For Task 3 
import algorithms.ISPO; 
import algorithms.CMAES;
import algorithms.Salgor;
import algorithms.TASK3;




import benchmarks.BaseFunctions.Alpine; 
import benchmarks.BaseFunctions.Ackley;
import benchmarks.BaseFunctions.Rosenbrock;
import benchmarks.BaseFunctions.Sphere;
import benchmarks.BaseFunctions.Schwefel;
import benchmarks.BaseFunctions.Rastigin;
import benchmarks.BaseFunctions.Michalewicz;

public class Test extends Experiment
{
	
	public Test(int probDim)
	{
		super(probDim,"TEST");
			
		Algorithm a;// ///< A generic optimiser.
	    Problem p;// ///< A generic problem.

		a = new ISPO();
		a.setParameter("p0", 1.0);
		a.setParameter("p1", 10.0);
		a.setParameter("p2", 2.0);
		a.setParameter("p3", 4.0);
		a.setParameter("p4", 1e-5);
		a.setParameter("p5", 30.0);
		add(a); //add it to the list

		a = new CMAES(); //N.B. this algorithm makes use of "generateRandomSolution" that has been implemented.
		add(a);
				
		a = new Salgor();
		a.setParameter("Al", 0.5);	
		add(a);
		
		a = new TASK3(); // - PSO
		a.setParameter("p0", 1.0);
		a.setParameter("p1", 10.0);
		a.setParameter("p2", 2.0);
		a.setParameter("p3", 4.0);
		add(a);
		
        //double[] bounds = {-100, 100};
		//p = new Ackley(problemDimension, bounds);
		p = new Ackley(probDim);
		add(p);//add it to the list
		
		p = new Alpine(probDim);
		add(p);//add it to the list
		
		p = new Rosenbrock(probDim);
		add(p);
		// Sphere
		p = new Sphere(probDim);
		add(p);
		
		p = new Schwefel(probDim);
		add(p);
		
		p = new Rastigin(probDim);
		add(p);
		
		p = new Michalewicz(probDim);
		add(p);

	}
}
