package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.ProfileDao;
import org.yearup.models.Profile;

@RestController
@RequestMapping("profiles")
@CrossOrigin
public class ProfileController {
    private ProfileDao profileDao;

    @Autowired
    public ProfileController(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Profile createAProfile(Profile profile){
        return profileDao.create(profile);
//        try {
//            return profileDao.create(profile);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

}
