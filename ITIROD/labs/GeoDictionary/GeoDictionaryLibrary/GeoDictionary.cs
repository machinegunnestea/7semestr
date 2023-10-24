using Microsoft.Data.Sqlite;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace GeoDictionaryLibrary
{
    public class GeoDictionary
    {
        private readonly string _dataFile;
        private List<string[]> _data;
        private string _databaseFile;

        public GeoDictionary(string dataFile, string databaseFile)
        {
            _dataFile = dataFile;
            _databaseFile = databaseFile;
            _data = LoadData();
            CreateDatabase();
        }

        private List<string[]> LoadData()
        {
            var data = new List<string[]>();
            using (var reader = new StreamReader(_dataFile))
            {
                while (!reader.EndOfStream)
                {
                    var line = reader.ReadLine();
                    var row = line.Split(',');
                    data.Add(row);
                }
            }
            return data;
        }

        private void SaveData()
        {
            using (var writer = new StreamWriter(_dataFile))
            {
                foreach (var row in _data)
                {
                    var line = string.Join(",", row);
                    writer.WriteLine(line);
                }
            }
        }

        public void CreateCountry(string countryName, string area, string population, string continent, string capital)
        {
            var country = new string[] { countryName, area, population, continent, capital };
            _data.Add(country);
            SaveData();
        }

        public string[] ReadCountry(string capitalName)
        {
            return _data.FirstOrDefault(country => country[4] == capitalName);
        }

        public bool UpdateCountry(string countryName, string area, string population, string continent, string capital)
        {
            var country = _data.FirstOrDefault(c => c[0] == countryName);
            if (country != null)
            {
                country[1] = area;
                country[2] = population;
                country[3] = continent;
                country[4] = capital;
                SaveData();
                return true;
            }
            return false;
        }
        public bool DeleteCountry(string countryName)
        {
            var country = _data.FirstOrDefault(c => c[0] == countryName);
            if (country != null)
            {
                _data.Remove(country);
                SaveData();
                return true;
            }
            return false;
        }

        public List<string[]> FilterCountriesByContinent(string continent)
        {
            return _data.Where(country => country[3] == continent).ToList();
        }
        private void CreateDatabase()
        {
            if (!File.Exists(_databaseFile))
            {
                using (var connection = new SqliteConnection($"Data Source={_databaseFile}"))
                {
                    connection.Open();

                    string query = "CREATE TABLE IF NOT EXISTS Countries (Name TEXT PRIMARY KEY, Area TEXT, Population TEXT, Continent TEXT, Capital TEXT)";
                    using (var command = new SqliteCommand(query, connection))
                    {
                        command.ExecuteNonQuery();
                    }

                    foreach (var country in _data)
                    {
                        string insertQuery = "INSERT INTO Countries (Name, Area, Population, Continent, Capital) VALUES (@Name, @Area, @Population, @Continent, @Capital)";
                        using (var insertCommand = new SqliteCommand(insertQuery, connection))
                        {
                            insertCommand.Parameters.AddWithValue("@Name", country[0]);
                            insertCommand.Parameters.AddWithValue("@Area", country[1]);
                            insertCommand.Parameters.AddWithValue("@Population", country[2]);
                            insertCommand.Parameters.AddWithValue("@Continent", country[3]);
                            insertCommand.Parameters.AddWithValue("@Capital", country[4]);
                            insertCommand.ExecuteNonQuery();
                        }
                    }
                }
            }
        }

        public void CreateCountryDB(string countryName, string area, string population, string continent, string capital)
        {
            var country = new string[] { countryName, area, population, continent, capital };
            _data.Add(country);
            SaveData();

            using (var connection = new SqliteConnection($"Data Source={_databaseFile};"))
            {
                connection.Open();

                string insertQuery = "INSERT INTO Countries (Name, Area, Population, Continent, Capital) VALUES (@Name, @Area, @Population, @Continent, @Capital)";
                using (var command = new SqliteCommand(insertQuery, connection))
                {
                    command.Parameters.AddWithValue("@Name", countryName);
                    command.Parameters.AddWithValue("@Area", area);
                    command.Parameters.AddWithValue("@Population", population);
                    command.Parameters.AddWithValue("@Continent", continent);
                    command.Parameters.AddWithValue("@Capital", capital);
                    command.ExecuteNonQuery();
                }
            }
        }

        public string[] ReadCountryDB(string capitalName)
        {
            using (var connection = new SqliteConnection($"Data Source={_databaseFile};"))
            {
                connection.Open();

                string query = "SELECT * FROM Countries WHERE Capital = @Capital";
                using (var command = new SqliteCommand(query, connection))
                {
                    command.Parameters.AddWithValue("@Capital", capitalName);

                    using (var reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            var country = new string[]
                            {
                                reader.GetString(0),
                                reader.GetString(1),
                                reader.GetString(2),
                                reader.GetString(3),
                                reader.GetString(4)
                            };
                            return country;
                        }
                    }
                }
            }

            return null;
        }
        public bool UpdateCountryDB(string countryName, string area, string population, string continent, string capital)
        {
            var country = _data.FirstOrDefault(c => c[0] == countryName);
            if (country != null)
            {
                country[1] = area;
                country[2] = population;
                country[3] = continent;
                country[4] = capital;
                SaveData();

                using (var connection = new SqliteConnection($"Data Source={_databaseFile};"))
                {
                    connection.Open();

                    string updateQuery = "UPDATE Countries SET Area = @Area, Population = @Population, Continent = @Continent, Capital = @Capital WHERE Name = @Name";
                    using (var command = new SqliteCommand(updateQuery, connection))
                    {
                        command.Parameters.AddWithValue("@Area", area);
                        command.Parameters.AddWithValue("@Population", population);
                        command.Parameters.AddWithValue("@Continent", continent);
                        command.Parameters.AddWithValue("@Capital", capital);
                        command.Parameters.AddWithValue("@Name", countryName);
                        command.ExecuteNonQuery();
                    }
                }
            }
            return false;
        }

        public bool DeleteCountryDB(string countryName)
        {
            var country = _data.FirstOrDefault(c => c[0] == countryName);
            if (country != null)
            {
                _data.Remove(country);
                SaveData();

                using (var connection = new SqliteConnection($"Data Source={_databaseFile};"))
                {
                    connection.Open();

                    string deleteQuery = "DELETE FROM Countries WHERE Name = @Name";
                    using (var command = new SqliteCommand(deleteQuery, connection))
                    {
                        command.Parameters.AddWithValue("@Name", countryName);
                        command.ExecuteNonQuery();
                    }
                }

                return true;
            }
            return false;
        }
    }
}