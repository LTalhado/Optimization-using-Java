package algorithms;
//in this part you can import the functionalities that yuo need to use for implementing your algorithm
import static utils.algorithms.Misc.generateRandomSolution;
import static utils.algorithms.Misc.toro;
import interfaces.Algorithm;
import interfaces.Problem;
import utils.RunAndStore.FTrend;
/**
 * Intelligent Single Particle Optimization
 */
public class Salgor extends Algorithm //This class implements the algorithm. Every algorithm will have to contain its specific implementation within the method "execute". The latter will contain a main loop performing the iterations, and will have to return the fitness trend (including the final best) solution. Look at this examples before implementing your first algorithm.
{
	@Override
	//to implement a different algorithm you'll have to change the content of this function
	public FTrend execute(Problem problem, int maxEvaluations) throws Exception
	{
		// first, we need to define variables for storing the paramters of the algorithm
		double Al = 0.5;	

		//we always need an object of the kynd FTrend (for storing the fitness trend), and variables for storing the dimesionality vlue and the bounds of the problem as showed below
		FTrend FT = new FTrend();
		int problemDimension = problem.getDimension(); 
		double[][] bounds = problem.getBounds();


		// particle (the solution, i.e. "x")
		double particle[];  // = XS
		double fParticle; //fitness value, i.e. "f(x)"
		double XF [];  // XF = XS
		
		double prev_fParticle;
		int i = 0;
		// === keep it untouched ==//
		// initial solution
		if (initialSolution != null)
		{
			particle = initialSolution;
		    fParticle = initialFitness;
		}
		else//random intitial guess
			
		{
			/// call here
			particle = generateRandomSolution(bounds, problemDimension);
			fParticle = problem.f(particle);
			i++;
		}
		//store the initital guess
		FT.add(0, fParticle);
		
		prev_fParticle = fParticle;
		XF = particle;
		
		// variables related aglor
		double UpperB = bounds[1][1];
		double LowerB = bounds[0][0];
		double rad = Al * (UpperB - LowerB);
		
		//main loop
		while (i < maxEvaluations)
		{
		for(int k = 0; k < problemDimension; k++) {
			XF[k] = particle[k] - rad;
			double t_value [] = toro(XF,bounds);
			double Xs_best = problem.f(t_value);
			i++;
			
			if(Xs_best <= fParticle) {
				particle[k] = XF[k];
				fParticle = Xs_best;
				
			}else {
				XF[k] = particle[k];
				XF[k] = particle[k] + (rad / 2);
				Xs_best = problem.f(toro(XF,bounds));
				
				if(Xs_best <= fParticle) {
					particle[k] = XF[k];
					fParticle = Xs_best;
				} else {
					XF[k] = particle[k];
				}
			}
		}
			
			
			if(prev_fParticle == fParticle ) {
				rad = rad / 2;
			}
		}
		
		
		finalBest = particle; //save the final best
		FT.add(i, fParticle);//add it to the txt file (row data)

		
		return FT; //return the fitness trend
	}
}
