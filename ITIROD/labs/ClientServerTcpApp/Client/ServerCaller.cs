using DataAccess;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Text;

namespace Client
{
    public class ServerCaller
    {
        private const int Port = 8080;
        private const string Server = "127.0.0.1";

        public Response ProcessInput()
        {
            PrintMenu();
            var choise = int.Parse(Console.ReadLine());

            switch (choise)
            {
                case 1:
                    return SendRequest(new Request { Action = CRUDAction.Create, JsonRequest = JsonConvert.SerializeObject(EditPayroll()) });

                case 2:
                    return SendRequest(new Request { Action = CRUDAction.Delete, JsonRequest = GetIdToDelete() });

                case 3:
                    return SendRequest(new Request { Action = CRUDAction.Update, JsonRequest = JsonConvert.SerializeObject(EditPayroll()) });

                case 4:
                    return SendRequest(new Request { Action = CRUDAction.Read });

                case 5:
                    Console.Clear();
                    return null;

                case 6:
                    return SendRequest(GetFilter());

                default:
                    return null;
            }
        }
        //запустить клиернта без сервера,если нет сервера ,спросить,повторить попытку? 

        private Response SendRequest(Request request)
        {
            using var client = new TcpClient();
            bool flag = false;
            while (!flag)
            {
                try
                {
                    client.Connect(Server, Port);
                    flag = true;
                }
                catch (Exception e)
                {
                    Console.WriteLine("Not connect to cerver...");
                    Console.WriteLine("Try again?(Yes/No)");
                    if (Console.ReadLine().ToLower() != "yes") Environment.Exit(0);

                }
            }
            using var stream = client.GetStream();
            var serializedRequest = JsonConvert.SerializeObject(request);
            stream.Write(Encoding.UTF8.GetBytes(serializedRequest));

            if (request.Action == CRUDAction.Read || request.Action == CRUDAction.Filter)
            {
                var buffer = new byte[2048];
                var builder = new StringBuilder();
                var bytes = 0;
                do
                {
                    bytes = stream.Read(buffer, 0, buffer.Length);
                    builder.Append(Encoding.UTF8.GetString(buffer, 0, bytes));
                }
                while (stream.DataAvailable);

                var message = builder.ToString();
                var response = JsonConvert.DeserializeObject<Response>(message);

                return response;
            }

            return new Response { IsSuccess = true };
        }

        private static void PrintMenu()
        {
            Console.Clear();
            Console.WriteLine("Choose:");
            Console.WriteLine("1.Add record");
            Console.WriteLine("2.Delete record");
            Console.WriteLine("3.Update record");
            Console.WriteLine("4.Get all record");
            Console.WriteLine("5.Clear console");
            Console.WriteLine("6.Filter");
        }

        private static GeoDictionary EditPayroll()
        {
            Console.WriteLine("Input counry name");
            var country = Console.ReadLine();

            Console.WriteLine("Input square(mln sq.km)");
            var square = double.Parse(Console.ReadLine());
            
            Console.WriteLine("Input population(mln)");
            var population = double.Parse(Console.ReadLine());

            Console.WriteLine("Input continent name");
            var continent = Console.ReadLine();

            Console.WriteLine("Input capital of the country");
            var capital = Console.ReadLine();

            return new GeoDictionary
            {
                Country = country,
                Square = square,
                Population = population,
                Continent = continent,
                Capital = capital
            };

        }

        private static string GetIdToDelete()
        {
            Console.WriteLine("Input country");

            return Console.ReadLine();
        }

        private static Request GetFilter()
        {
            Console.WriteLine("Input capital");
            var capital = Console.ReadLine();

            return new Request { Action = CRUDAction.Filter, Capital = capital };
        }
    }
}
