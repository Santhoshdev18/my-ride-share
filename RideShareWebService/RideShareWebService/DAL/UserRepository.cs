using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using RideShareWebService.Models;


namespace RideShareWebService.DAL
{
    public class UserRepository
    {
        private const string CacheKey = "UserStore";
        RideShareEntities db = new RideShareEntities();
        
        public UserRepository()
        {
            var ctx = HttpContext.Current;

            if (ctx != null)
            {
                if (ctx.Cache[CacheKey] == null)
                {
                    var users = db.Users.ToList();                    
                    ctx.Cache[CacheKey] = users;
                }
            }
        }
        // comm
        public List<User> GetAllUsers()
        {
            var ctx = HttpContext.Current;

            if (ctx != null)
            {
                // if cache is not null return from cache
                return (List<User>)ctx.Cache[CacheKey];
            }

            // if not data in cache return from database
            return db.Users.ToList();
        }

        public bool SaveUser(User user)
        {
            var ctx = HttpContext.Current;

            //if (ctx != null)
            //{
                try
                {
                    // add information to the cache
                    var currentData = ((List<User>)ctx.Cache[CacheKey]).ToList();
                    currentData.Add(user);
                    ctx.Cache[CacheKey] = currentData.ToArray();

                    // add data to the database
                    db.Users.Add(user);
                    db.SaveChanges();

                    return true;
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex.ToString());
                    return false;
                }
           // }
            //else
            //{
            //    try
            //    {
            //        // add information to the cache
            //        var currentData = ((User[])ctx.Cache[CacheKey]).ToList();
            //        currentData.Add(user);
            //        ctx.Cache[CacheKey] = currentData.ToArray();

            //        // add data to the database
            //        db.Users.Add(user);
            //        db.SaveChanges();

            //        return true;
            //    }
            //    catch (Exception ex)
            //    {
            //        Console.WriteLine(ex.ToString());
            //        return false;
            //    }
            //}

            return false;
        }
    }
}