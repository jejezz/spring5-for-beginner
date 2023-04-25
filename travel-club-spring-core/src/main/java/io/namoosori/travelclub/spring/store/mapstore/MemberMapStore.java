package io.namoosori.travelclub.spring.store.mapstore;

import io.namoosori.travelclub.spring.aggregate.club.CommunityMember;
import io.namoosori.travelclub.spring.store.MemberStore;
import io.namoosori.travelclub.spring.util.exception.NoSuchMemberException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository("memberStore")
public class MemberMapStore implements MemberStore {

    private Map<String, CommunityMember> memberMap;

    public MemberMapStore() {
        this.memberMap = new LinkedHashMap<>();
    }

    @Override
    public String create(CommunityMember member) {
        this.memberMap.put(member.getId(), member);
        return member.getId();
    }

    @Override
    public CommunityMember retrieve(String memberId) {
        return this.memberMap.get(memberId);
    }

    @Override
    @Nullable
    public CommunityMember retrieveByEmail(String email) {
        for(CommunityMember member : this.memberMap.values()) {
            if(member.getEmail().equals(email))
                return member;
        }
        return null;
    }

    @Override
    public List<CommunityMember> retrieveByName(String name) {
        return this.memberMap.values().stream()
                .filter(map -> map.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public void update(CommunityMember member) {
        this.memberMap.put(member.getId(), member);
    }

    @Override
    public void delete(String memberId) {
        this.memberMap.remove(memberId);
    }

    @Override
    public boolean exists(String memberId) {
        return Optional.ofNullable(this.memberMap.get(memberId))
                .isPresent();
    }
}
