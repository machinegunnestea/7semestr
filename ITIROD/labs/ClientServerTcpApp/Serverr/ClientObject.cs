using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Text;
using DataAccess;
using Newtonsoft.Json; 

namespace Server
{
    public class ClientObject
    {
        private readonly TcpClient _client;
        private readonly JsonIO _jsonIO;

        public ClientObject(TcpClient client)
        {
            _client = client;
            _jsonIO = new JsonIO("text.txt");
        }

        public void Process()
        {
            try
            {
                using var stream = _client.GetStream();
                var buffer = new byte[2048];
                while (true)
                {
                    // получаем сообщение
                    var builder = new StringBuilder();
                    int bytes = 0;
                    do
                    {
                        bytes = stream.Read(buffer, 0, buffer.Length);
                        builder.Append(Encoding.UTF8.GetString(buffer, 0, bytes));
                    }
                    while (stream.DataAvailable);

                    var message = builder.ToString();
                    var request = JsonConvert.DeserializeObject<Request>(message);

                    var response = ProcessRequest(request);
                    var serializedResponse = JsonConvert.SerializeObject(response);

                    buffer = Encoding.UTF8.GetBytes(serializedResponse);
                    stream.Write(buffer, 0, buffer.Length);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            finally
            {
                if (_client != null)
                    _client.Close();
            }
        }

        private Response ProcessRequest(Request request)
        {
            if (request is null)
            {
                return null;
            }

            switch (request.Action)
            {
                case CRUDAction.Create:
                    _jsonIO.Create(JsonConvert.DeserializeObject<GeoDictionary>(request.JsonRequest));
                    return new Response() { IsSuccess = true };

                case CRUDAction.Read:
                    return new Response() { IsSuccess = true, GeoDictionary = _jsonIO.Read() };

                case CRUDAction.Update:
                    _jsonIO.Update(JsonConvert.DeserializeObject<GeoDictionary>(request.JsonRequest));
                    return new Response() { IsSuccess = true };

                case CRUDAction.Delete:
                    return new Response() { IsSuccess = _jsonIO.Delete(request.JsonRequest) };

                case CRUDAction.Filter:
                    return new Response() { IsSuccess = true, GeoDictionary = _jsonIO.Filter(request.Capital) };

                default:
                    break;
            }

            return null;
        }
    }
}
