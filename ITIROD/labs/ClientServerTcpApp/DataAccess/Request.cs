using System;
using System.Collections.Generic;
using System.Text;

namespace DataAccess
{
    public class Request
    {
        public CRUDAction Action { get; set; }

        public string JsonRequest { get; set; }

        public string Capital { get; set; }
    }
}
