using System;
using System.Dynamic;

namespace GeoDictionaryServer
{
    internal class Program
    {
        static void Main(string[] args)
        {
            var server = new Server();
            server.Start();
            Console.ReadLine();
        }
    }
}
