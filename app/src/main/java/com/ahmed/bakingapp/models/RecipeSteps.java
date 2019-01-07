package com.ahmed.bakingapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class RecipeSteps implements Serializable {

    private static final String TAG = RecipeSteps.class.getSimpleName();
    // Recipe Steps JSON UiConstants
    private static final String STEPS_ID = "id";
    private static final String STEPS_SHORT_DESCRIPTION = "shortDescription";
    private static final String STEPS_DESCRIPTION = "description";
    private static final String STEPS_VIDEO_URL = "videoURL";
    private static final String STEPS_THUMBNAIL_URL = "thumbnailURL";

    // Recipe Steps Values
    @SerializedName(STEPS_ID)
    private Integer stepsId;

    @SerializedName(STEPS_SHORT_DESCRIPTION)
    private String stepsShortDescription;

    @SerializedName(STEPS_DESCRIPTION)
    private String stepsDescription;

    @SerializedName(STEPS_VIDEO_URL)
    private String stepsVideoURL;

    @SerializedName(STEPS_THUMBNAIL_URL)
    private String stepsThumbnailURL;

    public Integer getStepsId() {
        return stepsId;
    }

    public void setStepsId(Integer stepsId) {
        this.stepsId = stepsId;
    }

    public String getStepsShortDescription() {
        return stepsShortDescription;
    }

    public void setStepsShortDescription(String stepsShortDescription) {
        this.stepsShortDescription = stepsShortDescription;
    }

    public String getStepsDescription() {
        return stepsDescription;
    }

    public void setStepsDescription(String stepsDescription) {
        this.stepsDescription = stepsDescription;
    }

    public String getStepsVideoURL() {
        return stepsVideoURL;
    }

    public void setStepsVideoURL(String stepsVideoURL) {
        this.stepsVideoURL = stepsVideoURL;
    }

    public String getStepsThumbnailURL() {
        return stepsThumbnailURL;
    }

    public void setStepsThumbnailURL(String stepsThumbnailURL) {
        this.stepsThumbnailURL = stepsThumbnailURL;
    }

    @Override
    public String toString() {
        String comma = ",";
        String equal = "=";
        char backSlash = '\'';
        return "RecipeSteps{" +
                STEPS_ID + equal +stepsId+
                comma+STEPS_SHORT_DESCRIPTION+equal+stepsShortDescription+backSlash+
                comma+STEPS_DESCRIPTION+equal+stepsDescription+backSlash+
                comma+STEPS_VIDEO_URL+equal+stepsVideoURL+backSlash+
                comma+STEPS_VIDEO_URL+equal+stepsThumbnailURL+backSlash+
                '}';
    }
}
