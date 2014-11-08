//------------------------------------------------------------------------------
// <auto-generated>
//    This code was generated from a template.
//
//    Manual changes to this file may cause unexpected behavior in your application.
//    Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace RideShareWebService.Models
{
    using System;
    using System.Collections.Generic;
    
    public partial class Ride
    {
        public Ride()
        {
            this.RiderInfoes = new HashSet<RiderInfo>();
        }
    
        public string ride_id { get; set; }
        public string from_location { get; set; }
        public string to_location { get; set; }
        public string ride_type { get; set; }
        public Nullable<int> available_seats { get; set; }
        public string status { get; set; }
        public Nullable<System.DateTime> start_date { get; set; }
        public Nullable<System.TimeSpan> start_time { get; set; }
        public Nullable<System.TimeSpan> estimated_end_time { get; set; }
        public string cost_status { get; set; }
        public string comments { get; set; }
        public Nullable<decimal> rating { get; set; }
        public string driver_id { get; set; }
    
        public virtual ICollection<RiderInfo> RiderInfoes { get; set; }
    }
}
