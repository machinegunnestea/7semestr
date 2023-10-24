using System;
using System.Collections.Generic;
using System.Text;
using System.Xml.Linq;

namespace DataAccess
{
    public  class GeoDictionary
    {
        public string Country{ get; set; }  
        public double Square { get; set; }
        public double Population { get; set; }
        public string Continent { get; set; }
        public string Capital { get; set; }
        public override string ToString()
        {
            return $"{Country} {Square} {Population} {Continent} {Capital}";
        }
    }
}
