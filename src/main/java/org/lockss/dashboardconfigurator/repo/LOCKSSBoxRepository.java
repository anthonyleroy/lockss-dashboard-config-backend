package org.lockss.dashboardconfigurator.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import org.lockss.dashboardconfigurator.model.LOCKSSBoxInfo;

public interface LOCKSSBoxRepository extends CrudRepository<LOCKSSBoxInfo, Long> {
//	List<LOCKSSBoxInfo> findByIpAddress(String ipAddress);
}

