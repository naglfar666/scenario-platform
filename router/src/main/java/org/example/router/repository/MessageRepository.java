package org.example.router.repository;

import org.example.router.entity.MessageCached;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageRepository extends CrudRepository<MessageCached, UUID> {
}
