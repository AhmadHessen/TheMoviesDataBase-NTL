package com.example.coyg.tmdb.Details;

public class GetCrew
{
    private String crewImage;
    private String crewtName;
    private String jobCrew;

    public GetCrew(String crewImage, String crewtName, String jobCrew)
    {
        this.crewImage = crewImage;
        this.crewtName = crewtName;
        this.jobCrew = jobCrew;
    }

    public String getCrewImage()
    {
        return crewImage;
    }

    public String getCrewtName()
    {
        return crewtName;
    }

    public String getJobCrew()
    {
        return jobCrew;
    }
}
