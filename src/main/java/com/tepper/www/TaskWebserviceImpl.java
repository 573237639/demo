package com.tepper.www;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * This class was generated by Apache CXF 3.1.8
 * 2016-12-16T20:14:20.682+08:00
 * Generated source version: 3.1.8
 * 
 */
@WebService(targetNamespace = "http://www.tepper.com", name = "TaskWebserviceImpl")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface TaskWebserviceImpl {

    @WebResult(name = "return", targetNamespace = "http://www.tepper.com", partName = "return")
    @WebMethod
    public java.lang.String getTaskProcess(
        @WebParam(partName = "source", name = "source")
        java.lang.String source,
        @WebParam(partName = "starttime", name = "starttime")
        java.lang.String starttime,
        @WebParam(partName = "endtime", name = "endtime")
        java.lang.String endtime
    );

    @WebResult(name = "return", targetNamespace = "http://www.tepper.com", partName = "return")
    @WebMethod
    public java.lang.String getTaskSchedule(
        @WebParam(partName = "source", name = "source")
        java.lang.String source,
        @WebParam(partName = "starttime", name = "starttime")
        java.lang.String starttime,
        @WebParam(partName = "endtime", name = "endtime")
        java.lang.String endtime
    );

    @WebResult(name = "return", targetNamespace = "http://www.tepper.com", partName = "return")
    @WebMethod
    public java.lang.String getTaskFallback(
        @WebParam(partName = "source", name = "source")
        java.lang.String source,
        @WebParam(partName = "startdate", name = "startdate")
        java.lang.String startdate,
        @WebParam(partName = "enddate", name = "enddate")
        java.lang.String enddate
    );

    @WebResult(name = "return", targetNamespace = "http://www.tepper.com", partName = "return")
    @WebMethod
    public java.lang.String insertTask(
        @WebParam(partName = "taskxml", name = "taskxml")
        java.lang.String taskxml
    );

    @WebResult(name = "return", targetNamespace = "http://www.tepper.com", partName = "return")
    @WebMethod
    public java.lang.String cancleTask(
        @WebParam(partName = "source", name = "source")
        java.lang.String source,
        @WebParam(partName = "sourceid", name = "sourceid")
        java.lang.String sourceid
    );

    @WebResult(name = "return", targetNamespace = "http://www.tepper.com", partName = "return")
    @WebMethod
    public java.lang.String getTaskResult(
        @WebParam(partName = "source", name = "source")
        java.lang.String source,
        @WebParam(partName = "starttime", name = "starttime")
        java.lang.String starttime,
        @WebParam(partName = "endtime", name = "endtime")
        java.lang.String endtime
    );
}
