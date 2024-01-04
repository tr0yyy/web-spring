package com.fmi.springweb.repository;

import com.fmi.springweb.model.BidEntity;
import com.fmi.springweb.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<BidEntity, Long> {
    Optional<List<BidEntity>> findBidEntitiesByUsername(UserEntity user);
    Optional<List<BidEntity>> findBidEntitiesByUsernameAndWinningBidIsTrue(UserEntity username);
}
