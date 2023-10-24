using System;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace GeoDictionaryServer
{
    public class ClientHandler
    {
        private TcpClient _client;
        private GeoDictionaryLibrary.GeoDictionary _geoDictionary;

        public ClientHandler(TcpClient client, GeoDictionaryLibrary.GeoDictionary geoDictionary)
        {
            _client = client;
            _geoDictionary = geoDictionary;
        }

        public async Task HandleClient()
        {
            var stream = _client.GetStream();
            var data = new byte[1024];
            var receivedBytes = stream.Read(data, 0, data.Length);
            var request = Encoding.ASCII.GetString(data, 0, receivedBytes);
            var response = ProcessRequest(request);
            var responseData = Encoding.ASCII.GetBytes(response);
            stream.Write(responseData, 0, responseData.Length);
            _client.Close();
        }

        private string ProcessRequest(string request)
        {
            var parts = request.Split('|');
            var operation = parts[0];
            var result = string.Empty;

            switch (operation)
            {
                case "CREATE":
                    var countryName = parts[1];
                    var area = parts[2];
                    var population = parts[3];
                    var continent = parts[4];
                    var capital = parts[5];
                    _geoDictionary.CreateCountry(countryName, area, population, continent, capital);
                    result = "Country created successfully.";
                    break;
                case "READ":
                    var country = _geoDictionary.ReadCountry(parts[1]);
                    if (country != null)
                    {
                        result = string.Join(",", country);
                    }
                    else
                    {
                        result = "Country not found.";
                    }
                    break;
                case "UPDATE":
                    countryName = parts[1];
                    area = parts[2];
                    population = parts[3];
                    continent = parts[4];
                    capital = parts[5];
                    var updated = _geoDictionary.UpdateCountry(countryName, area, population, continent, capital);
                    if (updated)
                    {
                        result = "Country updated successfully.";
                    }
                    else
                    {
                        result = "Country not found.";
                    }
                    break;
                case "DELETE":
                    countryName = parts[1];
                    var deleted = _geoDictionary.DeleteCountry(countryName);
                    if (deleted)
                    {
                        result = "Country deleted successfully.";
                    }
                    else
                    {
                        result = "Country not found.";
                    }
                    break;
                case "FILTER":
                    continent = parts[1];
                    var filteredCountries = _geoDictionary.FilterCountriesByContinent(continent);
                    result = string.Join(Environment.NewLine, filteredCountries);
                    break;
                default:
                    result = "Invalid operation.";
                    break;
            }

            return result;
        }
    }
}