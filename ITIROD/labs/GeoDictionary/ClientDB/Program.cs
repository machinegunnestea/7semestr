using System;

namespace ClientDB
{
    internal class Program
    {
        static void Main(string[] args)
        {
            Client client = new Client();
            client.Run();

            Console.ReadKey();
        }
    }
}
