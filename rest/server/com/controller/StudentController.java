package com.controller;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.regex.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import com.entity.Student;
@Path("Services")
public class StudentController {


@GET
@Path("/SendEmail/{addr}/{msg}")
@Produces(MediaType.TEXT_PLAIN)
//use url like"loaclhost:8080/REST3/rest/Services/SendEmail/addr/msg "
public String SendEmail(@PathParam("addr") String addr,@PathParam("msg") String msg)
{
	Student student = new Student();
	String str = student.SendEmail(addr, msg) ;
    return str;
}

@GET
@Path("/SendEmailBatch/{msg}")
@Produces(MediaType.TEXT_PLAIN)   
//use url like"loaclhost:8080/REST3/rest/Services/SendEmailBatch/msg/addrs=*,*, "
public String SendEmailBatch(@QueryParam("addrs") List<String> addrs,@PathParam("msg") String msg)
{
    Student student = new Student();
    String str = student.SendEmailBatch(addrs, msg) ;
    return str;
}

@GET
@Path("/valid/{addr}")
@Produces(MediaType.TEXT_PLAIN)
//use url like"loaclhost:8080/REST3/rest/Services/valid/addr"
public String ValidateEmailAddress(@PathParam("addr") String addr)
{
	Student student = new Student();
	String str = student.ValidateEmailAddress(addr);
	return str;

}
}
