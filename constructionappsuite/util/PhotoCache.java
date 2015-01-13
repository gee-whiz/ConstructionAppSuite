package com.example.codetribe1.constructionappsuite.util;

import com.example.codetribe1.constructionappsuite.dto.transfer.PhotoUploadDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aubreyM on 2014/10/15.
 */
public class PhotoCache implements Serializable {
    private List<PhotoUploadDTO> photoUploadList = new ArrayList<>();

    public List<PhotoUploadDTO> getPhotoUploadList() {
        return photoUploadList;
    }

    public void setPhotoUploadList(List<PhotoUploadDTO> photoUploadList) {
        this.photoUploadList = photoUploadList;
    }
}
