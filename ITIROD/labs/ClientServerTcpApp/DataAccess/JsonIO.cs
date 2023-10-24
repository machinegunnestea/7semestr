using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;

namespace DataAccess
{
    public class JsonIO
    {
        private readonly string _fileName;
        private object _locker = new object();

        public JsonIO(string fileName)
        {
            _fileName = fileName;
        }

        public void Create(GeoDictionary geoDictionary)
        {
            var list = ReadFromFile();
            list.Add(geoDictionary);

            SaveToFile(list);
        }

        public List<GeoDictionary> Read() => ReadFromFile().OrderBy(x => x.Population).ToList();

        public bool Update(GeoDictionary geoDictionary)
        {
            var list = ReadFromFile();
            var itemToUpdate = list.FirstOrDefault(x => x.Country == geoDictionary.Country);

            if (itemToUpdate is null)
            {
                return false;
            }

            itemToUpdate.Country = geoDictionary.Country;
            itemToUpdate.Population = geoDictionary.Population;
            itemToUpdate.Square = geoDictionary.Square;
            itemToUpdate.Continent = geoDictionary.Continent;
            itemToUpdate.Capital = geoDictionary.Capital;
            SaveToFile(list);

            return true;
        }

        public bool Delete(string country)
        {
            var list = ReadFromFile();
            var itemToDelete = list.FirstOrDefault(x => x.Country == country);

            if (itemToDelete is null)
            {
                return false;
            }

            list.Remove(itemToDelete);
            SaveToFile(list);

            return true;
        }

        public List<GeoDictionary> Filter(string capital) => Read()
            .Where(x => x.Capital == capital)
            .ToList();

        private List<GeoDictionary> ReadFromFile()
        {
            var fileText = File.ReadAllText(_fileName);
            var list = string.IsNullOrWhiteSpace(fileText)
            ? new List<GeoDictionary>()
                : JsonConvert.DeserializeObject<List<GeoDictionary>>(fileText);

            return list;
        }
        //если несколько клиентов одновременно
        private void SaveToFile(List<GeoDictionary> list)
        {
            lock (_locker)
            {
                var result = JsonConvert.SerializeObject(list);
                File.WriteAllText(_fileName, result);
            }
        }
    }
}