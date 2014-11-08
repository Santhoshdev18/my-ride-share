using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using RideShareWebService.DAL;
using RideShareWebService.Models;

namespace RideShareWebService.BL
{
    public class UserBL
    {
        UserRepository user_repository = new UserRepository();

        public List<User> GetAllUsers()
        {
            return user_repository.GetAllUsers();
        }

        public bool RegisterUser(User user)
        {
            return user_repository.SaveUser(user);
        }
    }
}