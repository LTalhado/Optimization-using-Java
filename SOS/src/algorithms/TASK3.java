package algorithms;

import static utils.MatLab.max;
import static utils.MatLab.min;
//in this part you can import the functionalities that yuo need to use for implementing your algorithm
import static utils.algorithms.Misc.generateRandomSolution;
import static utils.algorithms.Misc.toro;
import interfaces.Algorithm;
import interfaces.Problem;
import utils.RunAndStore.FTrend;
import utils.random.RandUtils;

/**
 * Intelligent Single Particle Optimization
 */
public class TASK3 extends Algorithm // This class implements the algorithm. Every algorithm will have to contain its
// specific implementation within the method "execute". The latter will contain
// a main loop performing the iterations, and will have to return the fitness
										// trend (including the final best) solution. Look at this examples before
										// implementing your first algorithm.
{
	@Override
	// to implement a different algorithm you'll have to change the content of this
	// function
	public FTrend execute(Problem problem, int maxEvaluations) throws Exception {
		// first, we need to define variables for storing the paramters of the algorithm
		// Size of Swarm is 100
		
		double g = getParameter("p0").intValue();
		double S = getParameter("p1");
		double P = getParameter("p2");	
		double c2 = getParameter("p3");
		
		
		// int index [];
		// int index [] = new int[NP-1];
		// index = RandUtils.randomPermutation(index);

		// we always need an object of the kynd FTrend (for storing the fitness trend),
		// and variables for storing the dimesionality vlue and the bounds of the
		// problem as showed below
		FTrend FT = new FTrend();
		int problemDimension = problem.getDimension();
		double[][] bounds = problem.getBounds();

		// particle (the solution, i.e. "x")
		double particle[] = new double[problemDimension]; // NP
		double fParticle; // fitness value, i.e. "f(x)"

		int i = 0;
		// === keep it untouched ==//
		// initial solution
		if (initialSolution != null) {
			particle = initialSolution;
			fParticle = initialFitness;
		} else// random intitial guess

		{
			/// call here
			particle = generateRandomSolution(bounds, problemDimension);
			particle = toro(particle, bounds);
			fParticle = problem.f(particle);
			i++;
		}
		// store the initital guess
		FT.add(0, fParticle);

		// PSO varibales
		// temp variables
		// Old fitness = newfitness
		double prev_fParticle = fParticle;
		double velocity = 0;
		double gblparticle = fParticle; // global value xgb
		double particle_best = problem.f(particle);
		double U = RandUtils.uniform(0, 1);
		double C = 0;
		
		for (int j = 0; j < problemDimension; j++) {

			// Particle velocity
			velocity = Math.random() * -particle[1] / 3 - particle[1] / 3;

			// Particle position
			particle[j] = Math.random() * particle[0] - particle[1];

			// Intialize particle's best position
			particle_best = particle[j];

			// Updating the global best position
			if (particle_best <= gblparticle|| i == 1) {
				gblparticle = particle[j];

			}

		}

		// main loop budget-//= problem.f(particles);
		while (i < maxEvaluations) {

			for (int n = 0; n < problemDimension; n++) {

				// Update Velocity
				
				velocity = g/Math.pow(n+1,P)*(-0.5+RandUtils.random())+S*C;
				
				// Perturb xi	
				particle[n] = min(max(particle[n], bounds[n][0]), bounds[n][1]); 
				
				
				// Updating the Particles best position
				if (particle[n] < particle_best) {
					particle_best = particle[n];
				}

				// Updating the global best position
				if (particle_best <= gblparticle) {
					gblparticle = particle[n];
				}
				i++;
			}
			
		}

		// Don't touch this !!!
		finalBest = particle; // save the final best
		FT.add(i, fParticle);// add it to the txt file (row data)

		return FT; // return the fitness trend
	}
}
