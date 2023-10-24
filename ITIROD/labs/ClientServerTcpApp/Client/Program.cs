using DataAccess;
using System;

namespace Client
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var sc = new ServerCaller();
            while (true)
            {
                var response = sc.ProcessInput();
                PrintResult(response);
            }
        }

        private static void PrintResult(Response response)
        {
            if (response != null)
            {
                if (!response.IsSuccess)
                {
                    Console.WriteLine("Unable to delete");
                }
                else if (response.GeoDictionary != null)
                {
                    foreach (var record in response.GeoDictionary)
                    {
                        Console.WriteLine(record);
                    }
                }
            }
        }
    }
}
