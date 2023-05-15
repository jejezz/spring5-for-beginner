package io.namoosori.travelclub.spring.service.logic;

import io.namoosori.travelclub.spring.aggregate.club.TravelClub;
import io.namoosori.travelclub.spring.service.ClubService;
import io.namoosori.travelclub.spring.service.sdo.TravelClubCdo;
import io.namoosori.travelclub.spring.shared.NameValueList;
import io.namoosori.travelclub.spring.store.ClubStore;
import io.namoosori.travelclub.spring.store.mapstore.ClubMapStore;
import io.namoosori.travelclub.spring.util.exception.NoSuchClubException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("clubService")
public class ClubServiceLogic implements ClubService {

    private ClubStore clubStore;

    public ClubServiceLogic(ClubStore clubStore) {
        this.clubStore = clubStore;
    }

    @Override
    public String registerClub(TravelClubCdo club) {
        TravelClub newClub = new TravelClub(club.getName(), club.getIntro());
        newClub.checkValidation();
        return this.clubStore.create(newClub);
    }

    @Override
    public TravelClub findClubById(String id) {
        return this.clubStore.retrieve(id);
    }

    @Override
    public List<TravelClub> findClubsByName(String name) {
        return this.clubStore.retrieveByName(name);
    }

    @Override
    public void modify(String clubId, NameValueList nameValues) throws NoSuchClubException {
        TravelClub club = this.clubStore.retrieve(clubId);
        if(club == null) {
            throw new NoSuchClubException("No such club with id: " + clubId);
        }
        club.modifyValues(nameValues);
        clubStore.update(club);
    }

    @Override
    public void remove(String clubId) throws NoSuchClubException {
        if(!this.clubStore.exists(clubId)) {
            throw new NoSuchClubException("No such club with id: " + clubId);
        }
        this.clubStore.delete(clubId);
    }
}
