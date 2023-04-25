package io.namoosori.travelclub.spring.service.logic;

import io.namoosori.travelclub.spring.aggregate.club.CommunityMember;
import io.namoosori.travelclub.spring.service.MemberService;
import io.namoosori.travelclub.spring.service.sdo.MemberCdo;
import io.namoosori.travelclub.spring.shared.NameValueList;
import io.namoosori.travelclub.spring.store.MemberStore;
import io.namoosori.travelclub.spring.util.exception.MemberDuplicationException;
import io.namoosori.travelclub.spring.util.exception.NoSuchMemberException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.List;

@Service("memberService")
public class MemberServiceLogic implements MemberService {

    private MemberStore memberStore;

    public MemberServiceLogic(MemberStore memberStore) {
        this.memberStore = memberStore;
    }

    @Override
    public String registerMember(MemberCdo member) {
        String email = member.getEmail();
        CommunityMember communityMember = this.memberStore.retrieveByEmail(email);
        if(communityMember != null) {
            throw new MemberDuplicationException("Member already exists with the email: " + email);
        }
        communityMember = new CommunityMember(member.getEmail(),
                member.getName(), member.getPhoneNumber());
        communityMember.setNickName(member.getNickName());
        communityMember.setBirthDay(member.getBirthDay());

        communityMember.checkValidation();

        this.memberStore.create(communityMember);
        return communityMember.getId();

    }

    @Override
    public CommunityMember findMemberById(String memberId) {
        return this.memberStore.retrieve(memberId);
    }

    @Override
    public CommunityMember findMemberByEmail(String memberEmail) {
        return this.memberStore.retrieveByEmail(memberEmail);
    }

    @Override
    public List<CommunityMember> findMembersByName(String name) {
        return this.memberStore.retrieveByName(name);
    }

    @Override
    public void modifyMember(String memberId, NameValueList nameValueList) {
        CommunityMember communityMember = this.memberStore.retrieve(memberId);
        if(communityMember == null) {
            throw new NoSuchMemberException("No such member " + memberId);
        }
        communityMember.modifyValues(nameValueList);
        this.memberStore.update(communityMember);
    }

    @Override
    public void removeMember(String memberId) {
        if(this.memberStore.exists(memberId)) {
            throw new NoSuchMemberException("No such member " + memberId);
        }
        this.memberStore.delete(memberId);
    }
}
