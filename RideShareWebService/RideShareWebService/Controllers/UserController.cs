using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using RideShareWebService.BL;
using RideShareWebService.Models;

namespace RideShareWebService.Controllers
{
    public class UserController : ApiController
    {
        private UserBL user_bl;
        public UserController()
        {
            this.user_bl = new UserBL();
        }

        // GET api/user/5
        public List<User> Get()
        {
            return user_bl.GetAllUsers();
        }

        public HttpResponseMessage Post(User user)
        {
            this.user_bl.RegisterUser(user);

            var response = Request.CreateResponse<User>(System.Net.HttpStatusCode.Created, user);

            return response;
        }





        //// GET api/user
        //public IEnumerable<string> Get()
        //{
        //    return new string[] { "value1", "value2" };
        //}

        // POST api/user
        public void Post([FromBody]string value)
        {
        }

        // PUT api/user/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/user/5
        public void Delete(int id)
        {
        }
    }
}
