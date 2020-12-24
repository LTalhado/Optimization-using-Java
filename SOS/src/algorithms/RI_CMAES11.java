
/**
Copyright (c) 2018, Fabio Caraffini (fabio.caraffini@gmail.com, fabio.caraffini@dmu.ac.uk)
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met: 

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer. 
2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution. 

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those
of the authors and should not be interpreted as representing official policies, 
either expressed or implied, of the FreeBSD Project.
*/package algorithms;


import algorithms.CMAES_11;

import static utils.algorithms.Misc.generateRandomSolution;
import static utils.MatLab.min;
import utils.RunAndStore.FTrend;
import utils.random.RandUtils;

import interfaces.Algorithm;
import interfaces.Problem;

public class RI_CMAES11 extends Algorithm
{	
	boolean verbose = false;

	@Override
	public FTrend execute(Problem problem, int maxEvaluations) throws Exception
	{
		int problemDimension = problem.getDimension();
		double[][] bounds = problem.getBounds();
		
		int i;

		double[] best; 
		double fBest;

		FTrend FT = new FTrend();
		
		i = 0;
		if (initialSolution != null)
		{
			best = initialSolution;
			fBest = initialFitness;
		}
		else
		{
			best = generateRandomSolution(bounds, problemDimension);
			fBest = problem.f(best);
			i++;
		}
		FT.add(i, fBest);
		
		
		// INITIALISE MEMES//
		
	
		double globalCR = getParameter("p0").doubleValue(); //0.95
	
		CMAES_11 cma11 = new CMAES_11();
		cma11.setParameter("p0",getParameter("p1").doubleValue()); // 2/11
		cma11.setParameter("p1",getParameter("p2").doubleValue());  // 1/12
		cma11.setParameter("p2",getParameter("p3").doubleValue()); // 0.44
		cma11.setParameter("p3",getParameter("p4").doubleValue()); // 1 --> problem dependent!!
		
		
		double maxB = getParameter("p5").doubleValue();//0.2
		
		FTrend ft = null;
		
		
		double[] xTemp;
		double fTemp;
		
		while (i < maxEvaluations)
		{
			xTemp = generateRandomSolution(bounds, problemDimension);
			xTemp = crossOverExp(best, xTemp, globalCR);
			fTemp = problem.f(xTemp);
			i++;
			if(fTemp<fBest)
			{
				fBest = fTemp;
				for(int n=0;n<problemDimension; n++)
					best[n] = xTemp[n];
				FT.add(i, fBest);
			}
			
				if (verbose) System.out.println("C start point: "+fBest);
				cma11.setInitialSolution(xTemp);
				cma11.setInitialFitness(fTemp);
				int  budget = (int)(min(maxB*maxEvaluations, maxEvaluations-i));
				ft = cma11.execute(problem, budget);
				xTemp = cma11.getFinalBest();
				fTemp = ft.getF().lastElement();
				if (verbose) System.out.println("C final point: "+fBest);
				FT.merge(ft, i);
				i+=budget;
				if (verbose) System.out.println("C appended point: "+FT.getF().lastElement());
				if(fTemp<fBest)
				{
					fBest = fTemp;
					for(int n=0;n<problemDimension; n++)
						best[n] = xTemp[n];
				}
			
		}
		
		FT.add(i,fBest);
		finalBest = best;
		return FT;
			
			
	}
	
	
	
	   /**
			* Exponential crossover
			* 
			* @param x first parent solution.
			* @param y second parent solution.
			* @param CR crossover rate.
			* @return x_off offspring solution.
			*/
			public static double[] crossOverExp(double[] x, double[] y, double CR)
			{
				int n = x.length;
				int startIndex = RandUtils.randomInteger(n-1);
				int index = startIndex;

				double[] x_off = new double[n];
				for (int i = 0; i < n; i++)
					x_off[i] = x[i];
				          
				x_off[index] = y[index];

				index = index + 1;
				while ((RandUtils.random() <= CR) && (index != startIndex))
				{
					if (index >= n)
						index = 0;
					x_off[index] = y[index];
					index++;
				}

				return x_off;
			}
		
	
	
}
			
			
						