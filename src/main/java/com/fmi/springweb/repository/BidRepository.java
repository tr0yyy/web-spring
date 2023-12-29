package com.fmi.springweb.repository;

import com.fmi.springweb.model.BidEntity;
import com.fmi.springweb.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<BidEntity, Long> {
    Optional<List<BidEntity>> findBidEntitiesByUsernameAndWinningBidIsTrue(UserEntity username);
}
