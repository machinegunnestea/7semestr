using Microsoft.SolverFoundation.Services;
using System.Reflection;

static void Main(string[] args)
{
    SolverContext context = SolverContext.GetContext();
    Model model = context.CreateModel();

    // Создание переменных
    Decision x1 = new Decision(Domain.IntegerNonnegative, "x1");
    Decision x2 = new Decision(Domain.IntegerNonnegative, "x2");

    // Добавление переменных в модель
    model.AddDecision(x1);
    model.AddDecision(x2);

    // Создание ограничений
    model.AddConstraint("c1", x1 + x2 <= 13);
    model.AddConstraint("c2", x1 - x2 <= 6);
    model.AddConstraint("c3", -3 * x1 + x2 <= 9);

    // Создание целевой функции
    Goal goal = model.AddGoal("goal", GoalKind.Maximize, 3 * x1 + 2 * x2);

    // Решение модели
    Solution solution = context.Solve();

    // Проверка наличия решения
    if (solution.Quality != SolverQuality.Infeasible && solution.Quality != SolverQuality.Unbounded)
    {
        Console.WriteLine("Решение найдено:");
        Console.WriteLine("x1 = " + x1.ToDouble());
        Console.WriteLine("x2 = " + x2.ToDouble());
        Console.WriteLine("Значение целевой функции: " + goal.ToDouble());
    }
    else
    {
        Console.WriteLine("Решение не найдено.");
    }

    Console.ReadLine();
}
