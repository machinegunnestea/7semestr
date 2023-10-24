using System;
using System.Collections.Generic;
using System.Text;

namespace DataAccess
{
    public class Response
    {
        public bool IsSuccess { get; set; }

        public List<GeoDictionary> GeoDictionary { get; set; }
    }
}
