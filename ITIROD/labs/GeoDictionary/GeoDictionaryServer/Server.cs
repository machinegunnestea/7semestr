using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace GeoDictionaryServer
{
    class Server
    {
        private const int Port = 12345;
        private const string DataFile = "data.csv";
        private const string DataDBFile = "D:\\uni\\7sem\\ITIROD\\labs\\GeoDictionary\\GeoDictionaryServer\\geoDict.db";

        private GeoDictionaryLibrary.GeoDictionary _geoDictionary;

        public Server()
        {
            _geoDictionary = new GeoDictionaryLibrary.GeoDictionary(DataFile, DataDBFile);
        }

        public async Task Start()
        {
            var listener = new TcpListener(IPAddress.Any, Port);
            listener.Start();
            Console.WriteLine("Server started. Waiting for connections...");

            while (true)
            {
                var client = await listener.AcceptTcpClientAsync();
                Console.WriteLine("Client connected.");

                Task.Run(() => HandleClientAsync(client));
            }
        }

        private async Task HandleClientAsync(TcpClient client)
        {
            var clientHandler = new ClientHandler(client, _geoDictionary);
            clientHandler.HandleClient();
        }
    }
}