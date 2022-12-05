package org.example.repository;

import org.example.model.IPProxy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPProxyRepository extends JpaRepository<IPProxy, Long> {
}
