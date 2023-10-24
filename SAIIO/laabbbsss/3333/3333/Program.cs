using Google.OrTools.LinearSolver;
using System;


namespace _3333
{
    class Program
    {
        static void Main(string[] args)
        {
            // Create the solver
            Solver solver = Solver.CreateSolver("SCIP");

            // Define the variables
            Variable x1 = solver.MakeIntVar(0, int.MaxValue, "x1");
            Variable x2 = solver.MakeIntVar(0, int.MaxValue, "x2");

            // Add the constraints
            solver.Add(x1 + x2 <= 13);
            solver.Add(x1 - x2 <= 6);
            solver.Add(-3 * x1 + x2 <= 9);

            // Set the objective
            Objective objective = solver.Objective();
            objective.SetCoefficient(x1, 3);
            objective.SetCoefficient(x2, 2);
            objective.SetMaximization();

            // Solve the problem
            Solver.ResultStatus resultStatus = solver.Solve();

            // Check if a solution was found
            if (resultStatus == Solver.ResultStatus.OPTIMAL)
            {
                Console.WriteLine("Solution found:");
                Console.WriteLine("x1 = " + solver. Value(x1));
                Console.WriteLine("x2 = " + solver.Value(x2));
                Console.WriteLine("Objective value: " + objective.Value());
            }
            else
            {
                Console.WriteLine("Solution not found.");
            }

            Console.ReadLine();
        }
    }
}