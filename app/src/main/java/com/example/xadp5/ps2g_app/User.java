package com.example.xadp5.ps2g_app;


import java.util.ArrayList;


 public class User {

    private  String AgencyName;
    private  String UID;
    private  String projectPercentage;
    private  ArrayList<String>projects;
    private  String userAccess;


     public User (String id){
         projects = new ArrayList<>();
         UID= id;


     }

       public void setAgencyName(String AN){

           AgencyName=AN;

       }



     public void setProjects(ArrayList<String> PJ){

         projects=PJ;

    }


      public void setProjectPercentage(String selectedProject){

            projectPercentage=selectedProject;
      }

     public void setUserAccess(String UA){


        userAccess=UA;

     }






      public String getProjectPercentage(){

        return projectPercentage;
    }





      public ArrayList<String> getProjects(){


        return projects;
    }


      public String getAgencyName(){

        return AgencyName;

    }

    public String getAccess(){

        return userAccess;
    }

    public String getUID(){

        return UID;
    }



 }
