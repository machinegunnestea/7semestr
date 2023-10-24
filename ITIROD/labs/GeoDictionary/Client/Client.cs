using System;
using System.Net.Sockets;
using System.Text;

namespace GeoDictionaryClient
{
    class Client
    {
        private const string ServerIp = "127.0.0.1";
        private const int Port = 12345;

        public void Run()
        {
            Console.Clear();
            Console.WriteLine("Choose:");
            Console.WriteLine("1.Add record");
            Console.WriteLine("2.Delete record");
            Console.WriteLine("3.Update record");
            Console.WriteLine("4.Search by capital record");
            var choise = int.Parse(Console.ReadLine());

            var request = string.Empty;

            switch (choise)
            {
                case 1:
                    request = CreateCountryRequest();
                    break;
                case 2:
                    request = DeleteCountryRequest();
                    break;
                case 3:
                    request = UpdateCountryRequest();
                    break;
                case 4:
                    request = SearchCountryRequest();
                    break;
                default:
                    Console.WriteLine("Invalid operation.");
                    return;
            }

            var response = SendRequestToServer(request);
            Console.WriteLine($"Server response: {response}");
        }

        private string CreateCountryRequest()
        {
            Console.WriteLine("Enter country name:");
            var countryName = Console.ReadLine();
            Console.WriteLine("Enter area (in sq.km):");
            var area = Console.ReadLine();
            Console.WriteLine("Enter population (in million):");
            var population = Console.ReadLine();
            Console.WriteLine("Enter continent");
            var continent = Console.ReadLine();
            Console.WriteLine("Enter capital:");
            var capital = Console.ReadLine();

            return $"CREATE|{countryName}|{area}|{population}|{continent}|{capital}";
        }
        private string DeleteCountryRequest()
        {
            Console.WriteLine("Enter country name:");
            var countryName = Console.ReadLine();

            return $"DELETE|{countryName}";
        }

        private string SearchCountryRequest()
        {
            Console.WriteLine("Enter capital name:");
            var capitalName = Console.ReadLine();

            return $"READ|{capitalName}";
        }

        private string UpdateCountryRequest()
        {
            Console.WriteLine("Enter country name:");
            var countryName = Console.ReadLine();
            Console.WriteLine("Enter new area (in sq.km):");
            var area = Console.ReadLine();
            Console.WriteLine("Enter new population (in million):");
            var population = Console.ReadLine();
            Console.WriteLine("Enter new continent:");
            var continent = Console.ReadLine();
            Console.WriteLine("Enter new capital:");
            var capital = Console.ReadLine();

            return $"UPDATE|{countryName}|{area}|{population}|{continent}|{capital}";
        }

        private string SendRequestToServer(string request)
        {
            var client = new TcpClient(ServerIp, Port);
            var stream = client.GetStream();
            var requestData = Encoding.ASCII.GetBytes(request);
            stream.Write(requestData, 0, requestData.Length);

            var responseData = new byte[1024];
            var receivedBytes = stream.Read(responseData, 0, responseData.Length);
            var response = Encoding.ASCII.GetString(responseData, 0, receivedBytes);

            client.Close();

            return response;
        }
    }
}