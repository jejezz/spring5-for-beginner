package io.namoosori.travelclub.web.controller;

import io.namoosori.travelclub.web.aggregate.club.TravelClub;
import io.namoosori.travelclub.web.service.ClubService;
import io.namoosori.travelclub.web.service.sdo.TravelClubCdo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClubController {

    private ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @PostMapping("/club") // localhost:8090/club
    public String register(@RequestBody TravelClubCdo travelClubCdo) {
        return clubService.registerClub(travelClubCdo);
    }

    @GetMapping("/club/all")
    public List<TravelClub> findAll() {
        return clubService.findAll();
    }

    @GetMapping("/club/{clubId}")
    public TravelClub find(@PathVariable String clubId) {
        return clubService.findClubById(clubId);
    }
}
