package io.namoosori.travelclub.spring;

import io.namoosori.travelclub.spring.aggregate.club.CommunityMember;
import io.namoosori.travelclub.spring.aggregate.club.TravelClub;
import io.namoosori.travelclub.spring.service.ClubService;
import io.namoosori.travelclub.spring.service.MemberService;
import io.namoosori.travelclub.spring.service.sdo.MemberCdo;
import io.namoosori.travelclub.spring.service.sdo.TravelClubCdo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.Date;

public class TravelClubApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        String [] beanNames = context.getBeanDefinitionNames();
//        System.out.println(Arrays.toString(beanNames));

        MemberService memberService = context.getBean("memberService", MemberService.class);

        MemberCdo memberCdo = new MemberCdo(
                "test@gmail.com",
                "JongYun",
                "jejezz",
                "000-0000-0000",
                "1972.03.01");
        String memberId = memberService.registerMember(memberCdo);

        CommunityMember communityMember = memberService.findMemberById(memberId);
        System.out.println(communityMember.toString());

//        TravelClubCdo clubCdo = new TravelClubCdo("TravelClub", "Test TravelClub");
//        ClubService clubService = context.getBean("clubService", ClubService.class);
//
//        String clubId = clubService.registerClub(clubCdo);
//
//        TravelClub foundClub = clubService.findClubById(clubId);
//
//        System.out.println("Club name : " + foundClub.getName());
//        System.out.println("Club intro : " + foundClub.getIntro());
//        System.out.println("Club foundation date : " + new Date(foundClub.getFoundationTime()));
    }
}
