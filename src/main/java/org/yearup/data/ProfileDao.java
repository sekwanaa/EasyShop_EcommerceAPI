package org.yearup.data;


import org.yearup.models.Profile;

public interface ProfileDao
{
    Profile create(Profile profile);

    Profile getProfileById(int userId);

    int updateProfile(int userId, Profile profile);

    int deleteProfile(int userId);
}
